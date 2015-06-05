package com.endava.controller;

import com.endava.gui.GameFrameImpl;

public interface GameController {
		
	void set(int x, int y, String value);
	
	void setView(GameFrameImpl gf);
	
	void start();
	
}
