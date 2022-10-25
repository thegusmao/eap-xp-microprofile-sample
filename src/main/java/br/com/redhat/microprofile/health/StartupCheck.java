package br.com.redhat.microprofile.health;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

@Startup
@ApplicationScoped
public class StartupCheck implements HealthCheck {

    private static Logger logger = Logger.getLogger(StartupCheck.class.getName());

    @Override
    public HealthCheckResponse call() {
        logger.info(String.format("Calling %s", this.getClass().getSimpleName()));

        return HealthCheckResponse
            .named("startup-test")
            .withData("startup-test", true)
            .up()
            .build()
        ;
    }
    
}
