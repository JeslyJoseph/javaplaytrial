package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import data.EsClient;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;


import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.ok;

public class EmployeeController extends Controller {
    @Inject
    private EsClient esClient;

    public EmployeeController() {
    }

    public Result addData() throws IOException {
        System.out.println("connected to Elastic Search");
        String json = "{\"id\":\"8+\",\"name\":\"Pathik\"}";
        Request request = new Request("POST", "/employee/_doc/1");
        request.setJsonEntity(json);
        Response response = esClient.getRestClient().performRequest(request);

//        System.out.println(EntityUtils.toString(response.getEntity()));

        return ok(EntityUtils.toString(response.getEntity()));
    }
    public Result getData() throws IOException {
        Request request = new Request("GET", "/employee/_doc/1");
        Response response = esClient.getRestClient().performRequest(request);
        return ok(EntityUtils.toString(response.getEntity()));
    }
    public Result addDocument(Http.Request request,String employee,String id) {
        JsonNode requestBody = request.body().asJson();
        Request request1 = new Request("POST", "employee/_doc/3") ;
        try {
            esClient.getRestClient().performRequest(request1);
            return ok(String.format("Document with id '%s' added to index '%s'.", id, employee));
        } catch (IOException e) {
            return internalServerError(e.getMessage());
        }
    }

}
