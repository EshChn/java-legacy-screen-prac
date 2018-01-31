package com.usecases;

import com.entity.JobApplicant;
import com.entity.Location;

import java.io.IOException;
import java.net.URISyntaxException;

public interface JobApplicantInteractor {
    public JobApplicant createJobApplicant();
    public String formatLastNameFirst();
    public int validateName();
    public String formatSsn();
    public int validateSsn();
    public void save(JobApplicant jobApplicant);
    public void setLocation(String zipcode)throws IOException, URISyntaxException;

}
