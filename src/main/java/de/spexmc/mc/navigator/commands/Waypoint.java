package de.spexmc.mc.navigator.commands;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.storage.Const;
import de.spexmc.mc.navigator.storage.Messages;
import de.spexmc.mc.navigator.util.Messenger;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lara on 29.07.2019 for navigator
 */
public class Waypoint extends CommandUtils implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (checkPlayer(sender) && sender.isOp() && checkSyntax((Player) sender, args) &&
        checkLength(args, (Player) sender) && checkItem((Player) sender)) {
      final String name = determineName(args);
      if (args[0].equalsIgnoreCase("create") && NavigatorManager.determineWaypoint(name) == null) {
        evaluateCreateWaypoint((Player) sender, name);
      } else if (args[0].equalsIgnoreCase("delete") && NavigatorManager.determineWaypoint(name) != null) {
        evaluateDeleteWaypoint((Player) sender, name);
      } else if (args[0].equalsIgnoreCase("view") && NavigatorManager.determineWaypoint(name) != null) {
        evaluateViewWaypoint((Player) sender, name);
      }
    }
    return false;
  }

  private void evaluateViewWaypoint(Player sender, String name) {
    final WaypointModel waypoint = NavigatorManager.determineWaypoint(name);
    waypoint.view(sender);
  }

  private void evaluateDeleteWaypoint(Player sender, String name) {
    final WaypointModel waypoint = NavigatorManager.determineWaypoint(name);
    waypoint.remove();
    Messenger.sendMessage(sender, Messages.WAYPOINT_DELETED);
  }

  private void evaluateCreateWaypoint(Player sender, String name) {
    final ItemStack itemInHand = sender.getInventory().getItemInMainHand();
    final WaypointModel waypoint = new WaypointModel(name, sender.getLocation(), itemInHand.getType());
    waypoint.create();
    Messenger.sendMessage(sender, Messages.WAYPOINT_CREATED);
  }

  private String determineName(String[] args) {
    final StringJoiner name = new StringJoiner(" ");
    Arrays.stream(args, 1, args.length).forEach(name::add);
    return name.toString();
  }

  private boolean checkItem(Player sender) {
    final ItemStack itemInHand = sender.getInventory().getItemInMainHand();
    if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) {
      Messenger.sendMessage(sender, Messages.NO_ITEM_IN_HAND);
      return false;
    }
    return true;
  }

  private boolean checkLength(String[] args, Player player) {
    final int length = IntStream.range(1, args.length).map(i -> args[i].length()).sum();
    if (length > Const.NAME_LENGTH) {
      Messenger.sendMessage(player, Messages.NAME_TOO_LONG);
      return false;
    }
    return true;
  }

  private boolean checkSyntax(Player sender, String[] args) {
    if (args.length < 2) {
      Messenger.sendMessage(sender, Messages.SYNTAX_WAYPOINT);
      return false;
    }
    return true;
  }

}
