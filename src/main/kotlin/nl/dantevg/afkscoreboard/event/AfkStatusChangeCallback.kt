package nl.dantevg.afkscoreboard.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity

fun interface AfkStatusChangeCallback {
	companion object {
		val EVENT: Event<AfkStatusChangeCallback> =
			EventFactory.createArrayBacked(AfkStatusChangeCallback::class.java) { listeners ->
				AfkStatusChangeCallback { player, isAfk ->
					listeners.forEach { it.interact(player, isAfk) }
				}
			}
	}
	
	/**
	 * Runs when a player's AFK status changes.
	 *
	 * @param player The player whose AFK status changed.
	 * @param isAfk Whether the player is now AFK.
	 * @see AfkStatusChangeCallback
	 */
	fun interact(player: ServerPlayerEntity, isAfk: Boolean)
}
