package nl.dantevg.afkscoreboard

import net.fabricmc.api.ModInitializer
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.scoreboard.ScoreboardCriterion
import net.minecraft.scoreboard.ScoreboardObjective
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import nl.dantevg.afkscoreboard.event.AfkStatusChangeCallback
import org.slf4j.LoggerFactory

object AFKScoreboard : ModInitializer {
	const val MOD_ID = "afkscoreboard"
	const val OBJECTIVE_NAME = "afk"
	
	private val logger = LoggerFactory.getLogger(MOD_ID)
	
	override fun onInitialize() {
		AfkStatusChangeCallback.EVENT.register(::onAfkStatusChange)
	}
	
	private fun onAfkStatusChange(player: ServerPlayerEntity, isAfk: Boolean) {
		val objective = getObjective(player)
		val score = player.scoreboard.getOrCreateScore(player, objective)
		score.score = if (isAfk) 1 else 0
	}
	
	private fun getObjective(player: ServerPlayerEntity): ScoreboardObjective =
		player.scoreboard.getNullableObjective(OBJECTIVE_NAME) ?: initObjective(player.scoreboard)
	
	private fun initObjective(scoreboard: Scoreboard): ScoreboardObjective =
		scoreboard.addObjective(
			OBJECTIVE_NAME,
			ScoreboardCriterion.DUMMY,
			Text.literal("AFK"),
			ScoreboardCriterion.RenderType.INTEGER,
			true,
			null
		)
}
