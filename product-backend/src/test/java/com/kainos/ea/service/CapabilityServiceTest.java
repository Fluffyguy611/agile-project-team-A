package com.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.kainos.ea.service.CapabilityService;
import org.kainos.ea.model.Capability;
import org.kainos.ea.exception.CapabilityDoesNotExistException;
import org.kainos.ea.exception.FailedToGetCapabilityException;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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

    Connection conn;


    @Test
    void getEveryCapability_When_ThereAreProducts_Expect_CapabilitiesToBeReturned() throws SQLException, FailedToGetCapabilityException, CapabilityDoesNotExistException {
        List<Capability> mockedList= List.of(capability);
        when(capabilityDao.getEveryCapabilityLead(conn)).thenReturn(Optional.of(mockedList));

        List<Capability> capabilityList = capabilityService.getEveryCapabilityLead();

        assertThat(capabilityList).isEqualTo(mockedList);
    }

    @Test
    void getEveryCapability_When_ThereIsNoCapabilities_Expect_CapabilityDoesNotExistExceptionToBeThrown() throws CapabilityDoesNotExistException, SQLException {
        when(capabilityDao.getEveryCapabilityLead(conn)).thenReturn(Optional.empty());

        assertThatExceptionOfType(CapabilityDoesNotExistException.class)
                .isThrownBy(() -> capabilityService.getEveryCapabilityLead());
    }

    @Test
    void getEveryCapabilitys_When_ThereIsDatabaseError_Expect_FailedToGetCapabilityExceptionToBeThrown() throws SQLException {
        when(capabilityDao.getEveryCapabilityLead(conn)).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToGetCapabilityException.class)
                .isThrownBy(() -> capabilityService.getEveryCapabilityLead());
    }
}
