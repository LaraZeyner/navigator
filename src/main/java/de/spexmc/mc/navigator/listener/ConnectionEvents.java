package de.spexmc.mc.navigator.listener;

import de.spexmc.mc.navigator.storage.Data;
import de.spexmc.mc.navigator.storage.Messages;
import de.spexmc.mc.navigator.util.Messenger;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Lara on 30.07.2019 for navigator
 */
public class ConnectionEvents implements Listener {
  @EventHandler
  public void onJoin(PlayerJoinEvent joinEvent) {
    final Player eventPlayer = joinEvent.getPlayer();
    if (NavigatorManager.checkNaviNotInInventory(eventPlayer) && checkWaypointsExists(eventPlayer)) {
      eventPlayer.getInventory().addItem(NavigatorManager.getNavi());
    }
  }

  private boolean checkWaypointsExists(Player sender) {
    if (Data.getInstance().getWaypoints().isEmpty()) {
      Messenger.sendMessage(sender, Messages.NO_WAYPOINTS_CREATED);
      return false;
    }
    return true;
  }

}
