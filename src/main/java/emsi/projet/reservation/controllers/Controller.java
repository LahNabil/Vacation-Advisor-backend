package emsi.projet.reservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emsi.projet.reservation.auth.AuthenticationRequest;
import emsi.projet.reservation.auth.AuthenticationResponse;
import emsi.projet.reservation.auth.RegisterRequest;
import emsi.projet.reservation.repositories.UserRepository;
import emsi.projet.reservation.services.AuthenticationService;
import emsi.projet.reservation.services.JwtService;





@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000/")
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
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		System.out.println("Generated JWT Token: " + jwtToken);
	    return ResponseEntity.ok(service.authenticate(request));
	    
	  }
	
	@GetMapping("/profile")
    public ResponseEntity<String> getUser(@RequestParam String token){
		try {
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(username);
		} catch (Exception e) {
            // You can customize the error message and HTTP status code based on your requirements.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error extracting username from token");
        }
        
    }
	
	 	//@GetMapping("/profile")
	    //public AuthenticationResponse infos(Authentication authentication){
	      //  return authentication;
	    //}
	
	
	
	
}