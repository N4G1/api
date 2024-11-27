package org.demo.service;

import org.demo.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceTest {

    private Service service;

    @Mock
    private Fagsystem mockFagsystem;

    @Mock
    private Brevtjeneste mockBrevtjeneste;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new Service();
        service.fagsystem = mockFagsystem;
        service.brevtjeneste = mockBrevtjeneste;
    }

    NyAvtale nyAvtale = new NyAvtale(AvtaleType.BIL, 550.5, 120, 30, "test", "test", "12128811223");
    Kunde kunde = nyAvtale.toKunde();
    Avtale avtale = nyAvtale.toAvtale();

    @Test
    void testOpprettAvtale_Success() {
        when(mockFagsystem.opprettKunde(kunde)).thenReturn(1001);
        when(mockFagsystem.opprettAvtale(1001, avtale)).thenReturn(2002);
        when(mockBrevtjeneste.sendAvtaleTilKunde(2002, 1001, avtale, kunde)).thenReturn(AvtaleStatus.AVTALE_SENDT);
        when(mockFagsystem.oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT))
                .thenReturn(AvtaleStatus.AVTALE_SENDT);

        AvtaleResponse response = service.opprettAvtale(nyAvtale);

        assertNotNull(response, "Response should not be null");
        assertEquals(2002, response.avtalenummer(), "Avtalenummer should match");
        assertEquals(AvtaleStatus.AVTALE_SENDT, response.avtaleStatus(), "AvtaleStatus should match");

        verify(mockFagsystem).opprettKunde(kunde);
        verify(mockFagsystem).opprettAvtale(1001, avtale);
        verify(mockBrevtjeneste).sendAvtaleTilKunde(2002, 1001, avtale, kunde);
        verify(mockFagsystem).oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT);
    }

    @Test
    void testOpprettAvtale_FailureOnOpprettKunde() {
        when(mockFagsystem.opprettKunde(kunde)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> service.opprettAvtale(nyAvtale));

        verify(mockFagsystem).opprettKunde(kunde);
        verifyNoMoreInteractions(mockFagsystem, mockBrevtjeneste);
    }

    @Test
    void testOpprettAvtale_FailureOnOpprettAvtale() {
        when(mockFagsystem.opprettKunde(kunde)).thenReturn(1001);
        when(mockFagsystem.opprettAvtale(1001, avtale)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> service.opprettAvtale(nyAvtale));

        verify(mockFagsystem).opprettKunde(kunde);
        verify(mockFagsystem).opprettAvtale(1001, avtale);
        verifyNoMoreInteractions(mockFagsystem, mockBrevtjeneste);
    }

    @Test
    void testOpprettAvtale_FailureOnSendAvtaleTilKunde() {
        when(mockFagsystem.opprettKunde(kunde)).thenReturn(1001);
        when(mockFagsystem.opprettAvtale(1001, avtale)).thenReturn(2002);
        when(mockBrevtjeneste.sendAvtaleTilKunde(2002, 1001, avtale, kunde)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> service.opprettAvtale(nyAvtale));

        verify(mockFagsystem).opprettKunde(kunde);
        verify(mockFagsystem).opprettAvtale(1001, avtale);
        verify(mockBrevtjeneste).sendAvtaleTilKunde(2002, 1001, avtale, kunde);
        verifyNoMoreInteractions(mockFagsystem);
    }

    @Test
    void testOpprettAvtale_FailureOnUpdateStatus() {
        when(mockFagsystem.opprettKunde(kunde)).thenReturn(1001);
        when(mockFagsystem.opprettAvtale(1001, avtale)).thenReturn(2002);
        when(mockBrevtjeneste.sendAvtaleTilKunde(2002, 1001, avtale, kunde)).thenReturn(AvtaleStatus.AVTALE_SENDT);
        when(mockFagsystem.oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> service.opprettAvtale(nyAvtale));

        verify(mockFagsystem).opprettKunde(kunde);
        verify(mockFagsystem).opprettAvtale(1001, avtale);
        verify(mockBrevtjeneste).sendAvtaleTilKunde(2002, 1001, avtale, kunde);
        verify(mockFagsystem).oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT);
    }
}

