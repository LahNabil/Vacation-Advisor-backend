package emsi.projet.reservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emsi.projet.reservation.auth.AuthenticationRequest;
import emsi.projet.reservation.auth.AuthenticationResponse;
import emsi.projet.reservation.auth.RegisterRequest;
import emsi.projet.reservation.repositories.UserRepository;
import emsi.projet.reservation.services.AuthenticationService;
import emsi.projet.reservation.services.JwtService;





@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:5173/")
public class Controller {
	
	@Autowired
	private AuthenticationService service;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(
	      @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request));
	  }
	@PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(
	      @RequestBody AuthenticationRequest request) {
		System.out.println("Req email: " + request.getEmail());
		System.out.println("Req pass: " + request.getPassword());
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		System.out.println("Generated JWT Token: " + jwtToken);
	    return ResponseEntity.ok(service.authenticate(request));
	    
	  }
}