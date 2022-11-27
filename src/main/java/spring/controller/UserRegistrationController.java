package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.dto.UserRegistrationDto;
import spring.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

    //give my html\thymleaf the user obj for registration form \html
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

	//send you to the html file with the same nameeee
	//http get request
	@GetMapping
	public String showRegistrationForm() {
		//showRegistrationForm(Model model)
		//model.addAttribute("user",new UserRegistrationDto());
		return "registration";
	}
	// we take the user obj and bind the data to the UserRegistrationDto
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		userService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
