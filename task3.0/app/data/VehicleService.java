package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import config.EsConnection;
import config.MysqlConnection;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import play.libs.Json;
import play.mvc.Http;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

public class VehicleService {
    @Inject
    private EsConnection esConnection;
    @Inject
    private MysqlConnection mysqlConnection;

// ElastcSearch
    public void addData(Http.Request request) throws IOException {
        JsonNode body = request.body().asJson();

        String id = body.get("product_id").asText();
        String name = body.get("product_name").asText();
        String specId = body.get("spec_id").asText();
        String specDetails = body.get("spec_details").asText();

        Map<String, Object> productmap = new HashMap<>();
        productmap.put("product_id", id);
        productmap.put("product_name", name);
        productmap.put("spec_id", specId);

        Map<String, Object> specmap = new HashMap<>();
        specmap.put("spec_id", specId);
        specmap.put("spec_details", specDetails);

        IndexRequest indexRequest = new IndexRequest("product", "_doc", id).source(productmap);
        IndexRequest indexRequest1 = new IndexRequest("specification", "_doc", id).source(specmap);

        IndexResponse response = esConnection.getRestHighLevelClient().index(indexRequest, RequestOptions.DEFAULT);
        IndexResponse response1 = esConnection.getRestHighLevelClient().index(indexRequest1, RequestOptions.DEFAULT);
    }

    public String getByProductById(String id) throws IOException {
        GetRequest request1 = new GetRequest("product", "_doc", id);
        GetResponse response = esConnection.getRestHighLevelClient().get(request1, RequestOptions.DEFAULT);
        String result = response.getSourceAsString();
        return result;
    }

    public String getBySpecId(String specid) throws IOException {
        GetRequest request = new GetRequest("specification", "_doc", specid);
        GetResponse response = esConnection.getRestHighLevelClient().get(request, RequestOptions.DEFAULT);
        String result = response.getSourceAsString();
        return result;

    }


//  MYSQL

    public void addDataIntoSql(Http.Request request) throws SQLException {
        JsonNode jsonNode = request.body().asJson();
        int id = jsonNode.get("product_id").asInt();
        String name = jsonNode.get("product_name").asText();
        int specid = jsonNode.get("spec_id").asInt();
        String specdetails = jsonNode.get("spec_details").asText();
        System.out.println(jsonNode);

        PreparedStatement preparedStatement = mysqlConnection.getConnection().prepareStatement("insert into product (product_id,product_name,spec_id) values (?,?,?);");
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.setInt(3,specid);
        preparedStatement.executeUpdate();


        PreparedStatement preparedStatement2 = mysqlConnection.getConnection().prepareStatement("insert into specification (spec_id,spec_details) values (?,?);");
        preparedStatement2.setInt(1,specid);
        preparedStatement2.setString(2,specdetails);
        preparedStatement2.executeUpdate();

        System.out.println(preparedStatement);

    }
public String getById(int id) throws SQLException {
    PreparedStatement preparedStatement = mysqlConnection.getConnection().prepareStatement("select * from product where product_id =?");
    preparedStatement.setInt(1, id);
    ResultSet resultSet = preparedStatement.executeQuery();
    ObjectNode result = Json.newObject();
    while ((resultSet.next())) {
        int empid = resultSet.getInt("product_id");
        String empName = resultSet.getString("product_name");

        result.put("product_id", empid);
        result.put("product_name", empName);

    }
    return result.toString();
}
}
