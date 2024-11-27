package org.demo.model;

import jakarta.validation.constraints.*;

public record NyAvtale(
        @NotNull(message = "AvtaleType is required")
        AvtaleType avtaleType,
        @Min(value = 0, message = "Egenandel must be non-negative")
        double egenandel,
        @Min(value = 0, message = "Pris must be non-negative")
        double pris,
        @Min(value = 1, message = "Betalingsfrekvens must be at least 1")
        @Max(value = 12, message = "Betalingsfrekvens must not exceed 12")
        int betalingsFrekvens,
        @NotBlank(message = "Navn must not be blank")
        String navn,
        @NotBlank(message = "Etternavn must not be blank")
        String etternavn,
        @Pattern(regexp = "\\d{11}", message = "Fodselsnummer must be exactly 11 digits")
        String fodselsnummer
) {
    public Kunde toKunde() {
        return new Kunde(navn, etternavn, fodselsnummer);
    }

    public Avtale toAvtale() {
        return new Avtale(avtaleType, egenandel, pris, betalingsFrekvens);
    }
}
