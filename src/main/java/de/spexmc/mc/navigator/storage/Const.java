package de.spexmc.mc.navigator.storage;

import java.io.File;

/**
 * Created by Lara on 26.02.2019 for navigator
 */
public final class Const {
  public static final int NAME_LENGTH = 32;

  public static final File SQL_CONFIG =
      new File("plugins" + File.separator + "config" + File.separator + "sql.properties");

  public static final String PLUGIN_NAME = "Navigator";
  public static final String WAYPOINTSTABLE = "roleplaywaypoints";
  public static final String NAVIGATOR_TITLE = "§lRoleplay-Navigator";
}