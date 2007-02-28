package frontend.test;

import backend.client.IClient;
import backend.state.*;
import backend.state.ships.Ships;
import java.util.*;
import frontend.test.TestOpponent;

public class TestClient implements IClient {

	private Player mPlayer;
	private Board mBoard;
	
	public TestClient(String aid, Board aBoard) {
		mBoard = aBoard;
		
		mBoard.add(Ships.GetAircraftCarrier(), 0, 0, Orientation.VERTICAL);
		mBoard.add(Ships.GetBattleship(), 1, 1, Orientation.HORIZONTAL);
		mBoard.add(Ships.GetCruiser(), 6, 4, Orientation.VERTICAL);
		mBoard.add(Ships.GetPatrolBoat(), 6, 9, Orientation.HORIZONTAL);
		mBoard.add(Ships.GetSubmarine(), 5, 5, Orientation.VERTICAL);
		mBoard.print();
		
		mPlayer = new Player(aid, mBoard);
	}
	
	public void connect(String server, String port) {
		// We will leave this blank for test purposes.

	}

	public void disconnect() {
		// We will leave this blank for test purposes.

	}

	public Player getPlayer() {
		return mPlayer;
	}

	public boolean move(int x, int y) {
		//TEST CODE
		boolean rValue = false;			
		if(x<=4){
			mPlayer.getOppBoard().setCoordinate(Constants.BOARD_HIT, x, y);
			rValue = true;
		} else {		
			mPlayer.getOppBoard().setCoordinate(Constants.BOARD_MISS, x, y);
			rValue = false;
		}
		
		// If Hit, still player's turn
		mPlayer.setMyTurn(rValue);
		
		if (!rValue) {
			Thread t = new Thread(new TestOpponent(mPlayer));
			t.start();
		}
		
		//mPlayer.getMyBoard().setCoordinate(c, x, y);
		
		return rValue;
	}

}