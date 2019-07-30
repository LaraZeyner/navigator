package de.spexmc.mc.navigator.commands;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.TeleportCommand;
import org.bukkit.entity.Player;

/**
 * Created by Lara on 30.07.2019 for navigator
 */
public class Tp implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender.isOp() && args.length == 1) {
      final WaypointModel waypoint = NavigatorManager.determineWaypoint(args[0]);
      if (waypoint != null) {
        ((Player) sender).teleport(waypoint.getLocation());

      } else {
        final Player target = Bukkit.getPlayer(args[0]);

        if (target != null) {
          ((Player) sender).teleport(target.getLocation());

        } else {
          new TeleportCommand().execute(sender, command.getName(), args);
        }
      }
    }

    return false;
  }
}
