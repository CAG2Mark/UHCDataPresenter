package me.markng.uhcdatapresenter;

import net.minecraft.text.TranslatableText;

public class Death {
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