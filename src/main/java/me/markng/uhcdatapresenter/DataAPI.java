package me.markng.uhcdatapresenter;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataAPI {

	private HttpServer server;

	private boolean isInitialized;
	Response response=new Response();
	public static String playerName;
	public void addDeath(Death death) {
		response.deaths.add(death);
	}

	public void reset() {
		response.deaths.clear();
	}

	public void setStarted(boolean started) {
		response.started = started;
		if (started) {
			MinecraftClient mc = MinecraftClient.getInstance();

			if (mc.player == null) return;
			mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("[UHCDataPresenter] Set started flag to true."), mc.player.getUuid());
		}
	}

	public DataAPI() {

	}

	public void update() {
		ClientPlayerEntity thisPlayer=MinecraftClient.getInstance().player;
		if(thisPlayer==null) return;
		playerName = thisPlayer.getName().asString();

		// init stream
		Supplier<Stream<PlayerInfo>> playerStream = () -> thisPlayer.networkHandler.getPlayerList().stream()
				.filter(player->player.getDisplayName()==null)//remove BTLP2ebb60ef
				.map(PlayerInfo::new);

		// intermediary step to get the info of the current player
		PlayerInfo cur = playerStream.get().filter(p -> p.name.equals(playerName)).findFirst().orElse(null);

		if (cur != null) {
			response.curPlayer=cur;
		}

		response.players=playerStream.get().collect(Collectors.toList());
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
			update();

			OutputStream outputStream = exchange.getResponseBody();
			Gson gson = new Gson();
			String res=gson.toJson(response);

			byte[] bytes = res.getBytes();

			exchange.sendResponseHeaders(200, bytes.length);

			try {
				outputStream.write(bytes);
				outputStream.flush();
				outputStream.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
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
