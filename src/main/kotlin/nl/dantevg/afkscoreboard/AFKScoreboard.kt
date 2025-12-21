package nl.dantevg.afkscoreboard

import com.sakuraryoko.afkplus.api.AfkPlusEvents
import net.fabricmc.api.ModInitializer
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.scoreboard.ScoreboardCriterion
import net.minecraft.scoreboard.ScoreboardObjective
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

object AFKScoreboard : ModInitializer {
	const val MOD_ID = "afkscoreboard"
	const val OBJECTIVE_NAME = "afk"
	
	private val logger = LoggerFactory.getLogger(MOD_ID)
	
	override fun onInitialize() {
		AfkPlusEvents.AFK_EVENT.register { player, reason -> onAfkStatusChange(player, true) }
		AfkPlusEvents.AFK_RETURN_EVENT.register { player, duration -> onAfkStatusChange(player, false) }
	}
	
	private fun onAfkStatusChange(player: ServerPlayerEntity, isAfk: Boolean) {
		val objective = getObjective(player)
		val score = player.entityWorld.scoreboard.getOrCreateScore(player, objective)
		score.score = if (isAfk) 1 else 0
	}
	
	private fun getObjective(player: ServerPlayerEntity): ScoreboardObjective =
		player.entityWorld.scoreboard.getNullableObjective(OBJECTIVE_NAME)
			?: initObjective(player.entityWorld.scoreboard)
	
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
