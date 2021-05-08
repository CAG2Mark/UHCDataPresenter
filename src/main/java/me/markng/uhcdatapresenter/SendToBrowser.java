package me.markng.uhcdatapresenter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SendToBrowser {
	public static void sendMessage(String message) {
		try {
			URL url = new URL("http://localhost:8080/test");
			InputStream is = url.openStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
