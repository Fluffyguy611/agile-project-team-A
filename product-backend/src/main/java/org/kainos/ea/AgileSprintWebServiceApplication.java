package org.kainos.ea;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.controller.AuthController;
import org.kainos.ea.controller.BandController;
import org.kainos.ea.controller.CapabilityController;
import org.kainos.ea.controller.JobRoleController;
import org.kainos.ea.controller.RestrictedOperationsRequestFilter;

public class AgileSprintWebServiceApplication extends Application<AgileSprintWebServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AgileSprintWebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardWebService";
    }

    @Override
    public void initialize(final Bootstrap bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<AgileSprintWebServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AgileSprintWebServiceConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(AgileSprintWebServiceConfiguration dropwizardWebServiceConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new AuthController());
        environment.jersey().register(new JobRoleController());
        environment.jersey().register(new BandController());
        environment.jersey().register(new CapabilityController());
        environment.jersey().register(new RestrictedOperationsRequestFilter());
    }
}
