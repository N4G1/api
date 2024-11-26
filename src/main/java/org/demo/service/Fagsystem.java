package org.demo.service;

import com.jasongoodwin.monads.Try;
import org.demo.model.AvtaleStatus;

// Siden vi kan mocke oppførselen til hele denne tjenesten, velger jeg å skrive funksjonaliteten gjennom metoder istedenfor mocka REST Requests/Responses.
// Hadde jeg gjort dette i virkeligheten ville jeg opprettet en klient-wrapper som letteregjør bruken av HttpRequest metoden.
// Funksjonaliteten hadde blitt cirka samme som koden nedenfor, med variabler for ulike URI, metoder og errorhåndtering.

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

    public Try<Integer> opprettKunde(int nr) {
        if(nr < 0) {
          return Try.failure(new Exception("g"));
        } else {
            return Try.successful(nr);
        }
    }

    public Try<Integer> opprettAvtale(int kundenummer) {
        if(kundenummer < 0) {
            return Try.failure(new Exception("g"));
        } else {
            return Try.successful(kundenummer);
        }
    }

    public Try<AvtaleStatus> oppdaterAvtaleStatus(AvtaleStatus avtaleStatus) {
        if(avtaleStatus == null) {
            return Try.failure(new Exception("g"));
        } else {
            return Try.successful(avtaleStatus);
        }
    }
}
