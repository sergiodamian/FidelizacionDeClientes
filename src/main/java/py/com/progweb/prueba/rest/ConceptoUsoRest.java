/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import javax.ejb.Stateless;
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
        List<ConceptoUsoDao> result = conceptoUsoDao.getConceptoUsoDaos();
        return Response.ok(result).build();
    }

}
