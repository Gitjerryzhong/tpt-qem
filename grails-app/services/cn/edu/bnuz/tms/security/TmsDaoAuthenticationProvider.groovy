package cn.edu.bnuz.tms.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

class TmsDaoAuthenticationProvider extends DaoAuthenticationProvider {
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		Object salt = null;

		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}

		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}

		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt) && 
			!passwordEncoder.isPasswordValid(userDetails.getPasswordEs(), presentedPassword, salt)) { // verify es password
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}
	}
}
