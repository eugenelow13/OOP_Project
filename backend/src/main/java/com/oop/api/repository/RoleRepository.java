package com.oop.api.repository;

import com.oop.api.model.Role;
import com.oop.api.model.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The RoleRepository interface is responsible for providing CRUD operations
 * for the Role entity.
 * 
 * This interface extends the CrudRepository interface provided by Spring Data JPA,
 * which allows for basic CRUD operations to be performed on the Role entity.
 * 
 * The RoleRepository interface also provides a custom method, findByName,
 * which allows for finding a role by its name.
 * 
 * This interface is annotated with the @Repository annotation, which indicates
 * that it is a repository component in the Spring framework.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    /**
     * Finds a role by its name.
     *
     * @param name The name of the role.
     * @return An Optional containing the role if found, or an empty Optional if not found.
     */
    Optional<Role> findByName(RoleEnum name);
}
