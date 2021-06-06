package at.matthias.tourplanner.DL;

import java.sql.*;
import java.util.Map;
import lombok.Getter;
import org.apache.log4j.Logger;

public class Database {
  @Getter static Connection conn = null;
  private static final Logger logger = Logger.getLogger(Database.class);

  protected Database() {
    while (conn == null) {
      logger.info("Connections is still null");
      initConnection();
    }
    initCreateStatements();
  }

  private static void initConnection() {
    XMLReader reader = new XMLReader();
    Map<String, String> config = reader.readDbConfig(reader.getFullPath("dbaccess"));
    if (config != null) {
      String dburl = config.get("dburl");
      String dbname = config.get("dbname");
      String user = config.get("user");
      String pw = config.get("password");

      logger.info("Connecting to database...");
      try {
        conn = DriverManager.getConnection(dburl + dbname, user, pw);
        logger.info("Connecting to Database successful");
      } catch (Exception e) {
        logger.error("Error connecting to Database" + e);
      }
    } else {
      logger.error("Access String is null");
    }
  }

  private static void initCreateStatements() {
    if (conn != null) {
      try (Statement stmt = conn.createStatement()) {
        logger.info("Creating tables...");
        XMLReader reader = new XMLReader();
        String path = reader.getFullPath("sqlcreate");
        String sqlCreate = FileHandler.read(path);
        if (sqlCreate != null) {
          stmt.executeUpdate(sqlCreate);
          logger.info("Tables created successfully...");
        } else {
          logger.error("SQL Create String is null");
        }
      } catch (Exception e) {
        logger.error("Error creating tables! " + e);
      }
    } else {
      logger.error("Connection is null! Failed to create tables");
    }
  }
  public static void close() {
    logger.info("closing Database Connection");
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException se) {
      logger.error("Error closing Database Connecition" + se);
    }
  }
}
