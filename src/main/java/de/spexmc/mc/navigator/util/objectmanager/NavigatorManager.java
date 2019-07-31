package de.spexmc.mc.navigator.util.objectmanager;

import java.util.List;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.storage.Const;
import de.spexmc.mc.navigator.storage.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Lara on 29.07.2019 for navigator
 */
public final class NavigatorManager {

  public static WaypointModel determineWaypoint(final String name) {
    final List<WaypointModel> waypoints = Data.getInstance().getWaypoints();
    return waypoints.stream().filter(waypoint -> waypoint.getName().equalsIgnoreCase(name))
        .findAny()
        .orElse(null);
  }

  public static WaypointModel determineNearestWaypoint(Player player) {
    final double minDistance = Double.MAX_VALUE;
    WaypointModel shortest = null;
    for (WaypointModel waypoint : Data.getInstance().getWaypoints()) {
      final Location location = waypoint.getLocation();
      final double distance = location.distance(player.getLocation());
      if (distance < minDistance) {
        shortest = waypoint;
      }
    }
    return shortest;
  }

  public static Inventory determineInventory() {
    Data.getInstance().getSql().updateOnStart();

    final List<WaypointModel> waypoints = Data.getInstance().getWaypoints();
    final int size = waypoints.size() / 9 * 9 + 9;
    final Inventory inventory = Bukkit.createInventory(null, size, Const.NAVIGATOR_TITLE);
    waypoints.stream().map(WaypointModel::getItem).forEach(inventory::addItem);
    return inventory;
  }

  public static boolean checkNaviNotInInventory(Player player) {
    for (ItemStack item : player.getInventory().getContents()) {
      if (item != null && item.equals(getNavi())) {
        return false;
      }
    }
    return true;
  }

  public static ItemStack getNavi() {
    final ItemStack item = new ItemStack(Material.COMPASS);
    final ItemMeta info = item.getItemMeta();
    info.setDisplayName(Const.NAVIGATOR_TITLE);
    item.setItemMeta(info);
    return item;
  }

  public static boolean isItemANavigatorItem(ItemStack item) {
    final List<WaypointModel> waypoints = Data.getInstance().getWaypoints();
    return waypoints.stream().anyMatch(waypoint -> waypoint.getItem().equals(item));
  }

}
