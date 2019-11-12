package com.jsularz.practice_app.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job_offers")
@Getter
@Setter
@EqualsAndHashCode(of = {"title", "company"})
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 15)
    private String salary;

    @Column(nullable = false, length = 30)
    private String company;
}
