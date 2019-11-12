package com.jsularz.practice_app.services;

import com.jsularz.practice_app.models.JobOffer;

import java.util.List;

public interface JobOfferService {
    List<JobOffer> findAll();
    JobOffer findOne(final Long id);
}
