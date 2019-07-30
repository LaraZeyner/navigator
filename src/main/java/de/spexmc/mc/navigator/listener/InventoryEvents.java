package de.spexmc.mc.navigator.listener;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.util.Messenger;
import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lara on 26.02.2019 for navigator
 */
public class InventoryEvents implements Listener {
  @EventHandler
  public void onInteract(PlayerInteractEvent interactEvent) {
    final Player eventPlayer = interactEvent.getPlayer();
    final ItemStack itemInHand = eventPlayer.getItemInHand();
    if (itemInHand.equals(NavigatorManager.getNavi())) {
      eventPlayer.openInventory(NavigatorManager.determineInventory());
    }
  }

  @EventHandler
  public void onInventoryDrag(InventoryDragEvent dragEvent) {
    if (dragEvent.getInventory().equals(NavigatorManager.determineInventory()) ||
        dragEvent.getCursor().equals(NavigatorManager.getNavi()) ||
        dragEvent.getOldCursor().equals(NavigatorManager.getNavi())) {
      dragEvent.setCancelled(true);
    }
  }


  @EventHandler
  public void onInventoryClick(InventoryClickEvent clickEvent) {
    if (NavigatorManager.isItemANavigatorItem(clickEvent.getCursor())) {
      performClickedOnItem(clickEvent);
    }

    final HumanEntity humanEntity = clickEvent.getWhoClicked();
    final ItemStack itemInHand = humanEntity.getItemInHand();
    if (itemInHand.equals(NavigatorManager.getNavi())) {
      humanEntity.openInventory(NavigatorManager.determineInventory());
    }
  }

  private void performClickedOnItem(InventoryClickEvent clickEvent) {
    final String locationName = clickEvent.getCursor().getItemMeta().getDisplayName();
    final WaypointModel waypoint = NavigatorManager.determineWaypoint(locationName);
    final Player target = (Player) clickEvent.getWhoClicked();
    target.setCompassTarget(waypoint.getLocation());
    target.closeInventory();
    Messenger.sendMessage(target, "Dein Navi§b SpexNAV-100§7 wurde auf den Stadtteil §c" + locationName +
        "§7 eingestellt.");
  }
}
