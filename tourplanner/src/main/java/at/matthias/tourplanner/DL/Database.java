package at.matthias.tourplanner.DL;

import java.sql.*;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class Database {
    private static final String SQLCREATEPATH = "/db/create_tables.sql";
    private static final String DBACCESSPATH = "/config/dbAccess.xml";

    private static Connection conn = null;
    private static Logger logger = Logger.getLogger(Database.class);

    protected Database() {
    }

    private static void initConnection() {
        XMLReader xmlReader = new XMLReader();
        HashMap<String, String> config = xmlReader.readDbConfig(Connection.class.getResource(DBACCESSPATH).toString());

        String dburl = config.get("dburl");
        String dbname = config.get("dbname");
        String user = config.get("user");
        String pw = config.get("password");

        try {
            logger.info("Connecting to database...");

            conn = DriverManager.getConnection(dburl + dbname, user, pw);
        } catch (Exception e) {
            logger.error("Error connecting to Database" + e);
        }
        logger.info("Connecting to Database successful");
    }

    private static void initCreateStatements() {
        try (Statement stmt = conn.createStatement()) {
            logger.info("Creating tables...");
            stmt.executeUpdate(FileHandler.read(Connection.class.getResource(SQLCREATEPATH).toString()));
        } catch (Exception e) {
            logger.error("Error creating tables" + e);
        }
        logger.info("Tables created successfully...");
    }
    public static Connection getConn() {
        logger.info("getting Database Connection");
        if (conn == null) {
            initCreateStatements();
            initConnection();
        }
        return conn;
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
