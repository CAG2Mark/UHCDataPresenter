package me.markng.uhcdatapresenter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;

public class PlayerInfo {
	public String name;
	public String team="unset";
	public int health;
	public PlayerInfo(PlayerListEntry player) {
		name=player.getProfile().getName();
		if(player.getScoreboardTeam()!=null) team=player.getScoreboardTeam().getDisplayName().getString();
		if(MinecraftClient.getInstance().world==null) {
			System.out.println("NO WORLD LOADED???");
			return;
		}
		Scoreboard scoreboard= MinecraftClient.getInstance().world.getScoreboard();
		health=scoreboard.getPlayerScore(name,scoreboard.getObjectiveForSlot(0)).getScore();
	}

	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\", \"team\":\"" + team +"\", \"health\":\"" + health + "\"}";
	}
}
