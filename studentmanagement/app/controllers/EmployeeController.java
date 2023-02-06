package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import play.api.db.Database;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.EmployeeService;

import javax.inject.Inject;
import java.sql.*;



@Configuration
public class EmployeeController extends Controller {

    private EmployeeService employeeService;
    private FormFactory formFactory;
    private RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            )
    );

    @Inject
    public EmployeeController(EmployeeService employeeService, FormFactory formFactory) {
        this.employeeService = employeeService;
        this.formFactory = formFactory;
    }

    @Inject
    Database db;

    public Result getEmployee() throws SQLException {
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee_details");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            String phno = resultSet.getString("phno");
            result.add(JsonNodeFactory.instance.objectNode().put("id", id).put("name", name).put("email",email).put("gender",gender).put("phno",phno));
        }
        return ok(result);
    }
    public Result getById(int id) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from employee_details where id =?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet  = preparedStatement.executeQuery();
        ObjectNode result = Json.newObject();
        while ((resultSet.next())){
            int empid = resultSet.getInt("id");
            String empName = resultSet.getString("name");
            String empmail = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            result.put("id",id);
            result.put("name",empName);
            result.put("email",empmail);
            result.put("gender",gender);
        }
        return ok(result);
    }
    public Result addEmployee(String jsonData) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement statement1 = connection.prepareStatement("insert into employee_details (name,dob,address,phno,gender,email,ibu,designation,experience) values(?,?,?,?,?,?,?,?,?)");
        statement1.setString(1,jsonData);
        statement1.executeUpdate();
        return ok(jsonData);
    }
}


//    public Result addEmployee(Http.Request employee) {
//        Form<Employee> form = formFactory.form(Employee.class).bindFromRequest(employee);
//        return ok(employeeService.addEmployee(form.get()).toString());
//    }
//    public Result getEmployee(int id){
//        return ok(employeeService.getEmployee(id).toString());
//    }

//    public Result connection() {
//        try (Connection connection = db.getConnection()) {
//            try (Statement statement = connection.createStatement()) {
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM employee_details");
//                while (resultSet.next()){
//                    System.out.println(resultSet.getString("name"));
//                }
//            }
//        } catch (SQLException e) {
//            return internalServerError("Failed to connect to the database");
//        }
//
//        return ok("Successfully connected to the database");
//    }




