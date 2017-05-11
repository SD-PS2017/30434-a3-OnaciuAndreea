package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import main.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
	@Transactional
    void deleteByUsername(String name);
	
	@Transactional
    void deleteById(Long id);
}
