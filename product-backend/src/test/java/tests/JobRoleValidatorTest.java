package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.model.JobRoleRequest;
import org.kainos.ea.service.JobRoleValidator;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JobRoleValidatorTest {

    JobRoleValidator jobRoleValidator = new JobRoleValidator();

    @Test
    public void WhenJobRoleNameOver64CharactersExpectErrorMessage() {
        JobRoleRequest jobRoleRequest = new JobRoleRequest("123451234512345123451234512345123412345123451234512345123451234512341234512345123451234512345123451234", "Manages stuff", "Some SP Link", 1, 1);

        assertThat(jobRoleValidator.isValidJobRole(jobRoleRequest).get()).isEqualTo("Name longer than 64 characters");
    }

    @Test
    public void WhenJobRoleDescriptionOver2000CharactersExpectErrorMessage() {
        JobRoleRequest jobRoleRequest = new JobRoleRequest("12345141234512345123451234512345123451234", "MtuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages sanages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuffManages stuff", "Some SP Link", 1, 1);

        assertThat(jobRoleValidator.isValidJobRole(jobRoleRequest).get()).isEqualTo("Description longer than 2000 characters");
    }
}
