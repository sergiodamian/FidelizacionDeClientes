/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.progweb.prueba.ejb.VencimientoDePuntosDao;
import py.com.progweb.prueba.model.VencimientoDePuntos;

/**
 * Servicios Rest para vencimiento de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Path(value = "vencimiento-de-puntos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VencimientoDePuntosRest {

    @Inject
    private VencimientoDePuntosDao vencimientoDePuntosDao;

    /*@Inject
    private PointsSacDao pointsSacDao;*/
    @GET
    @Path("/")
    public Response listar() {
        List<VencimientoDePuntos> result = vencimientoDePuntosDao.getVencimientoDePuntos();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{assignId}")
    public Response getVencimientoDePuntos(@PathParam("assignId") Integer assignId) {
        VencimientoDePuntos vencimientoDePuntos1 = this.vencimientoDePuntosDao.getVencimientoDePuntos(assignId);
        if (vencimientoDePuntos1 == null) {
            return Response.noContent().build();
        }
        return Response.ok(vencimientoDePuntos1).build();
    }

    /*
    
    DESCOMENTAR CUANDO LA BOLSA DE PUNTOS YA ESTE AGREGADO
    
    @PUT
    @Path("/")
    public Response crear(@QueryParam("bolsaId") Integer id, VencimientoDePuntos vencimientoDePuntos) throws URISyntaxException {
        BolsaDePuntos pointsSac = this.BolsaDePuntosDao.getPointsSac(id);
        if (pointsSac == null || vencimientoDePuntos.getFechaFinValidez() == null) {
            return Response.status(400).build();
        }
        vencimientoDePuntos.setPointsSac(pointsSac);
        Integer pid = vencimientoDePuntosDao.createVencimientoDePuntos(vencimientoDePuntos);
        return Response.created(UriBuilder
                .fromResource(BolsaDePuntosRest.class)
                .path("/{id}")
                .build(pid)).build();
    }
     
    @POST
    @Path("/update")
    public Response update(VencimientoDePuntos vencimientoDePuntos) {
        this.vencimientoDePuntosDao.updateVencimientoDePuntos(vencimientoDePuntos);
        return Response.ok().build();
    }
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            VencimientoDePuntos vp = this.vencimientoDePuntosDao.getVencimientoDePuntos(id);
            if (vp == null) {
                return Response.status(400).build();
            }
            this.vencimientoDePuntosDao.deleteVencimientoDePuntos(vp);
            return Response.ok().build();
        } catch (Exception e) {
            System.err.println(e);
        }
        return Response.status(500).build();
    }

}
