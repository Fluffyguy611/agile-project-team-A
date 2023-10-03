package integration;



public class AddJobeRole {

    @ExtendWith(DropwizardExtensionsSupport.class)

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
}
