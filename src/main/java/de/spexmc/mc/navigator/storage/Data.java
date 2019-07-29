package de.spexmc.mc.navigator.storage;

import java.util.ArrayList;
import java.util.List;

import de.spexmc.mc.navigator.io.sql.SQLManager;
import de.spexmc.mc.navigator.model.WaypointModel;

/**
 * Created by Lara on 26.02.2019 for navigator
 */
public final class Data {
  private static Data instance;
  private static boolean forceDisable;

  /**
   * Singleton-Muster: nur eine Instanz im gesamten Programm
   *
   * @return Instanz
   */
  public static Data getInstance() {
    if (instance == null) {
      instance = new Data();
    }
    return instance;
  }

  public static boolean isForceDisable() {
    return forceDisable;
  }

  public static void setForceDisable(boolean forceDisable) {
    Data.forceDisable = forceDisable;
  }

  private final List<WaypointModel> waypoints;
  private final SQLManager sql;

  private Data() {
    this.waypoints = new ArrayList<>();
    this.sql = new SQLManager();
    forceDisable = false;
  }

  //<editor-fold desc="getter and setter">
  public SQLManager getSql() {
    return sql;
  }

  public List<WaypointModel> getWaypoints() {
    return waypoints;
  }
  //</editor-fold>
}
