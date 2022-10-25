package br.com.redhat.microprofile.faulttolerance;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/coffee")
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeResource implements CoffeeResourceAPI {
    @Inject
    private CoffeeRepositoryService coffeeRepository;

    private AtomicLong counter = new AtomicLong(0);

    @Operation(summary = "Return all coffees [from Class]")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @GET
    @Retry(maxRetries = 4)
    public List<Coffee> coffees() {
        final Long invocationNumber = counter.getAndIncrement();
        return coffeeRepository.getAllCoffees();
    }

    @GET
    @Path("/{id}/recommendations")
    @Timeout(250)
    public List<Coffee> recommendations(@PathParam("id") int id) {
        return coffeeRepository.getRecommendations(id);
    }

    @GET
    @Path("fallback/{id}/recommendations")
    @Fallback(fallbackMethod = "fallbackRecommendations")
    public List<Coffee> recommendations2(@PathParam("id") int id) {
        return coffeeRepository.getRecommendations(id);
    }

    public List<Coffee> fallbackRecommendations(int id) {
        //always return a default coffee
        return Collections.singletonList(coffeeRepository.getCoffeeById(1));
    }
}
