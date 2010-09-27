package com.redhat.qe.ldtpclient;

public class Element {

	protected String windowName;
	protected String componentName;
	
	public Element(String windowName, String componentName) {
		super();
		this.windowName = windowName;
		this.componentName = componentName;
	}

	public String getWindowName() {
		return windowName;
	}

	public String getComponentName() {
		return componentName;
	}
	
	
}
