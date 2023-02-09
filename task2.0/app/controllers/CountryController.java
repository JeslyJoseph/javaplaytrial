package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import data.MysqlClient;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CountryController extends Controller {

    @Inject
    private MysqlClient mysqlClient;

    public CountryController() {
    }
    public Result addCountry() throws SQLException {
        PreparedStatement preparedStatement = mysqlClient.getConnection().prepareStatement("insert into country (id,name) values (?,?);");
        preparedStatement.setInt(1, 8);
        preparedStatement.setString(2, "China");
        preparedStatement.executeUpdate();
        return ok("Data Inserted");
//        return ok("success");
//        return ok(mysqlClient.getConnection().getSchema());
    }
    public Result getById(int id) throws SQLException {
        PreparedStatement preparedStatement = mysqlClient.getConnection().prepareStatement("select * from country where id =?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObjectNode result = Json.newObject();
        while ((resultSet.next())) {
            int empid = resultSet.getInt("id");
            String empName = resultSet.getString("name");

            result.put("id", empid);
            result.put("name", empName);

        }
        return ok(result);
    }
    public Result getCountry() throws SQLException {
//        System.out.println(mysqlClient.getMap());
//        Connection connection = mysqlClient.getConnection();

        return ok(mysqlClient.getMap().toString());
    }
}
