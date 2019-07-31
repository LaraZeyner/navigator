package de.spexmc.mc.navigator.model;

import java.util.Arrays;

import de.spexmc.mc.navigator.storage.Data;
import de.spexmc.mc.navigator.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Lara on 29.07.2019 for navigator
 */
public class WaypointModel extends WaypointModelImpl {
  private static final long serialVersionUID = -1820083224071241661L;

  private final Material type;

  public WaypointModel(String name, Location location, Material type) {
    super(name, location);
    this.type = type;
  }

  public ItemStack getItem() {
    final ItemStack item = new ItemStack(type);
    item.setAmount(1);
    final ItemMeta info = item.getItemMeta();
    info.setDisplayName(getName());
    info.setLore(Arrays.asList("Welt:" + getWorldName(), "X -> " + getX(), "Y -> " + getY(), "Z -> " + getZ(), "§1 Klicken zum Teleportieren."));
    item.setItemMeta(info);
    return item;
  }

  public Location getLocation() {
    return new Location(Bukkit.getWorld(getWorldName()), getX(), getY(), getZ());
  }

  public Material getType() {
    return type;
  }

  public void create() {
    Data.getInstance().getWaypoints().add(this);
    Data.getInstance().getSql().setWaypoint(this);
  }

  public void remove() {
    Data.getInstance().getWaypoints().remove(this);
    Data.getInstance().getSql().removeWaypoint(this);
  }

  public void view(Player player) {
    Messenger.sendMessage(player, toString());
    Messenger.sendMessage(player, "§5Klicken zum Teleportieren", "/tp " + getName());
  }
}
