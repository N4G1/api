package org.demo.controller;

import org.demo.model.*;
import org.demo.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ControllerTest {

    private Controller controller;

    @Mock
    private Service mockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new Controller();
        controller.service = mockService;
    }

    @Test
    void testOpprettAvtale() {
        NyAvtale nyAvtale = new NyAvtale(AvtaleType.BIL, 550.5, 120, 30, "test", "test", "12128811223");
        AvtaleResponse expectedResponse = new AvtaleResponse(1, AvtaleStatus.AVTALE_SENDT);
        when(mockService.opprettAvtale(nyAvtale)).thenReturn(expectedResponse);

        AvtaleResponse actualResponse = controller.opprettAvtale(nyAvtale);

        assertEquals(expectedResponse, actualResponse, "Response should match expected");
        verify(mockService).opprettAvtale(nyAvtale);
    }
}

