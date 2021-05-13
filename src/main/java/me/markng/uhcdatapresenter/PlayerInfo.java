package me.markng.uhcdatapresenter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import org.apache.commons.lang3.StringUtils;

public class PlayerInfo {
	public String name;
	public String teamColor = "#ffffff";
	public int health;
	public PlayerInfo(PlayerListEntry player) {
		name=player.getProfile().getName();
		if(player.getScoreboardTeam()!=null && player.getScoreboardTeam().getColor() != null && player.getScoreboardTeam().getColor().getColorValue() != null) {
			teamColor = "#" + StringUtils.leftPad(
					Integer.toHexString(player.getScoreboardTeam().getColor().getColorValue()),
					6, '0');
		}
		if(MinecraftClient.getInstance().world==null) {
			System.out.println("NO WORLD LOADED???");
			return;
		}

		Scoreboard scoreboard= MinecraftClient.getInstance().world.getScoreboard();
		health=scoreboard.getPlayerScore(name,scoreboard.getObjectiveForSlot(0)).getScore();
	}

	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\", \"team\":\"" + teamColor +"\", \"health\":\"" + health + "\"}";
	}
}
