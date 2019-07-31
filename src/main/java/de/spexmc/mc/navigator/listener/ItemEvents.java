package de.spexmc.mc.navigator.listener;

import de.spexmc.mc.navigator.util.objectmanager.NavigatorManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lara on 30.07.2019 for navigator
 */
public class ItemEvents implements Listener {
  @EventHandler
  public void onCraft(CraftItemEvent craftItemEvent) {
    if (craftItemEvent.getRecipe().getResult().getType().equals(Material.COMPASS)) {
      craftItemEvent.setCancelled(true);
    }
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent dropItemEvent) {
    final ItemStack droppedItem = dropItemEvent.getItemDrop().getItemStack();
    if (NavigatorManager.isItemANavigatorItem(droppedItem) || droppedItem.equals(NavigatorManager.getNavi())) {
      dropItemEvent.setCancelled(true);
    }
  }

  @EventHandler
  public void onInventoryMove(InventoryMoveItemEvent moveItemEvent) {
    if (NavigatorManager.isInventoryNavigator(moveItemEvent.getInitiator()) ||
        NavigatorManager.isInventoryNavigator(moveItemEvent.getDestination())) {
      moveItemEvent.setCancelled(true);
    }

    if (moveItemEvent.getItem().equals(NavigatorManager.getNavi()) &&
        moveItemEvent.getDestination() != moveItemEvent.getInitiator()) {
      moveItemEvent.setCancelled(true);
    }
  }
}
