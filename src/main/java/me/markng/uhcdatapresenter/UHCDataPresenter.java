package me.markng.uhcdatapresenter;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

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
                SendToBrowser.sendMessage("test");
            }
        },0,5000); //This is just for testing.
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}