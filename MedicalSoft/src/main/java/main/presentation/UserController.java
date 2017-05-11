package main.presentation;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.entities.Patient;
import main.entities.Specialization;
import main.entities.User;
import main.service.SecurityService;
import main.service.UserService;
import main.validator.UserValidator;

@Controller
public class UserController  {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = {"/registration"}, method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model, Authentication authentication,HttpServletRequest request, 
    	      HttpServletResponse response) throws IOException {
    	String url=determineTargetUrl(authentication);
    	model.addAttribute("userForm",new User());
    	Specialization[] enumSpecialization=Specialization.values();
    	model.addAttribute("specialization",enumSpecialization);
    	if (url.equals("welcome")) return "welcome";
    	else
        return "redirect:/"+url;
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isSecretary = false;
        boolean isAdmin = false;
        boolean isDoctor = false; 
        Collection<? extends GrantedAuthority> authorities
         = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_SECRETARY")) {
                isSecretary = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_DOCTOR")) {
                isDoctor = true;
                break;
            } 
        }
 
        if (isSecretary) {
            return "secretary";
        } else if (isAdmin) {
            return "welcome";
        } else if (isDoctor) {
        	return "doctor";
        } else {
            throw new IllegalStateException();
        }
    }
    

    
    @RequestMapping(value = "/secretary", method = RequestMethod.GET)
   	public String goToSecretaryMenu(Model model) {
    	model.addAttribute("patientForm",new Patient());
   		return "secretary";
   	}

     
}
