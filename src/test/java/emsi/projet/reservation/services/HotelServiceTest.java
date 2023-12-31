package emsi.projet.reservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import emsi.projet.reservation.entities.Hotel;
import emsi.projet.reservation.repositories.HotelRepository;


@SpringBootTest
class HotelServiceTest {
	
	
	@MockBean
	private HotelRepository hotelRepository;
	
	@Autowired
	private HotelService hotelService;

	@BeforeEach
	void voidSetup(){
		Optional<Hotel> hotel1 = Optional.of(new Hotel(10L,"MHM", "Marrakech 40020 Morocco", "Breathtaking views from the top", "photo.png",5));
		Mockito.when(hotelRepository.findById(10L)).thenReturn(hotel1);			
	}
	
	@Test
	public void testGetHotelById_success() {
		String hotel_name = "MHM";
		Optional<Hotel> HotelById  = hotelService.findById(10L);
		assertEquals(hotel_name, HotelById.get().getNom());
	}
	
	@Test
    public void testSaveHotel_Success() {
        Hotel hotelToSave = new Hotel(15L, "New Hotel", "Location", "Description", "photo.png", 4);

        hotelService.save(hotelToSave);

        // assertEquals(hotelToSave, savedHotel);
        // Verify that the save method of the repository is called once
        verify(hotelRepository, times(1)).save(hotelToSave);
    }
	@Test
    public void testDeleteHotelById_Success() {
        hotelService.deleteById(10L);

        // Verify that the deleteById method of the repository is called once
        verify(hotelRepository, times(1)).deleteById(10L);
    }
	/* 
	@Test
	    public void testUpdateHotel_Success() {
	        Hotel updatedHotel = new Hotel(10L, "Updated Hotel", "Updated Location", "Updated Description", "updated.png", 3);

	        //Hotel result = hotelService.updateHotel(updatedHotel);

	        assertEquals(updatedHotel, result);
	        // Verify that the save method of the repository is called once
	        verify(hotelRepository, times(1)).save(updatedHotel);
	    }
	   
	*/
	
}
