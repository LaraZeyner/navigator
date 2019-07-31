package de.spexmc.mc.navigator.io.sql;

import java.util.List;

import de.spexmc.mc.navigator.model.WaypointModel;
import de.spexmc.mc.navigator.storage.Data;

/**
 * Created by Lara on 13.01.2019 for navigator
 */
public class SQLManager extends WaypointSQLHandler {

  public SQLManager() {
    init(connect());
  }

  @Override
  public void disconnect() {
    super.disconnect();
  }

  public void updateOnStart() {
    final Data data = Data.getInstance();
    final List<WaypointModel> waypointList = data.getSql().getWaypoints();
    data.getWaypoints().addAll(waypointList);
  }
}
