package com.jsularz.practice_app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "users") // opcjonalne, hiberante defaultowo
@EqualsAndHashCode(of = "email")
public class User {   // bierze nazwe tabeli tak jak klasy

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(initialValue = 4, name = "seq")
    private Long id;
    private String username;
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
        roles.add(role);
        role.getUsersSet().add(this);
    }

    public void removeRole(final Role role){
        roles.remove(role);
        role.getUsersSet().remove(this);
    }
}
