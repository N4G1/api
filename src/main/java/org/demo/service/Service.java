package org.demo.service;

import org.demo.model.Avtale;
import org.demo.model.AvtaleStatus;

// Integrasjonslag
public class Service {

    public Fagsystem fagsystem = new Fagsystem();
    public Brevtjeneste brevtjeneste = new Brevtjeneste();

    public Avtale opprettAvtale() {
        int kundenummer = fagsystem.opprettKunde();
        int avtalenummer = fagsystem.opprettAvtale();

        AvtaleStatus nyAvtaleStatus = brevtjeneste.sendAvtaleTilKunde();
        AvtaleStatus oppdatertAvtaleStatus = fagsystem.oppdaterAvtaleStatus(nyAvtaleStatus);
        return new Avtale();
    }

}
