package schweder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.trolltech.qt.gui.QApplication;

public class Server {
	private static int connectionid;
	private int port;// Portadresse
	private boolean keepGoing;
	private Ui_ChatServer sg;// ServerGUI
	private ArrayList<ClientThread> al;

	/**
	 * Konstruktor
	 */

	public Server(int port, Ui_ChatServer sg) {
		this.port = port;
		this.sg = sg;
		al = new ArrayList<ClientThread>();
	}

	public synchronized void broadcast(String msg) { // sync funktioniert so wie ein LOCK !!!!!!!
		 // nachricht in die GUI
		QApplication.invokeLater(new Runnable() {
			@Override
			public void run() {
				sg.messages.addItem(msg);
			}
		});
		// rueckwaerts loop falls jemand dc ist und wir ihn kicken m�ssen
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// versucht nachricht an client zu schicken falls false remove
			if (!ct.writeMsg(msg))
				al.remove(i);
		}

	}

	public synchronized void remove(int id) {
		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			if (ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	protected void stop() {
		keepGoing = false;
	}

	public void start() {
		keepGoing = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (keepGoing) {
				// wartet auf einen client bis es accept machen kann
				Socket socket = serverSocket.accept();
				if (!keepGoing)
					break;
				
				ClientThread t = new ClientThread(socket);
				al.add(t);// f�gt client in die ArrayList hinzu
				t.start();// startet Thread
			}
			serverSocket.close();

			for (int i = 0; i < al.size(); ++i) {
				ClientThread ct = al.get(i);
				ct.close();
			}
		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public class ClientThread extends Thread {
		Socket socket;
		int id;
		String cm;// ChatMessage
		ObjectOutputStream sOutput;
		ObjectInputStream sInput;

		public ClientThread(Socket socket) {
			id = ++connectionid;// ++ Berechnung vorher
			this.socket = socket;
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			boolean keepGoing = true;
			while (keepGoing) {
				try {
					cm = (String) sInput.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				// sendet an alle anderen
				broadcast(cm);
			}
			// remove client id
			remove(id);
			close();
		}

		/**
		 * schlie�en von Input/Output und socket
		 */
		private void close() {
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

		/**
		 * Message write
		 */
		private boolean writeMsg(String msg) {
			if (!socket.isConnected()) {
				close();
				return false;
			}
			try {
				sOutput.writeObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}

}
