package dev.lebassi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection() {
        //     String details = "jdbc:postgresql://34.71.52.13:5432/bankDB?user=amanuel&password=john316!";

        //     DataBaseLINK=jdbc:postgresql://34.71.52.13:5432/bankDB?user=amanuel&password=john316!
        String details = System.getenv("DataBaseLINK");

        try {
            Connection conn = DriverManager.getConnection(details);
            return conn;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }
}
