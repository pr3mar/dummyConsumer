package com.dummyConsumer.consumer;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

class CustomUser {
    private int id;
    private String firstName;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {
    @GET
    public Response getAllUser() {
        List<User> users = DataBase.getUsers();
        return Response.ok(users).build();
    }

    @POST
    public Response createNewUser(User user) {
        DataBase.addUser(user);
        return Response.ok(true).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(
            @PathParam("id") int id
    ) {
        System.out.println(id);
        User user = DataBase.getUser(id);
        return Response.ok(user).build();
    }

    @GET
    @Path("test")
    public Response getUserByIdTest(@QueryParam("id") int id) {
        System.out.println("Hooraaay" + id);
        Client client = ClientBuilder.newClient();
        Response resp = client
                .target("http://localhost:8080/v1/users/")
                .path(String.valueOf(id)) // sets a parameter in the url -> .../users/{id}
             // .queryParam("id", "1") // sets a query parameter as in ?id=<someID>
                .request(MediaType.APPLICATION_JSON)
                .get();
        return Response.ok(resp.getEntity()).build();
    }
}
