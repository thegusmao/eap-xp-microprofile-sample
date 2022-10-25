package br.com.redhat.microprofile.health;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessCheck implements HealthCheck {

    private static Logger logger = Logger.getLogger(LivenessCheck.class.getName());

    @Override
    public HealthCheckResponse call() {
        logger.info(String.format("Calling %s", this.getClass().getSimpleName()));
        
        return HealthCheckResponse
            .named("liveness-test")
            .withData("liveness-test", true)
            .up()
            .build();
    }
    
}
