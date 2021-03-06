package com.jsularz.practice_app.services.repositories;

import com.jsularz.practice_app.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(final String name);
}
