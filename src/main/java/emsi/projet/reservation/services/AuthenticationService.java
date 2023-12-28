package emsi.projet.reservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import emsi.projet.reservation.auth.AuthenticationRequest;
import emsi.projet.reservation.auth.AuthenticationResponse;
import emsi.projet.reservation.auth.RegisterRequest;
import emsi.projet.reservation.entities.Role;
import emsi.projet.reservation.entities.Token;
import emsi.projet.reservation.entities.TokenType;
import emsi.projet.reservation.entities.User;
import emsi.projet.reservation.repositories.TokenRepository;
import emsi.projet.reservation.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final PasswordEncoder passwordEncoder;
	@Autowired
    private final UserRepository repository;
	@Autowired
    private final JwtService jwtService;
	@Autowired
    private final AuthenticationManager authenticationManager;
	@Autowired
    private final TokenRepository tokenRepository;
    

    

    public AuthenticationResponse register(RegisterRequest request) {
    	
    	var user = User.builder()
    			.firstname(request.getFirstname())
    			.lastname(request.getLastname())
    			.email(request.getEmail())
    			.password(passwordEncoder.encode(request.getPassword()))
    			.role(Role.ADMIN)
    			.build();
    	
    	var saveUser = repository.save(user);
    	var jwtToken = jwtService.generateToken(user);
    	saveUserToken(saveUser, jwtToken);
    	return AuthenticationResponse.builder()
    			.token(jwtToken)
    			.build();
    	
    			
        
}
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
      }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())

        );
        
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        System.out.println("Generated JWT Token: " + jwtToken);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return
                AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

      }
    
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
      }
    			
    			
    	
    }