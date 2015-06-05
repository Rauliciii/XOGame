package com.endava.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.endava.controller.GameController;
import com.endava.controller.GameControllerImpl;
import com.endava.model.Interpreter;

public class GameFrameImpl extends JFrame implements GameFrame {

	private static final long serialVersionUID = 1L;
	private int n;
	private String type;
	private GameController ctr;
	private boolean turn = false;
	Map<String, JButton> buttonMap = new HashMap<String, JButton>();

	public GameFrameImpl(GameControllerImpl gctr) {
		super("XO-Game");
		this.ctr = gctr;
		ctr.setView(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		run();
	}

	public void displayMessage(String message) {
		System.out.println(message);
	}

	public void run() {
		this.ctr.start();

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(n, n, 15, 15));
		setUpPanel(panel);
		add(panel);
	}

	private void setUpPanel(JPanel panel) {
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.setPreferredSize(new Dimension(300, 300));
		for (int i = 0; i < n * n; i++) {
			String position = Interpreter.convertPosition(i, this.n);
			JButton button = new JButton("");
			button.setSize(300, 400);
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (turn == true) {
						JButton buttonClicked = (JButton) e.getSource();
						buttonClicked.setEnabled(false);
						buttonClicked.setText(type);
						String positionClicked = getPositionFromButton(buttonClicked);
						System.out.println(positionClicked);
						List<String> posList = new Interpreter()
								.interpretString(positionClicked);
						int x = Integer.parseInt(posList.get(0));
						int y = Integer.parseInt(posList.get(1));

						ctr.set(x, y, type);
						turn = false;
					} else {
						showMessage("Not your turn!");
					}
				}
			});
			buttonMap.put(position, button);
			panel.add(button);
		}
	}

	private String getPositionFromButton(JButton buttonClicked) {
		for (Entry<String, JButton> entity : buttonMap.entrySet()) {
			String key = entity.getKey();
			JButton value = entity.getValue();
			if (buttonClicked.equals(value)) {
				return key;
			}
		}
		return "No button position";
	}

	public void setResources(int dim, int k2, String type2) {
		System.out.println("Updated n=" + dim);
		this.n = dim;
		this.type = type2;
		if (type.equals("X"))
			this.turn = true;
	}

	public void ClickAtPosition(String pos) {
		JButton buttonOc = buttonMap.get(pos);
		buttonOc.setText(inverseType(type));
		buttonOc.setEnabled(false);
		turn = true;
	}

	private String inverseType(String type2) {
		if ("X".equals(type2))
			return "Y";
		return "X";
	}

	public void gameLost() {
		buttonMap.get("0_0").setText("LOST");
		JOptionPane.showMessageDialog(this, "You are the Loser!");

	}

	public void showMessage(String string) {
		JOptionPane.showMessageDialog(this, string);
	}

	public void gameWon() {
		buttonMap.get("0_0").setText("WINN");
		JOptionPane.showMessageDialog(this, "You are the Winner!");
	}

}
