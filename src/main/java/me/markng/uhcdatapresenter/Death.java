package me.markng.uhcdatapresenter;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.TranslatableTextContent;

public class Death {
	public String attacker;
	public String name;
	public String key;
	public String message;
	public Long time;
	public Death(TranslatableTextContent translatableText) {
		String killed=((BaseText) translatableText.getArgs()[0]).getContent();
		String attacker = "";
		if(translatableText.getArgs().length>1) {
			Object textPart = translatableText.getArgs()[1];
			if (textPart instanceof TranslatableTextContent) attacker = I18n.translate(((TranslatableTextContent) textPart).getKey());
			if (textPart instanceof LiteralTextContent) attacker = ((LiteralTextContent) textPart).getString();
		} else attacker=translatableText.getKey();
		if (killed.isEmpty()) {
			Object textPart = translatableText.getArgs()[0];
			if (textPart instanceof TranslatableTextContent) killed = I18n.translate(((TranslatableTextContent) textPart).getKey());
			if (textPart instanceof LiteralTextContent) killed = ((LiteralTextContent) textPart).getString();
		}
		this.attacker=attacker;
		this.name=killed;
		this.key=translatableText.getKey();
		this.message=translatableText.getString();
		this.time=(System.currentTimeMillis() / 1000L);
	}
}
