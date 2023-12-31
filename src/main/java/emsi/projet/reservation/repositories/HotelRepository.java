package emsi.projet.reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emsi.projet.reservation.entities.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	Hotel getHotelByNom(String nom);
}
