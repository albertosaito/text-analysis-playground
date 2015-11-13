package io.vuh.text.elasticsearch;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import io.vuh.text.persistence.model.Article;

/**
 * REST endpoint interface to interact with the system
 *
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Path("/v1/")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ElasticSearchResource {

    @GET
    @Path("search")
    public List<Article> search(@QueryParam(value = "query") String queryString);
}
