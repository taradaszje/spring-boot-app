package com.jsularz.practice_app.services.impl;

import com.jsularz.practice_app.models.JobOffer;
import com.jsularz.practice_app.services.JobOfferService;
import com.jsularz.practice_app.services.repositories.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public List<JobOffer> findAll() {
        return jobOfferRepository.findAll();
    }

    @Override
    public JobOffer findOne(Long id) {
        return jobOfferRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
