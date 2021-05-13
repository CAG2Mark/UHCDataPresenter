package me.markng.uhcdatapresenter;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class Death {
	public String attacker;
	public String name;
	public String key;
	public String message;
	public Long time;
	public Death(TranslatableText translatableText) {
		String killed=((BaseText) translatableText.getArgs()[0]).asString();
		String attacker = "";
		if(translatableText.getArgs().length>1) {
			Object textPart = translatableText.getArgs()[1];
			if (textPart instanceof TranslatableText) attacker = I18n.translate(((TranslatableText) textPart).getKey());
			if (textPart instanceof LiteralText) attacker = ((LiteralText) textPart).getString();
		} else attacker=translatableText.getKey();
		this.attacker=attacker;
		this.name=killed;
		this.key=translatableText.getKey();
		this.message=translatableText.getString();
		this.time=(System.currentTimeMillis() / 1000L);
	}
}