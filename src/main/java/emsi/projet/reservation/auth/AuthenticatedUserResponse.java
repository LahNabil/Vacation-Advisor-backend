package emsi.projet.reservation.auth;

import emsi.projet.reservation.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserResponse {
	
	private AuthenticationResponse authenticationResponse;
	private User user;
	
	public AuthenticationResponse getAuthenticationResponse() {
	        return authenticationResponse;
	    }
	public void setAuthenticationResponse(AuthenticationResponse authenticationResponse) {
	        this.authenticationResponse = authenticationResponse;
	    }

	public User getUser() {
	        return user;
	    }

	public void setUser(User user) {
	        this.user = user;
	    }
	

}
