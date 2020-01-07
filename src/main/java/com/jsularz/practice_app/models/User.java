package com.jsularz.practice_app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "email")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private char[] password;
    private String email;
    private LocalDateTime createdOn;
    private LocalDateTime lastLogin;
    private boolean status = false;

    @OneToOne(mappedBy = "user")
    private VerificationToken verificationToken;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(final Role role){
        if(role != null){
            roles.add(role);
            role.getUsersSet().add(this);
        }
    }

    public void removeRole(final Role role){
        if(role != null) {
            roles.remove(role);
            role.getUsersSet().remove(this);
        }
    }
}
