package me.markng.uhcdatapresenter;
import ca.weblite.objc.Client;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class UHCDataPresenter implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "uhc-data-presenter";
    public static final String MOD_NAME = "UHCDataPresenter";

    public static DataAPI api = new DataAPI();


    public static String playerName;

    @Override
    public void onInitialize() {

        log(Level.INFO, "Initializing");

        ClientCommandManager.DISPATCHER.register(literal("resetpresenter").executes(context -> {
            UHCDataPresenter.api.reset();

            // send message
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("Reset the UHC Data Presenter."), mc.player.getUuid());

            return 1;
        }));

        try {
            api.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // initialise commands

        //TODO: Initializer
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                ClientPlayerEntity thisPlayer=MinecraftClient.getInstance().player;
                if(thisPlayer==null) return;
                playerName = thisPlayer.getName().asString();

                // init stream
                Supplier<Stream<PlayerInfo>> playerStream = () -> thisPlayer.networkHandler.getPlayerList().stream()
                        .filter(player->player.getDisplayName()==null)//remove BTLP2ebb60ef
                        .map(PlayerInfo::new);

                Stream<PlayerInfo> str = playerStream.get();
                Object[] x = str.toArray();

                // intermediary step to get the info of the current player
                PlayerInfo cur = playerStream.get().filter(p -> p.name.equals(playerName)).findFirst().orElse(null);

                if (cur != null) {
                    api.setCurPlayer(cur.toString());
                }

                String playerString="["+
                        playerStream.get().map(Object::toString)
                        .collect(Collectors.joining(","))+"]";
                api.setPlayers("\"players\":"+playerString+"");
            }
        },0,50); //This is just for testing.
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}