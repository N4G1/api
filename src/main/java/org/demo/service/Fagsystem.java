package org.demo.service;

import org.demo.model.Avtale;
import org.demo.model.AvtaleStatus;
import org.demo.model.Kunde;

// Siden vi kan mocke oppførselen til hele denne tjenesten, velger jeg å skrive funksjonaliteten gjennom metoder istedenfor mocka REST Requests/Responses.
// Hadde jeg gjort dette i virkeligheten ville jeg opprettet en klient-wrapper som letteregjør bruken av HttpRequest metoden.
// Funksjonaliteten hadde blitt cirka samme som koden nedenfor, med variabler for ulike URI og exceptions.

/*
        try (HttpClient client = HttpClient.newHttpClient()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBody = objectMapper.writeValueAsString(object);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:1337/fagsystem/v1/avtale"))
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .header("Content-Type", "application/json")
                        .build();

                HttpResponse response = client.send(request);
        } catch (Exception e) {
            throw new CustomException();
        }
*/
public class Fagsystem {

    public int opprettKunde(Kunde kunde) {
        return 1337;
    }

    public int opprettAvtale(int kundenummer, Avtale avtale) {
        return 42;
    }

    public AvtaleStatus oppdaterAvtaleStatus(AvtaleStatus avtaleStatus) {
        return avtaleStatus;
    }
}
