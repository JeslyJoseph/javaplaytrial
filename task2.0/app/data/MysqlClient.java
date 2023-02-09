package data;

import com.google.inject.Inject;
import play.api.db.Database;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MysqlClient {
    private Connection connection;
    private Map<Integer, String> map;
//    @Inject
//    Database db;

    public MysqlClient() throws SQLException {
//        Connection connection1 = db.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/country_details", "root", "password");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from country");
        Map<Integer, String> country = new HashMap<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            country.put(id, name);
        }
        System.out.println(country);
        this.map=country;
    }

    public Connection getConnection() {
        return connection;
    }

    public Map<Integer, String> getMap() {
        return map;
    }
}
