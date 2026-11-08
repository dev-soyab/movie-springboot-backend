package com.project.movie.repository;

import com.project.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String username);

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

}
