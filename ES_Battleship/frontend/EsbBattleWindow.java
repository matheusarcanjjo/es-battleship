/**
 * 
 */
package frontend;

import java.awt.*;

import javax.swing.*;

import backend.constants.GameResult;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * @author cloud
 *
 */
public class EsbBattleWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane                 = null;
	private EsbPlayerGridPanel mPlayerPanel     = null;
	private EsbOpponentGridPanel mOpponentPanel = null;
	private EsbFrontendController mFController  = null;
	private JScrollPane mScrollMessageArea      = null;
	private JTextArea mMessageArea              = null;
	
	/**
	 * Default Constructor
	 * @param aFController This is an object that facilitates communication with the system's backend.
	 */
	public EsbBattleWindow(EsbFrontendController aFController) {
		super();
		
		mFController = aFController;
		this.mFController.setBattleWindow(this);
		
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		mPlayerPanel   = new EsbPlayerGridPanel(mFController);
		mOpponentPanel = new EsbOpponentGridPanel(mFController);
		mMessageArea   = new JTextArea();
		mScrollMessageArea = new JScrollPane(mMessageArea);
		this.setResizable(false);		
		this.setContentPane(getJContentPane());
		this.setTitle("ES Battleship");
//		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);	
		final JFrame localWindow = this;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {            	
//				try {
//					mFController.disconnect();
				System.exit(0);
//				} catch (Exception e1) {
//					String tMsg = e1.getMessage();
//					if (tMsg == null) {
//						tMsg = new String("Could not disconnect.");
//					}
//					JOptionPane.showMessageDialog(localWindow, tMsg, 
//							"Error", JOptionPane.ERROR_MESSAGE);
//					System.exit(-1);
//				}            	
			}
		});
		this.pack();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth  = 1;	
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			
			mPlayerPanel.setName("Player Grid");
			mPlayerPanel.setVisible(true);
			mOpponentPanel.setName("Opponent Grid");
			mOpponentPanel.setVisible(true);
			

			mMessageArea.setBackground(Color.WHITE);
			mMessageArea.setEditable(false);
			mMessageArea.setVisible(true);
			mScrollMessageArea.setSize(new Dimension(800, 100));			
			mScrollMessageArea.setPreferredSize(new Dimension(800, 100));			
			mScrollMessageArea.setVisible(true);
			
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.setBackground(Color.DARK_GRAY);
			jContentPane.setVisible(true);
			jContentPane.add(mPlayerPanel,  gridBagConstraints);
			
			gridBagConstraints.gridx = 1;
			jContentPane.add(mOpponentPanel, gridBagConstraints);
			
			gridBagConstraints.gridx      = 0;
			gridBagConstraints.gridy      = 1;	
			gridBagConstraints.gridwidth  = 2;
			gridBagConstraints.gridheight = 1;	
			jContentPane.add(mScrollMessageArea, gridBagConstraints);			
			
		}
		return jContentPane;		
	}
	
	public void setTurn(boolean aIsPlayersTurn){
		this.mOpponentPanel.setCanClick(aIsPlayersTurn);
	}
	
	/**
	 * This method should be called from a controller object that detects a
	 * change in the data that is to be displayed.  This method allows 
	 * components to handle the change in their own way.
	 */
	public void notifyComponents(){
		mPlayerPanel.screenNotify();
		mOpponentPanel.screenNotify();
		
		// Display all messages from game engine
		Vector<String> tMessageList = mFController.getMessages();
		Iterator<String> tMessageIterator = tMessageList.iterator();
		while (tMessageIterator.hasNext()) {			
			mMessageArea.append(mMessageArea.getLineCount() + ">  " + tMessageIterator.next() + "\n");			
		}
		int tScrollMax = mScrollMessageArea.getVerticalScrollBar().getMaximum();
		mScrollMessageArea.getVerticalScrollBar().setValue(tScrollMax);
		mFController.resetMessages();
		
		if (GameResult.UNKNOWN != mFController.getGameResult()) {
			JOptionPane.showMessageDialog(this,
					(GameResult.WIN == mFController.getGameResult() ) ? "You Win!" : "You Lose!", 
							"End of Game", JOptionPane.INFORMATION_MESSAGE);
//			try {
//				mFController.disconnect();
//			} catch (Exception e) {							
//				JOptionPane.showMessageDialog(this, e.getMessage(), 
//						"Error", JOptionPane.ERROR_MESSAGE);
//				System.exit(-1);
//			}
			System.exit(0);
		}		
	}

}
