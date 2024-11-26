package org.demo.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.demo.model.AvtaleResponse;
import org.demo.model.NyAvtale;
import org.demo.service.Service;

// Integrasjonslag
@Path("/avtale")
public class Controller {
    Service service = new Service();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AvtaleResponse opprettAvtale(NyAvtale nyAvtale) {
        System.out.println(nyAvtale);
        return service.opprettAvtale(nyAvtale).getUnchecked();
//        if(result.isSuccess()) {
//            return Response.ok(service.opprettAvtale().getUnchecked()).build();
//        } else {
//            return Response.s().build();
//        }
    }
}
