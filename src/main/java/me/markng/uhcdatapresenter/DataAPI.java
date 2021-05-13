package me.markng.uhcdatapresenter;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DataAPI {

	private HttpServer server;

	private boolean isInitialized;

	public void addDeath(Death death) {
		response.deaths.add(death);
	}

	public void reset() {
		response.deaths.clear();
	}

	public DataAPI() {

	}

	public static class Response {
		public List<PlayerInfo> players=new ArrayList<>();
		public PlayerInfo curPlayer;
		public List<Death> deaths=new ArrayList<>();
	}
	public static Response response=new Response();
	public static class Death {
		public String attacker;
		public String name;
		public String key;
		public String message;
		public Long time;
		public Death(String attacker, String killed, TranslatableText text) {
			this.attacker=attacker;
			this.name=killed;
			this.key=text.getKey();
			this.message=text.getString();
			this.time=(System.currentTimeMillis() / 1000L);
		}
	}
	public void initialize() throws IOException {
		if (isInitialized) return;

		try {
			server = HttpServer.create(
					new InetSocketAddress("localhost", 8081),
					0
			);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

		server.createContext("/data.json", exchange -> {
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

			if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
				exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
				exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
				exchange.sendResponseHeaders(204, -1);
				return;
			}


			OutputStream outputStream = exchange.getResponseBody();
			Gson gson = new Gson();
			String res=gson.toJson(response);

			exchange.sendResponseHeaders(200, res.length());

			outputStream.write(res.getBytes(StandardCharsets.UTF_8));
			outputStream.flush();
			outputStream.close();
		});

		server.setExecutor(threadPoolExecutor);
		server.start();

		System.out.println("Server started on port 8081");

		isInitialized = true;
	}


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

	}
	public static void sendLog(String message) {
		DataAPI.sendMessage("{\"log\":\""+encodeMessage(message)+"\"}");
	}
}
