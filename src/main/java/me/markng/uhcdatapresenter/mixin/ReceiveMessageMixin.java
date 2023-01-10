package me.markng.uhcdatapresenter.mixin;

import me.markng.uhcdatapresenter.Death;
import me.markng.uhcdatapresenter.UHCDataPresenter;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ChatHud.class, priority = 1100) //high priority, because advancedChat overwrites it: https://github.com/DarkKronicle/AdvancedChat/blob/v3/src/main/java/io/github/darkkronicle/advancedchat/mixin/MixinChatHud.java
public class ReceiveMessageMixin {
	@Inject(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"))
	public void addMessage(Text text, int messageId, CallbackInfo info) {

		List<Text> siblings = text.getSiblings();
		String msg;
		if (siblings.isEmpty()) {
			msg = text.getString();
		}
		else {
			msg = text.getSiblings().get(0).getString();
		}

		if(text.getContent() instanceof TranslatableTextContent translatableText) {
			if(!translatableText.getKey().contains("death")) return;
			Death death=new Death(translatableText, msg);
			UHCDataPresenter.api.addDeath(death);
			return;
		}

		// check if UHC started
		if (msg.startsWith("All your chat messages now appear in team chat.")) {
			UHCDataPresenter.api.setStarted(true);
		}
	}
}
