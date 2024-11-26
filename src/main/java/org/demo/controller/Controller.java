package org.demo.controller;

import com.jasongoodwin.monads.Try;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.model.AvtaleResponse;
import org.demo.model.AvtaleStatus;
import org.demo.service.Service;

// Integrasjonslag
@Path("/avtale")
public class Controller {
    Service service = new Service();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AvtaleResponse opprettAvtale() {
        return service.opprettAvtale().getUnchecked();
//        if(result.isSuccess()) {
//            return Response.ok(service.opprettAvtale().getUnchecked()).build();
//        } else {
//            return Response.s().build();
//        }
    }
}
