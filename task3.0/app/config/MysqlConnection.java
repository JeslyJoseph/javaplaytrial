package config;
import com.google.inject.AbstractModule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlConnection{
    private Connection connection;

    public MysqlConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vechicle_management", "root", "password");

    }

    public Connection getConnection() {
        return connection;
    }
}
