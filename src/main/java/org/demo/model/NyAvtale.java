package org.demo.model;

public record NyAvtale(
        AvtaleType avtaleType,
        double egenandel,
        double pris,
        int betalingsFrekvens,
        String navn,
        String etternavn,
        String fodselsnummer
) {}
