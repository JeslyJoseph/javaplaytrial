package controllers;

import com.google.inject.Inject;
import data.VehicleService;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;

import static play.mvc.Results.ok;

public class VehicleController {
    @Inject
    private VehicleService vehicleService;

    public Result addData(Http.Request request) throws IOException {
        vehicleService.addData(request);
        return ok("Data addedd successfully");
    }

    public Result getProductById(String id) throws IOException {
        String result = vehicleService.getByProductById(id);
        return ok(result);
    }

    public Result getBySpecId(String specid) throws IOException {
        String result = vehicleService.getBySpecId(specid);
        return ok(result);
    }
}
