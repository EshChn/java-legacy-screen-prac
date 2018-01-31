package com.usecases;

import com.entity.JobApplicant;
import com.lookup.LocationLookup;

import java.io.IOException;
import java.net.URISyntaxException;

public class JobApplicantInteractorImpl implements JobApplicantInteractor{

    private JobApplicant jobApplicant;
    private LocationLookup locationLookup;
    private JobApplicantGateway jobApplicantGateway;

    public JobApplicant getJobApplicant() {
        return jobApplicant;
    }

    public void setJobApplicant(JobApplicant jobApplicant) {
        this.jobApplicant = jobApplicant;
    }

    public LocationLookup getLocationLookup() {
        return locationLookup;
    }

    public void setLocationLookup(LocationLookup locationLookup) {
        this.locationLookup = locationLookup;
    }

    public JobApplicantGateway getJobApplicantGateway() {
        return jobApplicantGateway;
    }

    public void setJobApplicantGateway(JobApplicantGateway jobApplicantGateway) {
        this.jobApplicantGateway = jobApplicantGateway;
    }

    @Override
    public JobApplicant createJobApplicant() {
        jobApplicant = new JobApplicant();
        return jobApplicant;
    }
    @Override
    public String formatLastNameFirst() {
        String firstName = jobApplicant.getFirstName();
        String lastName = jobApplicant.getLastName();
        String middleName = jobApplicant.getMiddleName();

        StringBuilder sb = new StringBuilder(lastName);
        sb.append(", ");
        sb.append(firstName);
        if ( middleName.length() > 0 ) {
            sb.append(" ");
            sb.append(middleName);
        }
        return sb.toString();
    }
    @Override
    public int validateName() {
        if ( jobApplicant.getFirstName().length() > 0 &&
                jobApplicant.getLastName().length() > 0 ) {
            return 0;
        } else {
            return 6;
        }
    }

    @Override
    public String formatSsn(){
        String ssn = jobApplicant.getSsn();
        StringBuilder sb = new StringBuilder(ssn.substring(0,3));
        sb.append("-");
        sb.append(ssn.substring(3,5));
        sb.append("-");
        sb.append(ssn.substring(5));
        return sb.toString();

    }

    @Override
    public int validateSsn() {
        String ssn = jobApplicant.getSsn();
        String specialCases[] = jobApplicant.getSpecialCases();

        if ( !ssn.matches("\\d{9}") ) {
            return 1;
        }
        if ( "000".equals(ssn.substring(0,3)) ||
                "666".equals(ssn.substring(0,3)) ||
                "9".equals(ssn.substring(0,1)) ) {
            return 2;
        }
        if ( "0000".equals(ssn.substring(5)) ) {
            return 3;
        }
        for (int i = 0 ; i < specialCases.length ; i++ ) {
            if ( ssn.equals(specialCases[i]) ) {
                return 4;
            }
        }
        return 0;

    }

    @Override
    public void setLocation(String zipcode) throws IOException, URISyntaxException {
        jobApplicant.setLocation(locationLookup.locateCityandState(zipcode));
    }


    @Override
    public void save(JobApplicant jobApplicant) {

    }


}
