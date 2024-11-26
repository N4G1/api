package org.demo.service;

import com.jasongoodwin.monads.Try;
import org.demo.model.AvtaleResponse;
import org.demo.model.AvtaleStatus;

// Integrasjonslag
public class Service {

    public Fagsystem fagsystem = new Fagsystem();
    public Brevtjeneste brevtjeneste = new Brevtjeneste();

    public Try<AvtaleResponse> opprettAvtale() {

        // Vi kan enten løse det på funksjonell måte, ved å chaine hver av kallene
        return fagsystem.opprettKunde(1)
                .flatMap(kundenummer -> fagsystem.opprettAvtale(kundenummer)
                        .flatMap(avtaleummer -> brevtjeneste.sendAvtaleTilKunde(avtaleummer, kundenummer)
                                .flatMap(nyAvtaleStatus -> fagsystem.oppdaterAvtaleStatus(nyAvtaleStatus)
                                        .flatMap(x -> Try.successful(new AvtaleResponse(avtaleummer, AvtaleStatus.AVTALE_SENDT))
                                        )
                                )
                        )
                );

        // Eller ved å kalle de individuelt.

//        int kundenummer = fagsystem.opprettKunde(1).orElseThrow(RuntimeException::new);
//        int avtalenummer = fagsystem.opprettAvtale(kundenummer).orElseThrow(RuntimeException::new);
//        brevtjeneste.sendAvtaleTilKunde(avtalenummer, kundenummer).orElseThrow(RuntimeException::new);
//        AvtaleStatus nyAvtaleStatus = fagsystem.oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT).orElseThrow(RuntimeException::new);
//        return Try.successful(new AvtaleResponse(avtalenummer, nyAvtaleStatus));
    }

    /*
     Jeg bruker Try monaden som jeg er kjent med fra Scala og Rust programmeringsspråkene.
     Try monaden er veldig likt Optional monaden, bare at istedenfor (None, Some) så får man (Success, Failure),
     som hjelper i å propagere erroren og verdien videre i kjeden mens implementasjonen holdes konstant. Kan leses mer om den her:
     https://medium.com/@afcastano/monads-for-java-developers-part-2-the-result-and-log-monads-a9ecc0f231bb

     Det er 2 ulike framgangsmåter man kan ta ved implementasjonen av Integrasjonslaget:

     1)
     Dersom Fagsystemet er en blackbox og vi vet ikke implementasjonen/innholdet av koden og vi kjenner kun signaturen
     på metodene: Da er vi nødt til å lage våre egne exceptions i Integrasjonslaget dersom en feil oppstår.

     2)
     Dersom Fagsystemet er en whitebox, enten vår egen implementasjon eller implementasjon som vi har full tilgang til:
     Da slipper vi å definere våre egne exceptions. Ved å bruke Try monaden kan vi la errors/exceptions propagere gjennom
     Integrasjonslaget ut til koden vår. Dette forenkler Integrasjonslaget ved å slippe å reimplementere errorene.


     Jeg ser et stor integrasjonsproblem med dataflyten og måten sekvensdiagrammet for dette integrasjonslaget er satt opp.
     Dette kommer av erfaring med lignende systemer, apier generelt og erfaringen innen integrasjonen mellom mikrotjenester.

     Problem nr. 1:
     Dataen som sendes til "Opprett kunde"-kallet blir ikke returnert tilbake etter at Kunde-objektet i fagsystemet er opprettet,
     det vi får tilbake er kun selve kundenummeret. Dette skaper et problem med dataintegrasjon, for vi har ingen anelse
     hvordan dataen som ble sendt til Fagsystemet blir seende ut og hva slags ekstra felter/funksjonalitet legges til.
     Løsningen er å returnere hele Kunde-objektet fra "Opprett kunde" metoden, slik at vi kan jobbe på det ekte objektet
     og at dataintegriteten opprettholdes.

     Problem nr 2:
     Samme situasjon skjer med "Opprett avtale"-kallet. Vi får kun avtalenummer som returverdi og har ingen anelse åssen
     Avtale-objektet blir faktisk seende ut i fagsystemet. Dette blir enda mer problematisk ved neste kall til Brevtjenesten
     "Send avtale til kunde". Hvilken avtale er det snakk om som vi skal sende? Vi har kun tilgang til de feltene vi får
     fra Klienten, hvordan skal vi vite at disse utgjør en helhetlig Avtale og disse er alt vi trenger, hvordan vet vi at
     Fagsystemet ikke legger på ekstra felter i avtalen?
     Løsningen er som over, å få returnert hele Avtale-objektet fra "Opprett avtale" metoden slik at selve Avtale-objektet
     kan brukes videre ned i kjeden og dataintegriteten opprettholdes på tvers av apiene.

     Problem nr. 3:


     Curl as a solution, out of the box thinking.
     Api delete call to rollback/revert data, inside the box thinking

     * */

}
