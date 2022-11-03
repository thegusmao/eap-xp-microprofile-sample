package br.com.redhat.microprofile.controller;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import br.com.redhat.microprofile.model.Service;
import br.com.redhat.microprofile.repository.ServiceRepository;

@Path("services")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ServiceController {
    
    @Inject
    ServiceRepository repository;

    @Operation(summary = "Return all services")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Counted(name = "ServicesGETCount", displayName="Count of GET calls", description = "How many GET calls for services where executed.")
    @Timed(name = "ServicesGETTimer", absolute = true, description = "A measure of how long it takes to perform the get call.", unit = MetricUnits.MILLISECONDS)
    @GET
    public Response listServices() {
        List<Service> list = repository.findAllServices();
        return Response.ok(list).build();
    }

    @Operation(summary = "Creates a new service")
    @APIResponses({
        @APIResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Counted(name = "ServicesPOSTCount", displayName="Count of POST calls", description = "How many Services where created.")
    @POST
    public Response createService(Service service) {
        repository.createService(service);
        return Response.created(URI.create("")).build();
    }

    @Operation(summary = "Edit a service")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Path("{id}")
    @PUT
    public Response edit(@PathParam("id") Long id, Service service) {
        service.setId(id);
        repository.updateService(service);
        return Response.ok(service).build();
    }

    @Operation(summary = "Delete a service")
    @APIResponses({
        @APIResponse(responseCode = "204", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Counted(name = "ServicesDELETECount", displayName="Count of DELETE calls", description = "How many Services where deleted.")
    @Path("{id}")
    @DELETE
    public Response delete(@PathParam("id") Long id) {
        repository.deleteService(id);
        return Response.noContent().build();
    }

    @Operation(summary = "Method that fails to fallback")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    @Counted(name = "ServicesFailCount", displayName="Count of fail endpoint calls", description = "How many times fail endpoint was called.")
    @Retry(maxRetries = 1)
    @CircuitBreaker(failOn = Exception.class)
    @Fallback(fallbackMethod = "fallbackMethod")
    @Path("fail")
    @GET
    public Response failWithFallback() throws Exception {
        throw new Exception("Method for testing fallback.");
    }

    public Response fallbackMethod() {
        return Response.ok("{ \"fallback\": true }").build();
    }
}
