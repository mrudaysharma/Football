package com.cronjob;

import java.net.URI;
import java.net.URISyntaxException;

import javax.naming.*;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.xmlparsing.XMLParsing;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Cron Job.
 * 
 * @author Uday Sharma
 * @version 1.0 02/22/2011
 */
public class CronJob {
	private final Timer timer = new Timer();
	private int minutes = 15;
	private URI url;


	/**
	 * Web Call method.
	 * 
	 */
	public void getJobInfo() {
		try {
			// Properties from web.xml enviornment file
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			String ctxminutes = (String) envCtx.lookup("Minutes");
			this.minutes = Integer.parseInt(ctxminutes);
			startScheduler();
		} catch (Exception e) {
		}
	}

	/**
	 * start schedular.
	 * 
	 */
	private void startScheduler() {
		try {
			timer.schedule(new TimerTask() {
				public void run() {
					scheduleParse();
					// timer.cancel();
				}

				private void scheduleParse() {
					try {
						url = new URI("http://localhost:8080/SportsManagement");

						ClientConfig configure = new DefaultClientConfig();
						Client client = Client.create(configure);
						WebResource service = client.resource(url);

						// Get XML for application
						 String xml = service.path("rest").path("todo")
								.accept(MediaType.APPLICATION_XML)
								.get(String.class);
						System.out.println("---------\n"+xml);
						XMLParsing parsedata = new XMLParsing(xml);
						parsedata.readXML();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}, 0, this.minutes * 60 * 1000);
		} catch (Exception e) {
		}
	}
}