/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.core.MediaType;
import py.com.progweb.prueba.ejb.ConceptoUsoDao;
import py.com.progweb.prueba.model.ConceptoUso;

/**
 * Servicios Rest para Conceptos de uso de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Path(value = "concepto-de-uso")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ConceptoUsoRest {

    @Inject
    private ConceptoUsoDao conceptoUsoDao;

    @GET
    @Path("/")
    public Response listar() {
        List<ConceptoUso> result = conceptoUsoDao.getConceptoUso();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{idConceptoUso}")
    public Response listarPorId(@PathParam("idConceptoUso") Integer idConceptoUso) {
        ConceptoUso value = this.conceptoUsoDao.getConceptoUso(idConceptoUso);
        if (value == null) {
            return Response.noContent().build();
        }
        return Response.ok(value).build();
    }

    @POST
    @Path("/")
    public Response crear(ConceptoUso conceptoUso) throws URISyntaxException {
        Integer id = this.conceptoUsoDao.createConceptoUso(conceptoUso);
        return Response.created(UriBuilder
                .fromResource(ConceptoUsoRest.class)
                .path("/{id}")
                .build(id)).build();
    }

    @PUT
    @Path("/")
    public Response actualizar(ConceptoUso conceptoUso) {
        this.conceptoUsoDao.updateConceptoUso(conceptoUso);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{idConceptoUso}")
    public Response eliminar(@PathParam("idConceptoUso") Integer conceptoUsoId) {
        try {
            ConceptoUso conceptoUso = this.conceptoUsoDao.getConceptoUso(conceptoUsoId);
            if (conceptoUso == null) {
                return Response.status(400).build();
            }
            this.conceptoUsoDao.deleteConceptoUso(conceptoUso);
            return Response.ok().build();
        } catch (Exception e) {
            System.err.print(e);
        }
        return Response.status(500).build();
    }

}
