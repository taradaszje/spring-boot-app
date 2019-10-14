package com.jsularz.practice_app.services;

import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.models.RoleType;


public interface RoleService{
    Iterable<Role> findAll();
    Role findRole(final RoleType roleType);
}
