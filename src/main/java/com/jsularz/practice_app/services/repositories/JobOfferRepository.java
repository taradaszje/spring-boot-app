package com.jsularz.practice_app.services.repositories;

import com.jsularz.practice_app.models.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
}
