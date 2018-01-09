package movieRecs;

import java.sql.*;
import javax.xml.crypto.Data;

public class Database {
    private static Database db = new Database();
    private Connection c = null;

    private Database() {
        // connect

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }

    public Database getInstance() { return this.db; }
}
