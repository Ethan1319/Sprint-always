package io.github.Ethan1319.alwaysSprint

import org.bukkit.plugin.java.JavaPlugin
import  org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

class AlwaysSprint : JavaPlugin(), Listener {

    private val autoSprintEnabled = mutableSetOf<Player>()

    override fun onEnable() {
        class AlwaysSprintPlugin : JavaPlugin(), Listener {
            override fun onEnable() {
                Bukkit.getPluginManager().registerEvents(this, this)
                logger.info("Always Sprint 이 활성화 되었습니다.")
            }

            override fun onCommand(
                sender: CommandSender,
                command: Command,
                label: String,
                args: Array<out String>
            ): Boolean {
                if (sender is Player) {
                    if (command.name.equals("toggleautosprint", ignoreCase = true)) {
                        if (autoSprintEnabled.contains(sender)) {
                            autoSprintEnabled.remove(sender)
                            sender.sendMessage("§c[자동 달리기] 비활성화됨.")
                        } else {
                            autoSprintEnabled.add(sender)
                            sender.sendMessage("§a[자동 달리기] 활성화됨.")
                        }
                        return true
                    }
                } else {
                    sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.")
                }
                return false
            }
        }

        @EventHandler

        fun onPlayerMove(event: PlayerMoveEvent) {
            val player = event.player
            if (!player.isSprinting &&
                !player.isSneaking &&
                player.foodLevel > 6 &&
                player.location.block.blockData.material.isSolid &&
                player.velocity.length() > 0.05
            ) {
                player.isSprinting = true
            }
        }
    }
}


