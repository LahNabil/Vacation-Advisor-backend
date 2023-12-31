package emsi.projet.reservation.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import emsi.projet.reservation.entities.Hotel;
import emsi.projet.reservation.services.HotelService;

class HotelRepositoryTest {

	@MockBean
	 private HotelRepository hotelRepository;

	 @Autowired
	 private HotelService hotelService;
	 
	
	 
	 
	 @BeforeEach
	 void voidSetup(){
			Hotel hotel1 = new Hotel(10L,"MHM", "Marrakech 40020 Morocco", "Breathtaking views from the top", "photo.png",5);
			Mockito.when(hotelRepository.getHotelByNom("MHM")).thenReturn(hotel1);			
		}
	    
	 
	 	@Test
	    void testGetHotelByNom(String nom) {
	        Long hotel_id = 10L;
	        Hotel HotelByNom = hotelRepository.getHotelByNom("MHM");
	        
	        assertEquals(hotel_id, HotelByNom.getId());
	    }
	 	
	 	
	

}
