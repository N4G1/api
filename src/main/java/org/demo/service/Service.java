package org.demo.service;

import org.demo.model.*;

// Integrasjonslag
public class Service {

    public Fagsystem fagsystem = new Fagsystem();
    public Brevtjeneste brevtjeneste = new Brevtjeneste();

    public AvtaleResponse opprettAvtale(NyAvtale nyAvtale) {
        Kunde kunde = nyAvtale.toKunde();
        Avtale avtale = nyAvtale.toAvtale();

        int kundenummer = fagsystem.opprettKunde(kunde);
        int avtalenummer = fagsystem.opprettAvtale(kundenummer, avtale);
        brevtjeneste.sendAvtaleTilKunde(avtalenummer, kundenummer, avtale, kunde);
        AvtaleStatus nyAvtaleStatus = fagsystem.oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT);
        return new AvtaleResponse(avtalenummer, nyAvtaleStatus);
    }

    /*
     Det er 2 ulike framgangsmåter man kan ta ved implementasjonen av Integrasjonslaget:

     1)
     Dersom Fagsystemet er en blackbox og vi vet ikke implementasjonen/innholdet av koden og vi kjenner kun signaturen
     på metodene: Da er vi nødt til å lage våre egne exceptions i Integrasjonslaget dersom en feil oppstår.

     2)
     Dersom Fagsystemet er en whitebox, enten vår egen implementasjon eller implementasjon som vi har full tilgang til:
     Da slipper vi å definere våre egne exceptions. Vi kan la errors/exceptions propagere gjennom
     Integrasjonslaget ut til controller. Dette forenkler Integrasjonslaget ved å slippe å reimplementere errorene.
     Eneste vi må gjøre er å holde ExceptionHandling klassen oppdatert.


     Kommentarer til oppgaven:

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


     cron job as a solution, out of the box thinking.
     Api delete call to rollback/revert data, inside the box thinking

     * */

}
