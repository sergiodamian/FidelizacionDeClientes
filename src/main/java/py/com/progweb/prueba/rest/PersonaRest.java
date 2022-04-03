/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.progweb.prueba.ejb.PersonaDao;
import py.com.progweb.prueba.model.Persona;

/**
 * Capa de presentacion Rest de Persona
 *
 * @author Sergio D. Riveros Vazquez
 */
@Path(value = "persona")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonaRest {

    @Inject
    private PersonaDao personaDao;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(personaDao.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(Persona p) {
        this.personaDao.agregar(p);
        return Response.ok().build();
    }
    
   
}
