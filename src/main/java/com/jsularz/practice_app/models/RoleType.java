package com.jsularz.practice_app.models;

public enum RoleType {
    SUPER_USER(1L,"This user has ultimate rights for everything"),
    ADMIN_USER(2L,"This user has admin rights for administrative work"),
    SITE_USER( 3L,"This user has access to site, after login - normal user");

    private Long id;
    private String description;

    RoleType(final Long id, final String description){
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
