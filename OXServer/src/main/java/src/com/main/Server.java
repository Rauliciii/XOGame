package src.com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import src.com.controller.GameController;
import src.com.controller.Interpreter;
import src.com.model.DataSocketManage;

public class Server implements Runnable {

	private GameController ctr;
	private String CLIENT = "X";

	public Server(GameController ctr) {
		this.ctr = ctr;
	}

	public void run() {
		while (true) {
			try {
				final int port = 54321;
				System.out.println("Server waiting for connection on port "
						+ port);
				ServerSocket ss = new ServerSocket(port);
				Socket clientSocket = ss.accept();
				System.out.println("Recieved connection from "
						+ clientSocket.getInetAddress() + " on port "
						+ clientSocket.getPort() + " TYPE: " + CLIENT);

				//sending Dimension
				try {
					PrintWriter printWriterr = new PrintWriter(
							new OutputStreamWriter(
									clientSocket.getOutputStream()));

					printWriterr.println(ctr.getDimension() + "_" + ctr.getK()
							+ "_" + CLIENT);
					printWriterr.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}

				DataSocketManage.add(CLIENT, clientSocket);

				// create two threads to send and recieve from client
				RecieveFromClientThread recieve = new RecieveFromClientThread(
						clientSocket, ctr, CLIENT);
				Thread thread = new Thread(recieve);
				thread.start();
				SendToClientThread send = new SendToClientThread(clientSocket,
						ctr, CLIENT);

				CLIENT = "Y";
				Thread thread2 = new Thread(send);
				thread2.start();
				ss.close();

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	class RecieveFromClientThread implements Runnable {
		private Socket clientSocket = null;
		private BufferedReader brBufferedReader = null;
		private GameController ctr;
		private String type;

		public RecieveFromClientThread(Socket clientSocket, GameController ctr,
				String cLIENT) {
			this.clientSocket = clientSocket;
			this.ctr = ctr;
			this.type = cLIENT;
		}

		private String handleClient(String command, String type2) {
			// takes the command as argument, takes care of the command and
			// retrives a string containing the action that must be done
			System.out.println("S: I recived command: " + command);
			List<String> myList = Interpreter.interpretString(command);
			int i = Integer.parseInt(myList.get(0));
			int j = Integer.parseInt(myList.get(1));

			this.ctr.addValue(i, j, type2);

			String messgBTClient = command;

			return messgBTClient;
		}

		public void run() {
			try {
				brBufferedReader = new BufferedReader(new InputStreamReader(
						this.clientSocket.getInputStream()));

				String messageString;
				while (true) {

					while ((messageString = brBufferedReader.readLine()) != null) {

						String returnMessage = this.handleClient(messageString,
								type);

						Socket sock = DataSocketManage
								.getSocketFromString(inverse(type));
						try {
							PrintWriter printWriterr = new PrintWriter(
									new OutputStreamWriter(
											sock.getOutputStream()));
							if (this.ctr.gameWon(type)) {
								returnMessage += "\nLOSE";
								// send to the othere one - WINN
								Socket winSocket = DataSocketManage
										.getSocketFromString(type);
								PrintWriter pw = new PrintWriter(
										new OutputStreamWriter(
												winSocket.getOutputStream()));
								pw.println("WINN");
								pw.flush();
							}
							printWriterr.println(returnMessage);
							printWriterr.flush();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}

		private String inverse(String type2) {
			if (type2.equals("Y")) {
				return "X";
			}
			return "Y";
		}
	}

	class SendToClientThread implements Runnable {
		private PrintWriter pwPrintWriter;
		public Socket clientSock = null;
		GameController ctr;
		String type;

		public SendToClientThread(Socket clientSock, GameController ctr,
				String cLIENT) {
			this.ctr = ctr;
			this.clientSock = clientSock;
			type = cLIENT;
		}

		public void run() {
			try {
				pwPrintWriter = new PrintWriter(new OutputStreamWriter(
						this.clientSock.getOutputStream()));

				while (true) {
					BufferedReader input = new BufferedReader(
							new InputStreamReader(System.in));
					String msgToClientString = input.readLine();

					pwPrintWriter.println(msgToClientString);
					pwPrintWriter.flush();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
