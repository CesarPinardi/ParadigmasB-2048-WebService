/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author cesar
 */
@Path("generic")
public class ws2048 {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WS
     */
    public ws2048() {
    }

    /**
     * Retrieves representation of an instance of ws.WS
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
       return "TESTE JSON WEBSERVICE";
    }
  
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/2048/left")
    public String controlsLeft(){
        Comandos c = new Comandos();
        c.setLeft("1"); 
        Gson g = new Gson();
        return g.toJson(c);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/2048/right")
    public String controlsRight(){
        Comandos c = new Comandos();
        c.setRight("2"); 
        Gson g = new Gson();
        return g.toJson(c);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/2048/up")
    public String controlsUp(){
        Comandos c = new Comandos();
        c.setUp("3"); 
        Gson g = new Gson();
        return g.toJson(c);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/2048/down")
    public String controlsDown(){
        Comandos c = new Comandos();
        c.setDown("4"); 
        Gson g = new Gson();
        return g.toJson(c);
    }
    
    
    
}
