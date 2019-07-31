package de.spexmc.mc.navigator.commands;

import de.spexmc.mc.navigator.storage.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Lara on 31.07.2019 for navigator
 */
class CommandUtils {

  boolean checkPlayer(CommandSender sender) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Messages.PREFIX + Messages.NOT_A_PLAYER);
      return false;
    }
    return true;
  }

}
