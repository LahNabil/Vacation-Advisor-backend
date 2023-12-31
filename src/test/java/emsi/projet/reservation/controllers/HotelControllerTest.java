package emsi.projet.reservation.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import emsi.projet.reservation.entities.Hotel;
import emsi.projet.reservation.services.HotelService;

class HotelControllerTest {
	   
		@Mock
	    private HotelService hotelService;

	    @InjectMocks
	    private HotelController hotelController;
	    
	    private MockMvc mockMvc;
	    private Hotel hotel;
	    private List<Hotel> expectedHotel;
	    
	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.initMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();

	        hotel = Hotel.builder()
	                .id(1L)
	                .nom("MHM")
	                .adresse("Marrakech 40020 Morocco")
	                .description("Breathtaking views from the top")
	                .photo("photo.png")
	                .etoile(4)
	                .build();
	        expectedHotel = List.of(
	            hotel
	        );

	    }
	    @Test
	    void hotel() throws Exception{
	        when(hotelService.findAll()).thenReturn(expectedHotel);
	        mockMvc.perform(MockMvcRequestBuilders.get("/hotels"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedHotel.size()));
	        verify(hotelService, times(1)).findAll();
	    }

}
