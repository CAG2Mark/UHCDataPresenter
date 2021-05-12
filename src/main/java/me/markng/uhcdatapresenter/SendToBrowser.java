package me.markng.uhcdatapresenter;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class SendToBrowser {
	private static String encodeMessage(String message) {
		try {
			return URLEncoder.encode(message, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		} catch(UnsupportedEncodingException e) {
			return "ERROR PARSING";
		}
	}
	public static void sendMessage(String message) {
		try {
			URL url = new URL("http://localhost:8080/?message="+encodeMessage(message));
			InputStream is = url.openStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendLog(String message) {
		SendToBrowser.sendMessage("{\"log\":\""+encodeMessage(message)+"\"}");
	}
}
