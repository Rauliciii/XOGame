package com.endava.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.endava.gui.GameFrame;
import com.endava.gui.GameFrameImpl;
import com.endava.model.GameHandle;
import com.endava.model.Interpreter;

public class GameControllerImpl implements GameController {

	private String type;
	private GameHandle game;
	private GameFrame gFrame;
	private Socket sock;

	public GameControllerImpl(GameHandle game) {
		this.game = game;
	}

	public String check(int x, int y) {
		return game.valueAtPos(x, y);

	}

	public void set(int x, int y, String value) {
		try {
			PrintWriter printer = new PrintWriter(sock.getOutputStream(), true);
			printer.println(x + "_" + y);
			printer.flush();
			// printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		// saves 2 sockets from server and self
		// starts 2 threads for continous reciving and sending
		try {
			sock = new Socket("localhost", 54321);
			BufferedReader recieve = new BufferedReader(new InputStreamReader(
					this.sock.getInputStream()));
			String msgRecieved = recieve.readLine();
			updateFrameResources(msgRecieved);

			RecieveThread recieveThread = new RecieveThread(sock);
			Thread thread2 = new Thread(recieveThread);
			thread2.start();
		} catch (Exception e) {
			gFrame.displayMessage(e.getMessage());
		}
	}

	private void updateFrameResources(String msgRecieved) {
		List<String> message = new Interpreter().interpretString(msgRecieved);
		int dim = Integer.parseInt(message.get(0));
		int k = Integer.parseInt(message.get(1));
		String type = message.get(2);
		this.type = type;
		gFrame.setResources(dim, k, type);
	}

	public void setView(GameFrameImpl gf) {
		this.gFrame = gf;
	}

	class RecieveThread implements Runnable {
		Socket sock = null;
		BufferedReader recieve = null;

		public RecieveThread(Socket sock) {
			this.sock = sock;
		}

		public void run() {
			try {
				recieve = new BufferedReader(new InputStreamReader(
						this.sock.getInputStream()));
				String msgRecieved;
				while ((msgRecieved = recieve.readLine()) != null) {
					System.out.println("Client " + type + "has recived:"
							+ msgRecieved);
					if (msgRecieved.equals("LOSE")) {
						gFrame.gameLost();
					} else if ("WINN".equals(msgRecieved)) {
						gFrame.gameWon();
					} else {
						gFrame.ClickAtPosition(msgRecieved);
					}
					gFrame.displayMessage("C: Send to server: ");
				}
			} catch (Exception e) {
				gFrame.displayMessage(e.getMessage());
			}
		}

	}

}
