package integration;


import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.restassured.response.ValidatableResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.AgileSprintWebServiceApplication;
import org.kainos.ea.AgileSprintWebServiceConfiguration;
import org.kainos.ea.cli.JobRole;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
@ExtendWith(MockitoExtension.class)
public class JobRolesIT {
    static final DropwizardAppExtension<AgileSprintWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            AgileSprintWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider());
    private String apiUrl = System.getenv("API_URL");
    private String jobRoles = "/api/job-roles/";

   @Test
   void When_GettingNonExistingJobRole_Then_Expect_BadRequestReturned() {
       when()
               .get(apiUrl + jobRoles + "5999")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500);
    }

    @Test
    void When_GettingExistingJobRole_Then_Expect_200OkReturned() {
        when()
                .get(apiUrl + jobRoles + "5")
                .then()
                .statusCode(HttpStatus.OK_200);
    }

    @Test
    void When_GettingJobRole2_Then_Expect_NameEqualPrincipal() {
        JobRole expectedJobRole = new JobRole(2, "Principal", "A high-level position with significant responsibilities in the organization.", "https://example.com/principal" );
        String message = "Actual name is not equal expected";
        ValidatableResponse response = given().header("Content-Type", "application/json")
                .get(apiUrl + jobRoles + "2")
                .then();
        response.statusCode(HttpStatus.OK_200);

        JobRole receivedJobRole = response.extract().body().as(JobRole.class);
        assertThat(receivedJobRole).usingRecursiveComparison()
                .isEqualTo(expectedJobRole);

    }

    @Test
    void When_GettingJobRole2_Then_Expect_Description() {
        String expectedDescription = "Forfiter";
        String message = "Description is incorrect ";
        ValidatableResponse response = given().header("Content-Type", "application/json")
                .get(apiUrl + jobRoles + "2")
                .then();
        response.statusCode(HttpStatus.OK_200);
        String actualDescription = response.extract().body().jsonPath().get("description").toString();
        assertEquals(expectedDescription, actualDescription, message);
    }
}
