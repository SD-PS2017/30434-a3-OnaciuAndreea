package main.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import main.entities.Role;
import main.entities.User;
import main.repository.RoleRepository;
import main.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
    	List<User> users=userRepository.findByUsername(username);
    	if (users.isEmpty()) return null;
        return users.get(0);
    }
    
    public List<User> readUsers(){
		List<User> users=new ArrayList<User>();		
		users=userRepository.findAll();
		return  users;
	}
	
	public User readUser(String name){
		User user=userRepository.findByUsername(name).get(0);
		if (user==null) return null;
		return user;
	}
	
	public void updateUser(String name,User newUser){
		User userToSave=userRepository.findByUsername(name).get(0);	
		userToSave.setPassword(newUser.getPassword());
		userToSave.setSalary(newUser.getSalary());
		userToSave.setName(newUser.getName());
		userRepository.save(userToSave);
	}
	
	public void deleteUser(String name){
		userRepository.deleteByUsername(name);		
	}
	
	public void deleteUserById(Long id){
		userRepository.deleteById(id);
	}
}
