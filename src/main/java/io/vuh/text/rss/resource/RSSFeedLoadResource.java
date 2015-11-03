package io.vuh.text.rss.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.vuh.text.rss.resource.transport.LoadRSSResponse;

@Path("/v1/")
public interface RSSFeedLoadResource {

    @POST
    @Path("rss")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    List<LoadRSSResponse> loadRSSFeed(final String[] url);

}
