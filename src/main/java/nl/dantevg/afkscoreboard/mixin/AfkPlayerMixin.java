package nl.dantevg.afkscoreboard.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import nl.dantevg.afkscoreboard.event.AfkStatusChangeCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayerEntity.class, priority = 1500)
public abstract class AfkPlayerMixin {
	@TargetHandler(
			mixin = "com.github.sakuraryoko.afkplus.mixin.ServerPlayerEntityMixin",
			name = "setAfk"
	)
	@Inject(method = "@MixinSquared:Handler", at = @At("TAIL"))
	private void setAfk(boolean isAfk, CallbackInfo ci) {
		AfkStatusChangeCallback.Companion.getEVENT().invoker().interact((ServerPlayerEntity) (Object) this, isAfk);
	}
}
