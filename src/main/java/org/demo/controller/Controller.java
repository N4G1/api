package org.demo.controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.demo.model.Avtale;
import org.demo.service.Service;

// Integrasjonslag
@Path("/avtale")
public class Controller {
    Service service = new Service();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Avtale opprettAvtale() {
        return service.opprettAvtale();
    }
}
