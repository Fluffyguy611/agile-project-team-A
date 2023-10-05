package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.model.BandRequest;
import org.kainos.ea.service.BandValidator;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BandValidatorTests {
    BandValidator bandValidator = new BandValidator();

    @Test
    public void WhenJobBandNameOver64CharactersExpectErrorMessage() {
        BandRequest bandRequest = new BandRequest("123451234512345123451234512345123412345123451234512345123451234512341234512345123451234512345123451234", 6);

        assertThat(bandValidator.isValidBandName(bandRequest).get()).isEqualTo("Name longer than 64 characters");
    }
}
