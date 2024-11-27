package org.demo.service;

import org.demo.model.Avtale;
import org.demo.model.AvtaleStatus;
import org.demo.model.Kunde;

// Brevtjeneste
public class Brevtjeneste {
    public AvtaleStatus sendAvtaleTilKunde(int avtalenummer, int kundenummer, Avtale avtale, Kunde kunde) {
        return AvtaleStatus.AVTALE_SENDT;
    }
}
