package com.endava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Interpreter {

	private final String DELIMITER = "_";
	private List<String> list;

	public List<String> interpretString(String string) {
		//System.out.println("S: Interpreting the command");
		list = new ArrayList<String>();
		// first pos in the list: type of connection
		// second and third: parameters
		if (string.indexOf('_') == -1) {
			list.add(string);
		} else {
			StringTokenizer tokenizer = new StringTokenizer(string, DELIMITER);
			while (tokenizer.hasMoreTokens())
				list.add(tokenizer.nextToken());
		}
		//System.out.println(list);
		return list;
	}
	
	public static String convertPosition(int pos, int dimension) {
		String s = "";
		int i = pos/dimension;
		int j = pos%dimension;
		s += i + "_" + j;
		return s;
	}

}
