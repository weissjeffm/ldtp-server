package com.redhat.qe.ldtpclient;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;

import com.redhat.qe.xmlrpc.Session;

public class LDTPClient {
	protected Session session;
	protected URL url;
	
	
	public LDTPClient(String url)  {
		try {
			this.url = new URL(url);
		}catch (MalformedURLException mue){
			throw new RuntimeException(mue);
		}
		session = new Session("", "", this.url);
	}
	
	public void init() {
		try {
			session.init();
		} catch (Exception e){
			throw new RuntimeException("Could not initialize xmlrpc client.", e);
		}
	}
		
	public Integer launchApp(String binary, String[] arguments)  {
		return invoke("launchapp", Integer.class, binary, arguments);
	}

	public Integer click(Element element)  {
		return invoke("click", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer stateEnabled(Element element)  {
		return invoke("stateenabled", Integer.class, element.getWindowName(), element.getComponentName());
	}
	
	public Integer check(Element element)  {
		return invoke("check", Integer.class, element.getWindowName(), element.getComponentName());
	}
	
	public Integer uncheck(Element element)  {
		return invoke("uncheck", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer verifyCheck(Element element)  {
		return invoke("verifycheck", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer getTableRowIndex(Element element, String tableName, String cellValue)  {
		return invoke("gettablerowindex", Integer.class, element.getWindowName(), element.getComponentName(), tableName, cellValue);
	}
	
	public Integer checkRow(Element element, Integer rowIndex, Integer colIndex)  {
		return invoke("checkrow", Integer.class, element.getWindowName(), element.getComponentName(), rowIndex, colIndex);
	}
	
	public String[] getAllStates(Element element)  {
		return invoke("getallstates", String[].class, element.getWindowName(), element.getComponentName());
	}

	public Integer hasstate(Element element, String state)  {
		return invoke("hasstate", Integer.class, element.getWindowName(), element.getComponentName(), state);
	}

	public Integer selectRowPartialMatch(Element element, String matchText)  {
		return invoke("selectrowpartialmatch", Integer.class, element.getWindowName(), element.getComponentName(), matchText);
	}

	public Integer waitTilGuiExist(Element element, int timeoutSeconds)  {
		return invoke("waittillguiexist", Integer.class, element.getWindowName(), element.getComponentName(), timeoutSeconds);
	}
	
	public Integer waitTilGuiExist(Element element)  {
		return invoke("waittillguiexist", Integer.class, element.getWindowName(), element.getComponentName());
	}
	
	public Integer setTextValue(Element element, String text)  {
		return invoke("settextvalue", Integer.class, element.getWindowName(), element.getComponentName(), text);
	}

	public String getTextValue(Element element)  {
		return invoke("gettextvalue", String.class, element.getWindowName(), element.getComponentName());
	}
	
	public Integer activateWindow(Element element)  {
		return invoke("activatewindow", Integer.class, element.getWindowName());
	}
	
	public void closeWindow(String windowTitle)  {
		invoke("closewindow", null, windowTitle);
	}

	public String getCellValue(Element element, int row, int column){
		return invoke("getcellvalue", String.class, element.getWindowName(), element.getComponentName(), row, column);
	}
	
	public Integer getRowCount(Element element){
		return invoke("getrowcount", Integer.class, element.getWindowName(), element.getComponentName());
	}
	
	public Integer menuItemEnabled(Element element)  {
		return invoke("menuitemenabled", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer menuCheck(Element element)  {
		return invoke("menucheck", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer menuUncheck(Element element)  {
		return invoke("menuuncheck", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer selectMenuItem(MenuItem element)  {
		return invoke("selectmenuitem", Integer.class, element.getWindowName(), element.getComponentName());
	}

	public Integer comboSelect(Element element, String item)  {
		return invoke("comboselect", Integer.class, element.getWindowName(), element.getComponentName(), item);
	}

	/*
	 * below are higher level methods that provide some common abstractions
	 */
	
	public boolean isShowing(Element element) {
		return hasstate(element, "showing") == 1;
	}

	public void setChecked(Element element, boolean checked) {
		if (checked) check(element);
		else uncheck(element);
	}

	public <T>T invoke(String methodName, Class<T> returnType, Object... args) {
		try {
			return (T)session.getClient().execute(methodName, args);
		} catch (XmlRpcException xe) {
			throw new RuntimeException("XMLRPC call failed", xe);
		}
	}
	public static void main(String... args) {
		LDTPClient client  = new LDTPClient("http://jweiss-rhel6-1.usersys.redhat.com:8001/");
		client.init();
		client.launchApp("subscription-manager-gui", new String[] {});
		Element mainDialog = new Element("dialog_updates", "");
		Element closeButton = new Element("dialog_updates", "button_close");
		
		client.waitTilGuiExist(mainDialog);
		Integer state = client.hasstate(closeButton, "showing");
		System.out.println("showing state is " + state);
		if (state == 1) {
			client.click(closeButton);
		}
		
	}

}
