package me.markng.uhcdatapresenter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class UHCDataPresenter implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "uhc-data-presenter";
    public static final String MOD_NAME = "UHCDataPresenter";

    public static DataAPI api = new DataAPI();

    public static String playerName;

    @Override
    public void onInitialize() {

        log(Level.INFO, "Initializing");

        // reset command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    literal("resetpresenter").executes(context -> {
            UHCDataPresenter.api.reset();

            // send message
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.inGameHud.getChatHud().addMessage(Text.literal("Reset the UHC Data Presenter."));

            return 1;
        }));});

        // force send uhc stopped message
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("forcestopuhc").executes(context -> {
            // send message
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null)
                mc.inGameHud.getChatHud().addMessage(Text.literal("Force stopped the UHC."));

            api.setStarted(false);

            return 1;
        }));});

        // force send uhc started message
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("forcestartuhc").executes(context -> {
            UHCDataPresenter.api.reset();

            // send message
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null)
                mc.inGameHud.getChatHud().addMessage(Text.literal("Force started the UHC."));

            api.setStarted(true);

            return 1;
        }));});

        try {
            api.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}