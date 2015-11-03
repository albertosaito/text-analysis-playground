package io.vuh.text.elasticsearch;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    @Path("articles")
    public void pushAllArticles();

    /**
     * Pushes a specific {@link Article} from the persistence layer to
     * ElasticSearch.
     * 
     * @param id
     *            of the article to be pushed
     */
    @POST
    @Path("articles")
    public void pushArticleById(String id);

    @POST
    @Path("search")
    public List<Article> search(String queryString);

}
