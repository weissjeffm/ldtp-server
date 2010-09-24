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
		return (Integer)session.getClient().execute("launchapp", new Object[] {binary, arguments});
	}
	public static void main(String... args) throws Exception{
		LDTPClient client  = new LDTPClient("http://jweiss-rhel6-1.usersys.redhat.com:8001/");
		client.init();
		client.launchApp("subscription-manager-gui", new String[] {});
		
	}

}
