package com.neopragma.legacy.screen;

import com.entity.JobApplicant;
import com.lookup.LocationLookup;
import com.lookup.LocationLookupImpl;
import com.usecases.JobApplicantGatewayImpl;
import com.usecases.JobApplicantInteractor;
import com.usecases.JobApplicantInteractorImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class JobApplicantConsole {

    private static JobApplicantInteractorImpl jobApplicantInteractor;
    private static LocationLookup locationLookup;

    public static void main(String[] args) throws URISyntaxException, IOException {

        jobApplicantInteractor = new JobApplicantInteractorImpl();
        jobApplicantInteractor.setLocationLookup(new LocationLookupImpl());
        jobApplicantInteractor.setJobApplicantGateway(new JobApplicantGatewayImpl());
        JobApplicant jobApplicant = jobApplicantInteractor.createJobApplicant();

        boolean done = false;
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String middleName = "";
        String lastName = "";
        String ssn = "";
        String zipCode = "";
        while (!done) {
            System.out.println("Please enter info about a job candidate or 'quit' to quit");
            System.out.println("First name?");
            firstName = scanner.nextLine();
            if (firstName.equals("quit")) {
                scanner.close();
                System.out.println("Bye-bye!");
                done = true;
                break;
            }
            System.out.println("Middle name?");
            middleName = scanner.nextLine();
            System.out.println("Last name?");
            lastName = scanner.nextLine();
            System.out.println("SSN?");
            ssn = scanner.nextLine();
            System.out.println("Zip Code?");
            zipCode = scanner.nextLine();
            jobApplicant.setName(firstName, middleName, lastName);
            jobApplicant.setSsn(ssn);
            jobApplicantInteractor.setLocation(zipCode);
            jobApplicantInteractor.save(jobApplicant);
        }
    }

}
