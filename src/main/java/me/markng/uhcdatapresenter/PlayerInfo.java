package me.markng.uhcdatapresenter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;

import org.apache.commons.lang3.StringUtils;

public class PlayerInfo {
	public String name;
	public String teamColor = "#ffffff";
	public int health;

	public PlayerInfo(PlayerListEntry player) {
		MinecraftClient instance = MinecraftClient.getInstance();

		name = player.getProfile().getName();
		try {
			if (player.getScoreboardTeam() != null && player.getScoreboardTeam().getColor() != null
					&& player.getScoreboardTeam().getColor().getColorValue() != null) {
				teamColor = "#" + StringUtils.leftPad(
						Integer.toHexString(player.getScoreboardTeam().getColor().getColorValue()),
						6, '0');
			}
		} catch (NullPointerException e) {
			// mojang bad
			teamColor = "#ffffff";
		}
		if (instance.world == null) {
			return;
		}

		Scoreboard scoreboard = instance.world.getScoreboard();
		var score = scoreboard.getScore(ScoreHolder.fromName(name), scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.LIST));
		if (score != null)
			health = score.getScore();
	}
}
