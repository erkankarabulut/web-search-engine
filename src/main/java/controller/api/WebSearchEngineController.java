package controller.api;

import controller.provider.WebSearchEngineProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

// Controller class for the api

@Path("/")
public class WebSearchEngineController {

    /**
     * Get url list for the given search query
     *
     * @param request HttpServletRequest
     * @return JSONData
     */

    @GET
    @Path("/")
    @Produces("application/json;charset=utf-8")
    public Response getUrlList(@Context HttpServletRequest request, @QueryParam("searchQuery") String searchQuery,
                               @QueryParam("start") Integer start, @QueryParam("length") Integer length){
        JSONObject data = WebSearchEngineProvider.getUrlList(searchQuery, start, length);

        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }

    /**
     * Get url list for the given search query
     *
     * @param request HttpServletRequest
     * @param form    MultivaluedMap<String, String>
     * @return JSONData
     */


    @POST
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces("application/json;charset=utf-8")
    public Response getDefinitionsImages(@Context HttpServletRequest request, MultivaluedMap<String,String> form){
        JSONObject data = WebSearchEngineProvider.getDefinitionsAndImages(form);

        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }

}
