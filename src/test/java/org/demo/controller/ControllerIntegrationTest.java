package org.demo.controller;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.model.*;
import org.demo.service.Service;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerIntegrationTest extends JerseyTest {

    private final Service mockService = mock(Service.class);

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .register(Controller.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(mockService).to(Service.class);
                    }
                });
    }

    @Test
    void testOpprettAvtale() {
        String nyAvtale = "{\"avtaleType\":\"BIL\",\"egenandel\":550.5,\"pris\":120,\"betalingsFrekvens\":12,\"navn\":\"test\",\"etternavn\":\"test\",\"fodselsnummer\":\"12128811223\"}";

        AvtaleResponse expectedResponse = new AvtaleResponse(42, AvtaleStatus.AVTALE_SENDT);
        when(mockService.opprettAvtale(any(NyAvtale.class))).thenReturn(expectedResponse);

        Response response = target("/avtale")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nyAvtale, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        AvtaleResponse actualResponse = response.readEntity(AvtaleResponse.class);
        assertEquals(expectedResponse, actualResponse);
    }

    // Not working, can't finish it in time, but this is how the validation test would look like if it would work.
    @Test
    void testValidateOpprettAvtale() {
        String invalidAvtale = "{\"avtaleType\":\"null\",\"egenandel\":-10,\"pris\":-1,\"betalingsFrekvens\":30,\"navn\":\"\",\"etternavn\":\"\",\"fodselsnummer\":\"123\"}";

        Response response = target("/avtale")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(invalidAvtale, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus(), "Should return 400 Bad Request");
        String errorMessage = response.readEntity(String.class);
        assertTrue(errorMessage.contains("AvtaleType is required"), "Should mention missing AvtaleType");
        assertTrue(errorMessage.contains("Egenandel must be non-negative"), "Should mention invalid Egenandel");
        assertTrue(errorMessage.contains("Pris must be non-negative"), "Should mention invalid Pris");
        assertTrue(errorMessage.contains("Betalingsfrekvens must not exceed 12"), "Should mention invalid Betalingsfrekvens");
        assertTrue(errorMessage.contains("Navn must not be blank"), "Should mention blank Navn");
        assertTrue(errorMessage.contains("Etternavn must not be blank"), "Should mention blank Etternavnavn");
        assertTrue(errorMessage.contains("Fodselsnummer must be exactly 11 digits"), "Should mention invalid Fodselsnummer");
    }
}
