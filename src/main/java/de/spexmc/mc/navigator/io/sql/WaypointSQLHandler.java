package de.spexmc.mc.navigator.io.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.storage.Const;
import de.spexmc.mc.navigator.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by Lara on 29.07.2019 for navigator
 */
class WaypointSQLHandler extends SQLConnector {

  List<WaypointModel> getWaypoints() {
    final List<WaypointModel> waypoints = new ArrayList<>();
    try (final PreparedStatement statement = getConnection().prepareStatement(
        "SELECT name, typename, x, y, z, world FROM " + Const.WAYPOINTSTABLE);
         final ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        waypoints.add(determineWaypoint(resultSet));
      }

    } catch (final SQLException ex) {
      Messenger.administratorMessage(ex.getMessage());
    }
    return waypoints;
  }

  public void setWaypoint(WaypointModel waypoint) {
    try (final PreparedStatement statement = getConnection().prepareStatement(
        "INSERT INTO " + Const.WAYPOINTSTABLE + "(name, typename, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?)")) {
      statement.setString(1, waypoint.getName());
      statement.setString(2, waypoint.getType().name());
      statement.setDouble(3, waypoint.getX());
      statement.setDouble(4, waypoint.getY());
      statement.setDouble(5, waypoint.getZ());
      statement.setString(6, waypoint.getWorldName());
    } catch (SQLException ex) {
      Messenger.administratorMessage(ex.getMessage());
    }
  }

  public void removeWaypoint(WaypointModel waypoint) {
    try (final PreparedStatement statement = getConnection().prepareStatement(
        "DELETE FROM " + Const.WAYPOINTSTABLE + " WHERE name = ?")) {
      statement.setString(1, waypoint.getName());
    } catch (SQLException ex) {
      Messenger.administratorMessage(ex.getMessage());
    }
  }

  private WaypointModel determineWaypoint(ResultSet resultSet) throws SQLException {
    final String waypointName = resultSet.getString(1);
    final Material shownMaterial = Material.getMaterial(resultSet.getString(2));
    final double x = resultSet.getDouble(3);
    final double y = resultSet.getDouble(4);
    final double z = resultSet.getDouble(5);
    final String worldName = resultSet.getString(6);
    final Location waypointLocation = new Location(Bukkit.getWorld(worldName), x, y, z);
    return new WaypointModel(waypointName, waypointLocation, shownMaterial);
  }
}
