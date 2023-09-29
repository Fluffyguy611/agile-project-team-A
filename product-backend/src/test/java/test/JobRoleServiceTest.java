package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.client.FailedToGetAllJobRolesException;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.cli.JobRole;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class JobRoleServiceTest {

  JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);

  JobRoleService jobRoleService = new JobRoleService(jobRoleDao);



 @Test
 void getJobRoles_shouldReturnJobRoles_whenDaoReturnsJobRoles() throws SQLException, FailedToGetAllJobRolesException {
 List<JobRole> mockedJobRole = new ArrayList<>();

 //given
 mockedJobRole.add(new JobRole(
            1,
            "name",
            "description",
            "sharePointLink"
 ));

 //preparing mock //when
//  Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
  Mockito.when(jobRoleService.getAllJobRoles()).thenReturn(mockedJobRole);


//calling the method to test
  List<JobRole> result = jobRoleService.getAllJobRoles();


  //comparison list
  assertEquals(result, mockedJobRole);

 }

@Test
void getJobRole_shouldThrowsFailedToGetAllJobRolesException_whenFailedToGetAllJobs() throws SQLException, FailedToGetAllJobRolesException {

//calling
 Mockito.when(jobRoleService.getAllJobRoles()).thenThrow(SQLException.class);

 //
    assertThatExceptionOfType(FailedToGetAllJobRolesException.class)
            .isThrownBy(() -> jobRoleService.getAllJobRoles())
            .withMessage("Failed to get job all job roles");

 }


}



