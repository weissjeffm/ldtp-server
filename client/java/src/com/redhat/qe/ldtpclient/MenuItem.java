package com.redhat.qe.ldtpclient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MenuItem extends Element {

	public MenuItem(String windowName, String... pathItems) {
		super(windowName, join(Arrays.asList(pathItems), ";"));
	}

	 static String join(Collection<?> s, String delimiter) {
	     StringBuilder builder = new StringBuilder();
	     Iterator<?> iter = s.iterator();
	     while (iter.hasNext()) {
	         builder.append(iter.next());
	         if (!iter.hasNext()) {
	           break;                  
	         }
	         builder.append(delimiter);
	     }
	     return builder.toString();
	 }
}
