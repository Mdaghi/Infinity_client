package controller;

import java.util.Comparator;

class MyComp implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return o2.compareTo(o1);
	}

	
}

