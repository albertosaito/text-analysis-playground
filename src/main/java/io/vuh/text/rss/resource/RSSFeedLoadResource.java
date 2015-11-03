package io.vuh.text.rss.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.vuh.text.rss.resource.transport.LoadRSSResponse;

@Path("/v1/")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface RSSFeedLoadResource {

    @POST
    @Path("rss")
    public LoadRSSResponse loadRSSFeed(String[] url);

}
