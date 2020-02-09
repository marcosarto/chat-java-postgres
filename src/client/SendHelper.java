package client;

import java.io.PrintWriter;

public class SendHelper {
	PrintWriter pw;
	String dest;

	public void setPw(PrintWriter pw){
		this.pw = pw;
	}

	public void setDest(String dest){
		this.dest = dest + "!";
	}

	public void send(String msg){
		pw.println(dest+msg);
		pw.flush();
	}

	public void sendSenzaDesinatario(String msg){
		pw.println(msg);
		pw.flush();
	}
}
