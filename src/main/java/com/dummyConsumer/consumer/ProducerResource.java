package com.dummyConsumer.consumer;

import com.kumuluz.ee.discovery.annotations.DiscoverService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/producer")
@ApplicationScoped
public class ProducerResource {

    @Inject
    @DiscoverService(value = "dummy-producer", version = "1.0.0", environment = "dev")
    private WebTarget tProducer;

    @GET
    @Path("url")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUrl() {
        return Response.ok(tProducer.getUri().toString()).build();
    }

    @GET()
    public Response getProxiedCustomers() {
        WebTarget service = tProducer.path("v1/producer");

        Response response;
        try {
            response = service.request().get();
        } catch (ProcessingException e) {
            return Response.status(408).build();
        }

        ProxiedResponse proxiedResponse = new ProxiedResponse();
        proxiedResponse.setResponse(response.readEntity(String.class));
        proxiedResponse.setProxiedFrom(tProducer.getUri().toString());

        return Response.ok(proxiedResponse).build();
    }
}
