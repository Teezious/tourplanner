package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.models.TourItem;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database implements DataAccess {
    private static final String SQLCREATEPATH = "/db/create_tables.sql";
    private static final String DBACCESSPATH = "/config/dbAccess.xml";
    private String dburl;
    private String dbname;
    private String user;
    private String pw;
    private Connection conn;
    public Database() {
        initConnection();
        initCreateStatements();
    }
    // TODO evaluate usefulness of this function
    private boolean connectionSuccessful() {
        try {
            final Connection pgconn = DriverManager.getConnection(dburl + dbname, user, pw);
            return pgconn != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void initConnection() {
        XMLReader xmlReader = new XMLReader();
        HashMap<String, String> config = xmlReader.readDbConfig(getClass().getResource(DBACCESSPATH).toString());

        dburl = config.get("dburl");
        dbname = config.get("dbname");
        user = config.get("user");
        pw = config.get("password");

        System.out.println(dburl + dbname + user + pw);

        if (connectionSuccessful()) {
            try {
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(dburl + dbname, user, pw);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Connecting to Database successful");
        } else {
            System.out.println("Connecting to database failed ");
            System.exit(0);
        }
    }
    private void initCreateStatements() {
        try {
            System.out.println("Creating tables...");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(readSQL(getClass().getResource(SQLCREATEPATH).toString()));
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tables created successfully...");
    }
    public Connection getConn() {
        return conn;
    }
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    @Override
    public List<TourItem> GetItems() {
        return new ArrayList<>();
    }

    private String readSQL(String filename) throws IOException {
        FileReader file = new FileReader(filename);
        StringBuilder statements = new StringBuilder();
        int i;
        while ((i = file.read()) != -1)
            statements.append((char)i);

        return statements.toString();
    }
}
