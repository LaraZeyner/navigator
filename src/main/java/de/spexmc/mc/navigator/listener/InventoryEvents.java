package de.spexmc.mc.navigator.listener;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.storage.Const;
import de.spexmc.mc.navigator.util.Messenger;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

/**
 * Created by Lara on 26.02.2019 for navigator
 */
public class InventoryEvents implements Listener {

  @EventHandler
  public void onInventoryClick(InventoryMoveItemEvent moveItemEvent) {

  }

  @EventHandler
  public void onInventoryClick2(InventoryInteractEvent interactEvent) {
    if (interactEvent.getInventory().getTitle().equals(Const.NAVIGATOR_TITLE)) {
      if (interactEvent instanceof InventoryClickEvent) {
        performClickedOnItem((InventoryClickEvent) interactEvent);
      } else {
        interactEvent.setCancelled(true);
      }
    }

  }

  private void performClickedOnItem(InventoryClickEvent clickEvent) {
    final String locationName = clickEvent.getCurrentItem().getItemMeta().getDisplayName();
    final WaypointModel waypoint = NavigatorManager.determineWaypoint(locationName);
    final Player target = (Player) clickEvent.getWhoClicked();
    target.teleport(waypoint.getLocation());
    target.closeInventory();
    Messenger.sendMessage(target, "Du wurdest zum Stadtteil §b" + locationName + "§7 teleportiert.");
  }
}
