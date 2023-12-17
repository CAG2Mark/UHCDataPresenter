package me.markng.uhcdatapresenter.mixin;

import me.markng.uhcdatapresenter.DataAPI;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class HeaderFooter {
	@Inject(method = "onPlayerListHeader(Lnet/minecraft/network/packet/s2c/play/PlayerListHeaderS2CPacket;)V", at = @At("HEAD"))
	public void onPlayerListHeader(PlayerListHeaderS2CPacket packet, CallbackInfo ci) {
		if (!packet.getHeader().getString().isEmpty())
			DataAPI.sendMessage("{\"header\":\"" + packet.getHeader().getString().replace("\n", "<br>") + "\"}");
		if (!packet.getFooter().getString().isEmpty())
			DataAPI.sendMessage("{\"footer\":\"" + packet.getFooter().getString().replace("\n", "<br>") + "\"}");

	}
}
