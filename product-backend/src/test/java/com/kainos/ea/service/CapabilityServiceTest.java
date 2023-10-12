package com.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.CapabilityDoesNotExistException;
import org.kainos.ea.exception.FailedToCreateCapabilityLeadException;
import org.kainos.ea.exception.FailedToGetCapabilityException;
import org.kainos.ea.exception.InvalidCapabilityLeadException;
import org.kainos.ea.model.Capability;
import org.kainos.ea.model.CapabilityRequest;
import org.kainos.ea.service.CapabilityService;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;


public class CapabilityServiceTest {

    CapabilityDao capabilityDao = Mockito.mock(CapabilityDao.class);

    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    CapabilityService capabilityService = new CapabilityService(databaseConnector, capabilityDao);

    Capability capability = new Capability(
            1,
            "capability1",
            "Moczywór",
            "Principal",
            "This is a test case"
    );


    @Test
    void getEveryCapability_When_ThereAreCapabilities_Expect_CapabilitiesToBeReturned() throws SQLException, FailedToGetCapabilityException, CapabilityDoesNotExistException, FailedToCreateCapabilityLeadException {

        Connection conn = databaseConnector.getConnection();

        Capability capability = new Capability(
                1,
                "capability1",
                "Moczywór",
                "Principal",
                "This is a test case"
        );

        List<Capability> mockedList = new ArrayList<>();
        mockedList.add(capability);
        when(capabilityDao.getEveryCapabilityLead(conn)).thenReturn(mockedList);

        List<Capability> capabilityList = capabilityService.getEveryCapabilityLead();

        assertThat(capabilityList).isEqualTo(mockedList);
    }

    @Test
    void getEveryCapability_When_ThereIsNoCapabilities_Expect_CapabilityDoesNotExistExceptionToBeThrown() throws SQLException {
        Connection conn = databaseConnector.getConnection();
        when(capabilityDao.getEveryCapabilityLead(conn)).thenReturn(new ArrayList<>());

        assertThatExceptionOfType(CapabilityDoesNotExistException.class)
                .isThrownBy(() -> capabilityService.getEveryCapabilityLead());
    }

    @Test
    void getEveryCapability_When_ThereIsDatabaseError_Expect_FailedToGetCapabilityExceptionToBeThrown() throws SQLException {
        Connection conn = databaseConnector.getConnection();
        when(capabilityDao.getEveryCapabilityLead(conn)).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToGetCapabilityException.class)
                .isThrownBy(() -> capabilityService.getEveryCapabilityLead());
    }

    @Test
    void createCapability_When_CapabilityInputIsValid_Expect_NewCapabilityToBeReturned() throws FailedToCreateCapabilityLeadException, SQLException, InvalidCapabilityLeadException {
        Connection conn = databaseConnector.getConnection();
        CapabilityRequest mockedCapabilityRequest = new CapabilityRequest("MockedCapability", "MockedName", "MockedPhoto", "MockedMessage");

        capabilityService.createCapabilityLead(mockedCapabilityRequest);

        verify(capabilityDao).createCapabilityLead(mockedCapabilityRequest, conn);
    }

    @Test
    void createCapability_When_ThereIsDatabaseError_Expect_FailedToCreateCapabilityLeadException() throws SQLException, FailedToCreateCapabilityLeadException { //kiedy wystapi blad z v=baza dancyh rzuc wyjatkiem failedToCreate
        Connection conn = databaseConnector.getConnection();
        CapabilityRequest mockedCapabilityRequest = new CapabilityRequest("MockedCapability", "MockedName", "MockedPhoto", "MockedMessage");
        doThrow(new FailedToCreateCapabilityLeadException()).when(capabilityDao).createCapabilityLead(mockedCapabilityRequest, conn);

        assertThatExceptionOfType(FailedToCreateCapabilityLeadException.class)
                .isThrownBy(() -> capabilityService.createCapabilityLead(mockedCapabilityRequest))
                .withMessageMatching("Failed to create capability");
    }
}
