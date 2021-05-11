package org.acme.resources;

import org.acme.models.User;
import org.acme.services.UserService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @Counted(name = "ChecksUserGetAllPerformed")
    @Timed(name = "ChecksUserGetAllTimer", unit = MetricUnits.MILLISECONDS)
    public Response all() {
        List<User> users = userService.all();

        return Response
                .ok(users)
                .build();
    }

    @POST
    @Transactional
    public Response save(User user) {
        userService.save(user);

        return Response
                .created(UriBuilder.fromPath("user").build())
                .build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        User user = userService.getById(id);

        return Response
                .ok(user)
                .build();
    }
}