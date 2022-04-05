/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import py.com.progweb.prueba.ejb.BolsaPuntosDao;
import py.com.progweb.prueba.ejb.ClienteDao;
import py.com.progweb.prueba.ejb.UsoPuntosDao;
import py.com.progweb.prueba.model.BolsaPuntos;

import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.UsoPuntos;

/**
 * Servicios extras Rest
 *
 * @author Sergio D. Riveros Vazquez
 */
@Path("consultas")
@Consumes("application/json")
@Produces("application/json")
public class ConsultasRest {

    @Inject
    private UsoPuntosDao usoPuntosDao;

    @Inject
    private BolsaPuntosDao bolsaDePuntosDao;

    @Inject
    private ClienteDao clientDao;

    @GET
    @Path("/listarUsosDePuntos")
    public Response listarUsoDePuntos(
            @QueryParam("conceptoDeUso") Integer idConcepto,
            @QueryParam("fechaDeUso") String fechaUso,
            @QueryParam("cliente") Integer idClientee) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = null;
        try {
            if (fechaUso != null) {
                fecha = myFormat.parse(fechaUso);
            }
        } catch (Exception e) {
            return Response.status(400).build();
        }
        List<UsoPuntos> usoPuntosList = usoPuntosDao.ListPorConceptoFechaCliente(idConcepto, (Date) fecha, idClientee);
        return Response.ok(usoPuntosList).build();
    }

    @GET
    @Path("/listarBolsaDePuntos")
    public Response listarBolsaDePuntos(
            @QueryParam("cliente") Integer clientId,
            @QueryParam("puntosRangoInf") Integer rangInf,
            @QueryParam("puntosRangoSup") Integer rangSup) {
        if (rangInf > rangSup || rangInf == null || rangSup == null) {
            return Response.status(400).build();
        }
        
        List<BolsaPuntos> bolsaDePuntosList = bolsaDePuntosDao.listaPorClienteYRango(clientId, rangSup, rangSup);
        return Response.ok(bolsaDePuntosList).build();
    }

    
    @GET
    @Path("/listarClienteesConPuntosAVencer")
    public Response listarClienteesPuntosAVencer(@QueryParam("xDias") Integer xDias) {
        if (xDias < 0) {
            return Response.status(400).build();
        }
        
        List<BolsaPuntos> bolsaDePuntosList = bolsaDePuntosDao.listaPorDiasDeExperiacion(xDias);
        List<Cliente> clients = new ArrayList<Cliente>();
        for (BolsaPuntos bolsaDePuntos : bolsaDePuntosList) {
            Cliente x = bolsaDePuntos.getCliente();
            if (!clients.contains(x)) {
                clients.add(x);
            }
        }
        return Response.ok(clients).build();
    }

    @GET
    @Path("/listarClienteesPorNombreApellidoNacimiento")
    public Response listarClienteesNombreApellidoNacimiento(
            @QueryParam("nombre") String name,
            @QueryParam("apellido") String lastname,
            @QueryParam("nacimiento") String birthday) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = null;
        try {
            if (birthday != null) {
                fecha = myFormat.parse(birthday);
            }
        } catch (ParseException e) {
            return Response.status(400).build();
        }
        List<Cliente> clients = clientDao.listByNameOrLastnameOrBirthday(name, lastname, fecha);
        return Response.ok(clients).build();
    }

    @GET
    @Path("calcularPuntosPorMonto")
    public Response puntosXMonto(@QueryParam("monto") Integer amount) {
       
        return Response.ok(bolsaDePuntosDao.calcularPuntos(amount)).build();
    }

}
