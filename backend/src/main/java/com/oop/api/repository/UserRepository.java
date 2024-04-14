package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.User;
/**
 * The UserRepository interface is responsible for providing CRUD operations
 * for the User entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the User entity.
 * 
 * The UserRepository interface also provides a custom method, findByEmail,
 * which allows for finding a user by their email address.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return an Optional containing the user with the specified email, or an empty Optional if no user is found
     */
    Optional<User> findByEmail(String email);
}
