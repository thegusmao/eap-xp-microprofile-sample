package br.com.redhat.microprofile.faulttolerance;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

public interface CoffeeResourceAPI {
    
    @Operation(summary = "Return all coffees [from Interface]")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    public List<Coffee> coffees();

    @Operation(summary = "Return one coffee recomendation [from Interface]")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @APIResponse(responseCode = "404", description = "Coffee not found")
    })
    public List<Coffee> recommendations(int id);

    @Operation(summary = "Return one coffee recomendation fallback [from Interface]")
    @APIResponses({
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
        @APIResponse(responseCode = "404", description = "Coffee not found")
    })
    public List<Coffee> recommendations2(int id);
}
