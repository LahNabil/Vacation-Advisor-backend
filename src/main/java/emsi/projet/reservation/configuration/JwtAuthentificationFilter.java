package emsi.projet.reservation.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import emsi.projet.reservation.repositories.TokenRepository;
import emsi.projet.reservation.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
	
	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final UserDetailsService userDetailsService;
	@Autowired
	private final TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
		      var isTokenValid = tokenRepository.findByToken(jwt)
		              .map(t -> !t.isExpired() && !t.isRevoked())
		              .orElse(false);
		      if(jwtService.isTokenValid(jwt, userDetails)&& isTokenValid) {
		    	  UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
		    			   userDetails,
		    			   null,
		    			   userDetails.getAuthorities()
		    			   );
		    	  authToken.setDetails(
		    			  new WebAuthenticationDetailsSource().buildDetails(request)
		    			  );
		    	  SecurityContextHolder.getContext().setAuthentication(authToken);
		      }
		
	}
		filterChain.doFilter(request, response);
	
	}
}
