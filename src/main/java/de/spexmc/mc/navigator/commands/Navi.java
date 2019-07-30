package de.spexmc.mc.navigator.commands;

import de.spexmc.mc.navigator.storage.Data;
import de.spexmc.mc.navigator.storage.Messages;
import de.spexmc.mc.navigator.util.Messenger;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Lara on 26.02.2019 for navigator
 * /testcommand
 */
public class Navi implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
    if (checkPlayer(sender) && checkWaypointsExists(sender)) {
      final Player playerSender = (Player) sender;
      if (NavigatorManager.checkNaviNotInInventory(playerSender)) {
        playerSender.getInventory().addItem(NavigatorManager.getNavi());
      }
    }
    return false;
  }

  private boolean checkWaypointsExists(CommandSender sender) {
    if (Data.getInstance().getWaypoints().isEmpty()) {
      Messenger.sendMessage((Player) sender, Messages.NO_WAYPOINTS_CREATED);
      return false;
    }
    return true;
  }

  private boolean checkPlayer(CommandSender sender) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Messages.PREFIX + Messages.NOT_A_PLAYER);
      return false;
    }
    return true;
  }
}
