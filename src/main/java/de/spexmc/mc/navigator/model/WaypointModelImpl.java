package de.spexmc.mc.navigator.model;

import java.io.Serializable;
import java.util.Objects;

import org.bukkit.Location;

/**
 * Created by Lara on 29.07.2019 for navigator
 */
public class WaypointModelImpl implements Serializable {
  private static final long serialVersionUID = -424658313503942980L;

  private final double x, y, z;
  private final String name, worldName;

  WaypointModelImpl(String name, Location location) {
    this.x = location.getX();
    this.y = location.getY();
    this.z = location.getZ();
    this.name = name;
    this.worldName = location.getWorld().getName();
  }

  //<editor-fold desc="getter and setter">
  public String getName() {
    return name;
  }

  public String getWorldName() {
    return worldName;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }
  //</editor-fold>

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WaypointModelImpl)) return false;
    final WaypointModelImpl waypoint = (WaypointModelImpl) o;
    return Double.compare(waypoint.x, x) == 0 && Double.compare(waypoint.y, y) == 0 &&
        Double.compare(waypoint.z, z) == 0 && name.equals(waypoint.name) && worldName.equals(waypoint.worldName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z, name, worldName);
  }

  @Override
  public String toString() {
    return "Wegpunkt: " + name + "\n" +
        "   Welt -> " + worldName + "\n" +
        "      x -> " + x + "\n" +
        "      y -> " + z + "\n" +
        "      z -> " + z;
  }
}