package com.jsularz.practice_app.services.impl;

import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.models.RoleType;
import com.jsularz.practice_app.services.RoleService;
import com.jsularz.practice_app.services.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRole(final RoleType roleType) {
        return roleRepository.findByName(roleType.name());
    }
}
