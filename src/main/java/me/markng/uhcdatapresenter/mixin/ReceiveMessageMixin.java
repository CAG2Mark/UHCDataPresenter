package me.markng.uhcdatapresenter.mixin;

import me.markng.uhcdatapresenter.DataAPI;
import me.markng.uhcdatapresenter.UHCDataPresenter;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ReceiveMessageMixin {
	@Inject(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("TAIL"))
	public void addMessage(Text text, int messageId, CallbackInfo info) {
		System.out.println(text);
		if(text instanceof TranslatableText) {
			TranslatableText translatableText=(TranslatableText) text;
			if(!translatableText.getKey().contains("death")) return;
			String killed=((BaseText) translatableText.getArgs()[0]).asString();

			if (killed.isEmpty()) {
				Object textPart = translatableText.getArgs()[0];
				if (textPart instanceof TranslatableText) killed = I18n.translate(((TranslatableText) textPart).getKey());
				if (textPart instanceof LiteralText) killed = ((LiteralText) textPart).getString();
			}

			String attacker = "";
			if(translatableText.getArgs().length>1) {
				Object textPart = translatableText.getArgs()[1];
				if (textPart instanceof TranslatableText) attacker = I18n.translate(((TranslatableText) textPart).getKey());
				if (textPart instanceof LiteralText) attacker = ((LiteralText) textPart).getString();
			} else attacker=translatableText.getKey();

			String death = "{\"attacker\":\""+attacker+"\",\"name\":\""+killed+"\",\"key\":\""+translatableText.getKey()+"\",\"message\":\""+translatableText.getString()+
					"\",\"time\":" + (System.currentTimeMillis() / 1000L) + "}";
			UHCDataPresenter.api.addDeath(death);
			System.out.println("Added death: " + death);
		}
	}
}