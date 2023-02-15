package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import config.EsConnection;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import play.mvc.Http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

public class VehicleService {
    @Inject
    private EsConnection esConnection;

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
}
