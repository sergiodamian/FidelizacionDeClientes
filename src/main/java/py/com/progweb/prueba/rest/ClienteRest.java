/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.rest;

import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import py.com.progweb.prueba.ejb.ClienteDao;

import py.com.progweb.prueba.model.Cliente;

/**
 *
 * @author nruiz
 */
@Path(value = "cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ClienteRest {
    @Inject
    private ClienteDao clienteDao;
    @GET
    @Path("/")
    public Response listar(){
        List<Cliente> resultado= clienteDao.getClientes();
        return Response.ok(resultado).build();
    }
    
    @GET
    @Path("/{cliente_id}")
    public Response getCliente(@PathParam("cliente_id") Integer cliente_id){
        Cliente cliente= this.clienteDao.getCliente(cliente_id);
        if(cliente==null){
            return Response.noContent().build();
        } 
        return Response.ok(cliente).build();
    }
    
    @PUT
    @Path("/")
    public Response crear(Cliente cliente) {
        this.clienteDao.crearCliente(cliente);
        return Response.ok().build();
    }
    
    @POST
    @Path("/actualizar")
    public Response UpdateCliente(Cliente cliente){
        this.clienteDao.UpdateCliente(cliente);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{cliente_id}")
    public Response DeleteCliente(@PathParam("cliente_id") Integer cliente_id){
    Cliente cliente= this.clienteDao.getCliente(cliente_id);
        if(cliente==null){
            return Response.status(404).build();
        }
        this.clienteDao.DeleteCliente(cliente);
        return Response.ok(cliente).build();
    }
}
