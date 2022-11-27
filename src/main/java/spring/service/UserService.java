package spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import spring.dto.UserRegistrationDto;
import spring.JPAEntities.User;

// extends UserDetailsService because needed for the login autentication
// at security config\DaoAuthenticationProvider\UserService
public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
