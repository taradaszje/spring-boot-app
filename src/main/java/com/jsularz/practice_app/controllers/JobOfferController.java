package com.jsularz.practice_app.controllers;

import com.jsularz.practice_app.models.JobOffer;
import com.jsularz.practice_app.services.JobOfferService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<JobOffer> presentAllJobOffers(final @RequestParam(required = false) Long id){
        if(id != null){
            return Collections.singletonList(jobOfferService.findOne(id));
        }else{
            return jobOfferService.findAll();
        }
    }


    @GetMapping(value = "/jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage() throws IOException {
        Resource resource = new ClassPathResource("static/images/uscisk.jpg");
        InputStream input = resource.getInputStream();
        return IOUtils.toByteArray(input);
    }
}
