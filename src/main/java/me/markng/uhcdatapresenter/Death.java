package me.markng.uhcdatapresenter;

import net.minecraft.client.resource.language.I18n;

import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mutable;

public class Death {
	public String attacker;
	public String name;
	public String key;
	public String message;
	public Long time;
	public Death(TranslatableTextContent translatableText, String message) {
		System.out.println("death received");
		String killed= "";
		String attacker = "";

		Object killedPart = translatableText.getArgs()[0];

		if (killedPart instanceof TranslatableTextContent killedText)
			killed = I18n.translate((killedText).getKey());
		else if (killedPart instanceof LiteralTextContent killedText)
			killed = killedText.string();
		else if (killedPart instanceof MutableText killedText)
			killed = killedText.getString();

		if (translatableText.getArgs().length > 1) {
			Object attackerPart = translatableText.getArgs()[1];

			if (attackerPart instanceof TranslatableTextContent attackerText)
				attacker = I18n.translate(attackerText.getKey());
			else if (attackerPart instanceof LiteralTextContent attackerText)
				attacker = attackerText.string();
			else if (attackerPart instanceof MutableText attackerText)
				attacker = attackerText.getString();
		} else {
			attacker=translatableText.getKey();
		}

		this.attacker=attacker;
		this.name=killed;
		this.key=translatableText.getKey();
		this.message=message;
		this.time=(System.currentTimeMillis() / 1000L);


	}
}
