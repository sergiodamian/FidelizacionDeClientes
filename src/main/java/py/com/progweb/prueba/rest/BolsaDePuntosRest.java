/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import java.net.URISyntaxException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import py.com.progweb.prueba.ejb.BolsaDePuntosDao;
import py.com.progweb.prueba.ejb.ClienteDao;
import py.com.progweb.prueba.model.BolsaDePuntos;
import py.com.progweb.prueba.model.Cliente;

/**
 * Servicios Rest para Conceptos de uso de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Path(value = "bolsa-de-puntos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BolsaDePuntosRest {

    @Inject
    private BolsaDePuntosDao bolsaDePuntosDao;
    @Inject
    private ClienteDao clienteeDao;

    @GET
    @Path("/")
    public Response listar() {
        List<BolsaDePuntos> result = bolsaDePuntosDao.getBolsaPuntoss();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{bolsaDePuntosId}")
    public Response getBolsaDePuntos(@PathParam("bolsaDePuntosId") Integer bolsaDePuntosId) {
        BolsaDePuntos cliente = this.bolsaDePuntosDao.getBolsaPuntos(bolsaDePuntosId);
        if (cliente == null) {
            return Response.noContent().build();
        }
        return Response.ok(cliente).build();
    }

    /*
    @PUT
    @Path("/")
    public Response crear(@QueryParam("clientee") Integer clienteId, BolsaDePuntos bolsaDePuntos) throws URISyntaxException {
        Cliente cliente = this.clienteeDao.getCliente(clienteId);
        if (cliente == null) {
            return Response.status(404).build();
        }
        bolsaDePuntos.setCliente(cliente);
        Integer id = this.bolsaDePuntosDao.addPSac(bolsaDePuntos);
        return Response.created(UriBuilder
                .fromResource(BolsaDePuntosRest.class)
                .path("/{id}")
                .build(id)).build();
    }*/

    @POST
    @Path("/update")
    public Response updateBolsaDePuntos(BolsaDePuntos bolsaDePuntos) {
        this.bolsaDePuntosDao.updateBolsaPuntos(bolsaDePuntos);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{bolsaDePuntosId}")
    public Response deleteBolsaDePuntos(@PathParam("bolsaDePuntosId") Integer bolsaDePuntosId) {
        BolsaDePuntos bolsaDePuntos = this.bolsaDePuntosDao.getBolsaPuntos(bolsaDePuntosId);
        if (bolsaDePuntos == null) {
            return Response.status(404).build();
        }
        this.bolsaDePuntosDao.deleteBolsaPuntos(bolsaDePuntos);
        return Response.ok().build();
    }

}