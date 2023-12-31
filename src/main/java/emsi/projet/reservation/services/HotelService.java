package emsi.projet.reservation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import emsi.projet.reservation.entities.Hotel;
import emsi.projet.reservation.repositories.HotelRepository;




@Service
public class HotelService {
	
	@Autowired
    private HotelRepository hotelRepository;
    

    public Hotel save(Hotel hotel) {
       
        return hotelRepository.save(hotel);
    }
    

    public Optional<Hotel> findById(Long id) {
        return hotelRepository.findById(id);
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }
    

    
    public Hotel Hotel(Hotel hotel) {
		  Hotel exHotel = hotelRepository.findById(hotel.getId()).orElse(null);
	        if(exHotel == null) {
	            System.out.println("Emp not found");
	            
	        }else  {
	        	exHotel.setNom(hotel.getNom());
	        	exHotel.setAdresse(hotel.getAdresse());
	        	exHotel.setEtoile(hotel.getEtoile());
	        	exHotel.setPhoto(hotel.getPhoto());
	        	exHotel.setDescription(hotel.getDescription());
	        	hotelRepository.save(exHotel);
	        }
	        return hotel;
	 }

}
