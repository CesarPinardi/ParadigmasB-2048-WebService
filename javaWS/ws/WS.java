/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import modelo.Comandos;
import modelo.Usuario;

/**
 * REST Web Service
 *
 * @author cesar
 */
@Path("generic")
public class WS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WS
     */
    public WS() {
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
    
    
    
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Usuario/list")
    public String listUsuarios() {
        List<Usuario> lista = new ArrayList<Usuario>();
        Usuario u = new Usuario();
        u.setEmail("1)email");
        u.setLogin("1)login");
        u.setSenha("1)senha");
        u.setPerfil("1)perfil");
       
        lista.add(u);
        
        u.setEmail("2)email");
        u.setLogin("2)login");
        u.setSenha("2)senha");
        u.setPerfil("2)perfil");
       
        lista.add(u);
        
        Gson g = new Gson();
        return g.toJson(lista);
       
    }

    */
    
    
    /**
     * PUT method for updating or creating an instance of WS
     * @param content representation for the resource
     */
   
}
