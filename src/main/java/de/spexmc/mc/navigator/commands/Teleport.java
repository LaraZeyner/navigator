package de.spexmc.mc.navigator.commands;

import java.util.Arrays;
import java.util.StringJoiner;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Lara on 30.07.2019 for navigator
 */
public class Teleport extends CommandUtils implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender.isOp() && args.length == 1 && checkPlayer(sender)) {
      final WaypointModel waypoint = NavigatorManager.determineWaypoint(args[0]);
      if (waypoint != null) {
        ((Player) sender).teleport(waypoint.getLocation());
        return true;

      } else {
        final Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
          ((Player) sender).teleport(target.getLocation());
          return true;
        }
      }
    }

    performDefaultTp((Player) sender, args);

    return false;
  }


  private void performDefaultTp(Player sender, String[] args) {
    final StringJoiner arguments = new StringJoiner(" ");
    Arrays.stream(args).forEach(arguments::add);

    sender.performCommand("/tp " + arguments);
  }
}
