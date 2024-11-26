package org.demo.service;

import com.jasongoodwin.monads.Try;
import org.demo.model.AvtaleStatus;

// Brevtjeneste
public class Brevtjeneste {
    public Try<AvtaleStatus> sendAvtaleTilKunde(int avtalenummer, int kundenummer) {
        if(avtalenummer < 0 || kundenummer < 0) {
            return Try.failure(new IllegalArgumentException("avtalenummer or kundenummer is invalid"));
        } else {
            return Try.successful(AvtaleStatus.AVTALE_SENDT);
        }
    }
}
