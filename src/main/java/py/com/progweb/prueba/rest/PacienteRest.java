/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.rest;

import controller.PacienteDao;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import py.com.progweb.prueba.model.Paciente;

/**
 *
 * @author nruiz
 */
@Path("paciente")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class PacienteRest {
    @Inject 
    private PacienteDao pacienteDao;
    
    @POST
    @Path("/")
    public Response crear(Paciente paciente){
        this.pacienteDao.CrearPaciente(paciente);
        return Response.ok().build();
    }
}
