package com.usecases;

import static org.junit.Assert.assertEquals;

import com.entity.Location;
import com.lookup.LocationLookup;
import com.entity.JobApplicant;
import com.lookup.AZLocationLookupStub;
import com.lookup.TXLocationLookupStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class JobApplicantInteractorTest {

    private JobApplicant jobApplicant;
    private JobApplicantInteractor jobApplicantInteractor;
    private LocationLookup locationLookup;
    private JobApplicantGateway jobApplicantGateway;

    @Before
    public void setup(){
        jobApplicantInteractor = new JobApplicantInteractorImpl();
        jobApplicant =  jobApplicantInteractor.createJobApplicant();
        jobApplicantGateway = new JobApplicationGatewayStub();
    }

    @Test
    public void formatEnglishNameLastNameFirst() {
        jobApplicant.setName("First", "Middle", "Last");
        assertEquals("Last, First Middle", jobApplicantInteractor.formatLastNameFirst());
    }


    @Test
    public void completeNameProvided() {
        jobApplicant.setName("First", "Middle", "Last");
        assertEquals(0, jobApplicantInteractor.validateName());
    }

    @Test
    public void firstAndLastNamesProvided() {
        jobApplicant.setName("First", null, "Last");
        assertEquals(0, jobApplicantInteractor.validateName());
    }

    @Test
    public void missingFirstName() {
        jobApplicant.setName(null, null, "Last");
        assertEquals(6, jobApplicantInteractor.validateName());
    }

    @Test
    public void missingLastName() {
        jobApplicant.setName("First", null, null);
        assertEquals(6, jobApplicantInteractor.validateName());
    }

    @Test
    public void completeSpanishNameProvided() {
        jobApplicant.setSpanishName("PrimerNombre", "SegundoNombre", "PrimerApellido", "SegundoApellido");
        assertEquals(0, jobApplicantInteractor.validateName());
    }

    @Test
    public void spanishNameWithOneFirstNameProvided() {
        jobApplicant.setSpanishName("PrimerNombre", null, "PrimerApellido", "SegundoApellido");
        assertEquals(0, jobApplicantInteractor.validateName());
    }

    @Test
    public void spanishNameWithOneLastNameProvided() {
        jobApplicant.setSpanishName("PrimerNombre", null, "PrimerApellido", null);
        assertEquals(0, jobApplicantInteractor.validateName());
    }

    @Test
    public void spanishNameWithNoFirstNameProvided() {
        jobApplicant.setSpanishName(null, null, "PrimerApellido", null);
        assertEquals(6, jobApplicantInteractor.validateName());
    }

    @Test
    public void spanishNameWithNoLastNameProvided() {
        jobApplicant.setSpanishName("PrimerNombre", "SegundoNombre", null, null);
        assertEquals(6, jobApplicantInteractor.validateName());
    }

    @Test
    public void ssnFormattingTest() {
        jobApplicant.setSsn("123456789");
        assertEquals("123-45-6789", jobApplicantInteractor.formatSsn());
    }

    @Test
    public void validSsnWithNoDashes() {
        jobApplicant.setSsn("123456789");
        assertEquals(0, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnWithDashesInWrongPlaces() {
        jobApplicant.setSsn("12-3456-789");
        assertEquals(1, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void validSsnWithDashes() {
        jobApplicant.setSsn("123-45-6789");
        assertEquals(0, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnIsTooShort() {
        jobApplicant.setSsn("12345678");
        assertEquals(1, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnIsTooLong() {
        jobApplicant.setSsn("1234567890");
        assertEquals(1, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnAreaNumberIs000() {
        jobApplicant.setSsn("000223333");
        assertEquals(2, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnAreaNumberIs666() {
        jobApplicant.setSsn("666223333");
        assertEquals(2, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnAreaNumberStartsWith9() {
        jobApplicant.setSsn("900223333");
        assertEquals(2, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void ssnSerialNumberIs0000() {
        jobApplicant.setSsn("111220000");
        assertEquals(3, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void itRejectsSsn078051120() {
        jobApplicant.setSsn("078051120");
        assertEquals(4, jobApplicantInteractor.validateSsn());
    }

    @Test
    public void itRejectsSsn219099999() {
        jobApplicant.setSsn("219099999");
        assertEquals(4, jobApplicantInteractor.validateSsn());
    }


    @Test
    public void itFindsAddisonTexasBy5DigitZipCode() throws URISyntaxException, IOException {

        locationLookup = new TXLocationLookupStub();
        Location cityandState = locationLookup.locateCityandState("75001");
        assertEquals("Addison", cityandState.getCity());
        assertEquals("TX", cityandState.getState());

    }

    @Test
    public void itFindsMaranaArizonaBy9DigitZipCode() throws URISyntaxException, IOException {

        locationLookup = new AZLocationLookupStub();
        Location cityandState = locationLookup.locateCityandState("856585578");
        assertEquals("Marana", cityandState.getCity());
        assertEquals("AZ", cityandState.getState());
    }

    @Test
    public void saveJobApplicant(){
        jobApplicantGateway.save(jobApplicant);
    }

}
