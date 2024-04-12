package com.oop.api.repository;

import com.oop.api.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

/**
 * The TokenRepository interface is responsible for providing CRUD operations
 * for the Token entity.
 * 
 * This interface extends the JpaRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the Token entity.
 * 
 * The TokenRepository interface also provides custom methods, findAllTokensByUser
 * and findByToken, which allow for retrieving tokens associated with a specific user
 * and finding a token by its value, respectively.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    /**
     * Retrieves all tokens associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of tokens associated with the user
     */
    @Query("""
        select t from Token t inner join User u on t.user.id = u.id
        where t.user.id = :userId and t.loggedOut = false
        """)
    List<Token> findAllTokensByUser(Integer userId);

    /**
     * Retrieves a token by its value.
     *
     * @param token the value of the token
     * @return an Optional containing the token, or an empty Optional if not found
     */
    Optional<Token> findByToken(String token);
}
