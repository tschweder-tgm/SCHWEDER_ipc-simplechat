package schweder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.trolltech.qt.gui.QApplication;

public class Client {

	private int port;
	private String host;
	private Ui_ChatClient cg;// gui
	private Socket socket;
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;

	public Client(int port, String host, Ui_ChatClient cg) {
		this.port = port;
		this.host = host;
		this.cg = cg;
	}

	public boolean start() {
		try {
			socket= new Socket(host, port);
			sInput= new ObjectInputStream(socket.getInputStream());
			sOutput= new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Listener().start();
		return true;
		
	}

	public void sendMessage(String message) {
		try {
			sOutput.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (sInput != null)
				sInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class Listener extends Thread {
		public void run() {
			while (true) {
				try {
					String incomingMsg = (String) sInput.readObject();
					QApplication.invokeLater(new Runnable() { // GE�NDERT
						@Override
						public void run() {
							cg.messages.addItem(incomingMsg); // fuegt nachricht
																// in die
							// Chatbox
						}
					});
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					break;
				}
			}
		}
	}
}
