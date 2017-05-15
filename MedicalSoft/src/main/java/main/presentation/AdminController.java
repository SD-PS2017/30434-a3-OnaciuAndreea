package main.presentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.entities.Doctor;
import main.entities.Role;
import main.entities.Specialization;
import main.entities.User;
import main.repository.RoleRepository;
import main.service.DoctorService;
import main.service.SecurityService;
import main.service.UserServiceImpl;
import main.validator.UserValidator;


@Controller
public class AdminController {

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private RoleRepository roleRepo;

	
    @RequestMapping(value = {"/","/welcome"}, method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("userForm") User userForm,HttpServletRequest request, 
	        HttpServletResponse response, BindingResult bindingResult, Model model) {
        
    	String isValid=userValidator.validateUser(userForm);
        if (!isValid.equals("")) {
            model.addAttribute("error",isValid);
        }
        else {
        	String role=request.getParameter("role");
        		System.out.println("The role is"+role);
        	Set<Role> hs=new HashSet<Role>();
            Role r=roleRepo.findByName(role);
            hs.add(r);
            userForm.setRoles(hs);
            userService.save(userForm);

        	if (r.toString().equals("ROLE_DOCTOR")){
            	String type=request.getParameter("type");
            	System.out.println(type);
            	Doctor d=new Doctor(userForm.getUsername(),userForm.getPassword(),userForm.getPasswordConfirm(),
            			userForm.getName(),userForm.getSalary(),Specialization.valueOf(type));
            	System.out.println(Specialization.valueOf(type).toString());
            	doctorService.save(d);
            } 

        	model.addAttribute("success","User saved successfully");
        }
        return "welcome";
    }

    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(Model model) {
    	model.addAttribute("deleteUser",new User());
        return "deleteUser";
    }
    
    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(Model model) {
    	model.addAttribute("editUser",new User());
        return "editUser";
    }

        
    @RequestMapping(value = "/seeUsers", method = RequestMethod.GET)
    public String seeUsers(Model model) {
    	List<User> employees=userService.readUsers();
    	model.addAttribute("employees",employees);
        return "seeUsers";
    }
    
    
    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public String searchUser(@ModelAttribute("deleteUser") User user, Model model) {
    	System.out.println("deleteUser");
    	User userToDelete=new User();
    	user.setSalary(0.0f);
		userToDelete=userService.findByUsername(user.getUsername());
		if (userToDelete==null){
			model.addAttribute("error","There is no user with this name !");
			System.out.println("user to delete null");
		}
		else{
			userService.deleteUser(userToDelete.getUsername());
			model.addAttribute("finalMessage","User was successfully deleted!");
			System.out.println("user deleted");
		}
	
		return "deleteUser";
     }
    
    @RequestMapping(value="/editUser",method=RequestMethod.POST)
    public String editUser(@ModelAttribute("editUser")User user,Model model){
    	User toEdit=new User();
    	toEdit=userService.findByUsername(user.getUsername());
    	if (toEdit==null) {
    		model.addAttribute("error","No user find in the db with this username.");
    	}
    	else {
    		if (user.getName()==null || user.getName().equals("")){
    			model.addAttribute("editUser",toEdit);
    		}
    		else {
    			if (user.getSalary()<=0) {
    				model.addAttribute("err","Invalid salary.");
    			}
    			else {
    			    user.setPassword(toEdit.getPassword());
    			    userService.updateUser(user.getUsername(), user);
    			    model.addAttribute("success","User updated successfully!");
    			}
    		}
    	}
    	
    	return "editUser";
    }
    
    
}
