package br.com.redhat.microprofile;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;


@Server(url = "/microprofile", description = "localhost")
@OpenAPIDefinition(info = @Info(
        title = "Microprofile reference application", 
        version = "1.0.0"
))
@ApplicationPath("/")
public class CoffeeApplication extends Application {
    
}
