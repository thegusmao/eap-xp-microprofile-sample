package br.com.redhat.microprofile.health;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    private static Logger logger = Logger.getLogger(ReadinessCheck.class.getName());
    
    @Override
    public HealthCheckResponse call() {
        logger.info(String.format("Calling %s", this.getClass().getSimpleName()));
        
        return HealthCheckResponse
            .named("readiness-test")
            .withData("readiness-test", true)
            .up()
            .build()
        ;
    }
    
}
