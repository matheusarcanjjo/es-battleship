package backend.util;

import java.io.PrintWriter;

public class MsgUtils {
	
	//Messages from Clients to Server
	public static void sendTestMessage (PrintWriter out) {
		String pack = "0|test";
		out.println(pack);
	}

	public static void sendMoveMessage (PrintWriter out, int x, int y) {
		String pack = "1|"+Integer.toString(x)+"|"+Integer.toString(y);
		out.println(pack);
	}
}