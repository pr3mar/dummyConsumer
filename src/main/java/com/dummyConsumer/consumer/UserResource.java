package com.dummyConsumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
//import com.kumuluz.ee.logs.cdi.Log;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
@ApplicationScoped
public class UserResource {

    @Inject
    private ConfigProperties properties;

    //@Log
    @GET
    @Path("/config")
    public Response test() {
        String response =
                "{" +
                        "\"stringProperty\": \"%s\"," +
                        "\"booleanProperty\": %b," +
                        "\"integerProperty\": %d" +
                        "}";

        response = String.format(
                response,
                properties.getStringProperty(),
                properties.getBooleanProperty(),
                properties.getIntegerProperty());

        return Response.ok(response).build();
    }

    //@Log
    @GET
    public Response getAllUsers() {
        List<User> users = DataBase.getUsers();
        return Response.ok(users).build();
    }

    //@Log
    @POST
    public Response createNewUser(User user) {
        DataBase.addUser(user);
        return Response.ok(true).build();
    }

    //@Log
    @GET
    @Path("{id}")
    public Response getUserById(
            @PathParam("id") int id
    ) {
        System.out.println(id);
        User user = DataBase.getUser(id);
        return Response.ok(user).build();
    }

    //@Log
    @POST
    @Path("create")
    public Response createDummyUsers() {
        if (DataBase.isEmpty()) {
            System.out.println("Creating dummy users.");

            User m = new User();
            m.setId(0);
            m.setFirstName("Marko");
            m.setLastName("P");
            m.setMeta("[b]New feature 1");

            User m1 = new User();
            m1.setId(1);
            m1.setFirstName("Luka");
            m1.setLastName("P");
            m1.setMeta("[c]New feature 2");

            DataBase.addUser(m);
            DataBase.addUser(m1);
        }

        return Response.ok(DataBase.getUsers()).build();
    }

    //@Log
    @GET
    @Path("info")
    public Response getProjectInfo() {
        JSONObject jsonString = new JSONObject()
                .put("clani", new String[]{"mp2638"})
                .put("opis_projekta", "Ta projekt je IoT data aggregator.\n" +
                        "Na koncu bo imel naslednje mikrostoritve:\n" +
                        "- skrbi o komunikaciji s posameznimi napravami,\n" +
                        "- za analizo vhodnih podatkov\n" +
                        "- ki strezi vse podatke v raw obliki\n" +
                        "- ki generira porocila o podatkih v dolocenem casovnem obdobju\n" +
                        "- improviziran front-end\n" +
                        "- uporabniki, ki imajo svoje naprave\n" +
                        "OPOMBA: Pri tej oddaji so samo 2 servisa, ki imata dummy podatke, kako bi implementiral cim vec funkcionalnost.")
                .put("mikrostoritve", new String[]{"http://169.51.13.160:31933/v1/users/", "http://169.51.13.160:30350/v1/producer/", })
                .put("github", new String[]{"https://github.com/rsoStream/dummyConsumer", "https://github.com/rsoStream/Producer", })
                .put("travis", new String[]{"https://travis-ci.org/rsoStream/dummyConsumer", "https://travis-ci.org/rsoStream/Producer", })
                .put("dockerhub", new String[]{"https://hub.docker.com/r/rsostream/"});


        return Response.ok(jsonString.toString()).build();
    }

    @Inject
    @DiscoverService(value = "dummy-producer", version = "1.0.0", environment = "dev")
    private WebTarget tProducer;

    //@Log
    @GET
    @Path("url")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUrl() {
        return Response.ok(tProducer.getUri().toString()).build();
    }

    //@Log
    @GET
    @Path("movies")
    public Response getProxiedCustomers() throws IOException {
        System.out.println("getting via discovery service");

        WebTarget service = tProducer.path("v1/producer");

        Response response = HandleResponces.getResponse(service);
        if (response != null) {
            Movie[] data = new ObjectMapper().readValue(response.readEntity(String.class), Movie[].class);
            return Response.ok(data).build();
        } else {
            return Response.noContent().build();
        }

    }

    //@Log
    @GET
    @Path("movie/{id}")
    public Response getProxiedCustomers(@PathParam("id") int id) throws IOException {
        System.out.println("getting via discovery service");

        WebTarget service = tProducer.path("v1/producer/" + id);

        Response response = HandleResponces.getResponse(service);

        if (response != null) {
            Movie data = new ObjectMapper().readValue(response.readEntity(String.class), Movie.class);
            return Response.ok(data).build();
        } else {
            return Response.noContent().build();
        }
    }
}
