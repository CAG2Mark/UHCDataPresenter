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
		if (!packet.header().getString().isEmpty())
			DataAPI.sendMessage("{\"header\":\"" + packet.header().getString().replace("\n", "<br>") + "\"}");
		if (!packet.footer().getString().isEmpty())
			DataAPI.sendMessage("{\"footer\":\"" + packet.header().getString().replace("\n", "<br>") + "\"}");

	}
}
