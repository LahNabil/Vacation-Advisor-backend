package emsi.projet.reservation.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emsi.projet.reservation.entities.Hotel;
import emsi.projet.reservation.repositories.HotelRepository;
import emsi.projet.reservation.services.HotelService;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "http://localhost:3000/")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.findAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Optional<Hotel> optionalHotel = hotelService.findById(id);

        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            return ResponseEntity.ok(hotel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public Hotel createHotel(@RequestBody Hotel hotel) {
    	return hotelService.save(hotel);
    	
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        Optional<Hotel> optionalHotel = hotelService.findById(id);

        if (optionalHotel.isPresent()) {
        	hotelService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
