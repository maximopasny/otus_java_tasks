package orm.h2source;

import java.sql.*;

public class Database {
    private static final String DB_CONNECTION_STRING = "jdbc:h2:./myorm;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "qwe";
    private static final String DB_PASSWORD = "qwe";
    private static final String DDL_TABLE_USER = "CREATE TABLE Users (\n" +
            "    id INT(10) AUTO_INCREMENT PRIMARY KEY NOT NULL, \n" +
            "    name VARCHAR(255) NOT NULL, \n" +
            "    age  INT(3) NOT NULL\n" +
            ");\n";

    private static Database instance;

    public static Database getInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new Database();
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_CONNECTION_STRING, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Database() {
        init();
        createTableIfNotExists();
    }

    private void init() {
        try {
            Driver driver = new org.h2.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        try (Connection connection = getConnection(); ResultSet rs = connection.getMetaData().getTables(null, null, "USER", null)) {
            if (!rs.next()) {
                Statement stmt = connection.createStatement();
                stmt.execute(DDL_TABLE_USER);
                System.out.println("User table has been created!");
            } else {
                System.out.println("User table already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
