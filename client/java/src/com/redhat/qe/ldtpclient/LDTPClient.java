package com.redhat.qe.ldtpclient;

import java.net.URL;

import com.redhat.qe.xmlrpc.Session;

public class LDTPClient {
	protected Session session;
	protected URL url;
	
	
	public LDTPClient(String url) throws Exception {
		this.url = new URL(url);
		session = new Session("", "", this.url);
	}
	
	public void init() throws Exception{
		session.init();
	}
	
	public Integer launchApp(String binary, String[] arguments) throws Exception {
		return invoke("launchapp", Integer.class, binary, arguments);
	}

	public Integer click(String windowName, String componentName) throws Exception {
		return invoke("click", Integer.class, windowName, componentName);
	}

	public Integer stateEnabled(String windowName, String componentName) throws Exception {
		return invoke("stateenabled", Integer.class, windowName, componentName);
	}
	
	public Integer check(String windowName, String componentName) throws Exception {
		return invoke("check", Integer.class, windowName, componentName);
	}
	
	public Integer uncheck(String windowName, String componentName) throws Exception {
		return invoke("uncheck", Integer.class, windowName, componentName);
	}

	public Integer verifyCheck(String windowName, String componentName) throws Exception {
		return invoke("verifycheck", Integer.class, windowName, componentName);
	}

	public Integer getTableRowIndex(String windowName, String componentName, String tableName, String cellValue) throws Exception {
		return invoke("gettablerowindex", Integer.class, windowName, componentName, tableName, cellValue);
	}
	
	public Integer checkRow(String windowName, String componentName, Integer rowIndex, Integer colIndex) throws Exception {
		return invoke("checkrow", Integer.class, windowName, componentName, rowIndex, colIndex);
	}
	
	public String[] getAllStates(String windowName, String componentName) throws Exception {
		return invoke("getallstates", String[].class, windowName, componentName);
	}

	public Integer hasstate(String windowName, String componentName, String state) throws Exception {
		return invoke("hasstate", Integer.class, windowName, componentName, state);
	}

	public Integer selectRowPartialMatch(String windowName, String componentName, String matchText) throws Exception {
		return invoke("selectrowpartialmatch", Integer.class, windowName, componentName, matchText);
	}

	public Integer waitTilGuiExist(String windowName, String componentName) throws Exception {
		return invoke("waittillguiexist", Integer.class, windowName, componentName);
	}



	public <T>T invoke(String methodName, Class<T> returnType, Object... args) throws Exception{
		return (T)session.getClient().execute(methodName, args);
	}
	public static void main(String... args) throws Exception{
		LDTPClient client  = new LDTPClient("http://jweiss-rhel6-1.usersys.redhat.com:8001/");
		client.init();
		client.launchApp("subscription-manager-gui", new String[] {});
		
	}

}
