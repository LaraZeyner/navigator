package de.spexmc.mc.navigator.listener;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.storage.Const;
import de.spexmc.mc.navigator.util.Messenger;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Lara on 26.02.2019 for navigator
 */
public class InventoryEvents implements Listener {

  @EventHandler
  public void onInteract(PlayerInteractEvent interactEvent) {
    if ((interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
        interactEvent.getAction().equals(Action.RIGHT_CLICK_AIR)) &&
        interactEvent.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Const.NAVIGATOR_TITLE)) {
      interactEvent.getPlayer().openInventory(NavigatorManager.determineInventory());
    }
  }

  @EventHandler
  public void onInventoryInteract(InventoryInteractEvent interactEvent) {
    if (interactEvent.getInventory().getTitle().equals(Const.NAVIGATOR_TITLE)) {
      if (interactEvent instanceof InventoryClickEvent) {
        performClickedOnItem((InventoryClickEvent) interactEvent);
      } else {
        interactEvent.setCancelled(true);
      }
    }

    final HumanEntity humanEntity = interactEvent.getWhoClicked();
    if (humanEntity.getItemInHand().getItemMeta().getDisplayName().equals(Const.NAVIGATOR_TITLE)) {
      humanEntity.openInventory(NavigatorManager.determineInventory());
    }

  }

  private void performClickedOnItem(InventoryClickEvent clickEvent) {
    final String locationName = clickEvent.getCurrentItem().getItemMeta().getDisplayName();
    final WaypointModel waypoint = NavigatorManager.determineWaypoint(locationName);
    final Player target = (Player) clickEvent.getWhoClicked();
    target.setCompassTarget(waypoint.getLocation());
    target.closeInventory();
    Messenger.sendMessage(target, "Dein Navi§b SpexMC GPS 100§7 wurde auf den Stadtteil §c" + locationName + "§7 eingestellt.");
  }
}
