package org.demo.service;

import com.jasongoodwin.monads.Try;
import org.demo.model.AvtaleResponse;
import org.demo.model.AvtaleStatus;
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

    @Test
    void testOpprettAvtale_Success() {
        when(mockFagsystem.opprettKunde(1)).thenReturn(Try.successful(1001));
        when(mockFagsystem.opprettAvtale(1001)).thenReturn(Try.successful(2002));
        when(mockBrevtjeneste.sendAvtaleTilKunde(2002, 1001)).thenReturn(Try.successful(AvtaleStatus.AVTALE_SENDT));
        when(mockFagsystem.oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT))
                .thenReturn(Try.successful(AvtaleStatus.AVTALE_SENDT));

        AvtaleResponse response = service.opprettAvtale().getUnchecked();

        assertNotNull(response, "Response should not be null");
        assertEquals(2002, response.avtalenummer(), "Avtalenummer should match");
        assertEquals(AvtaleStatus.AVTALE_SENDT, response.avtaleStatus(), "AvtaleStatus should match");

        verify(mockFagsystem).opprettKunde(1);
        verify(mockFagsystem).opprettAvtale(1001);
        verify(mockBrevtjeneste).sendAvtaleTilKunde(2002, 1001);
        verify(mockFagsystem).oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT);
    }
}

