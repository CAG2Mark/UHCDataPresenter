package me.markng.uhcdatapresenter;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class UHCDataPresenter implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "uhc-data-presenter";
    public static final String MOD_NAME = "UHCDataPresenter";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        //TODO: Initializer
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if(MinecraftClient.getInstance().player==null) return;
                ClientPlayerEntity thisPlayer=MinecraftClient.getInstance().player;
                if(thisPlayer==null) return;
                String playerString="["+thisPlayer.networkHandler.getPlayerList().stream()
                        .map(PlayerInfo::new)
                        .map(Object::toString)
                        .collect(Collectors.joining(","))+"]";
                SendToBrowser.sendMessage(playerString);
            }
        },0,1000); //This is just for testing.
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}