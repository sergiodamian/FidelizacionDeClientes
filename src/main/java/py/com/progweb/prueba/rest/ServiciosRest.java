/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.rest;

import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.progweb.prueba.ejb.BolsaPuntosDao;
import py.com.progweb.prueba.ejb.ClienteDao;
import py.com.progweb.prueba.ejb.ConceptoUsoDao;
import py.com.progweb.prueba.ejb.DetalleUsoDao;
import py.com.progweb.prueba.ejb.UsoPuntosDao;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.ConceptoUso;
import py.com.progweb.prueba.model.DetalleUso;
import py.com.progweb.prueba.model.UsoPuntos;

/**
 * Servicios Rest para servicios solicitados
 *
 * @author Sergio D. Riveros Vazquez
 */
@Path(value = "servicios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiciosRest {

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private BolsaPuntosDao bolsaDePuntosDao;

    @Inject
    private ConceptoUsoDao conceptoUsoDao;

    @Inject
    private UsoPuntosDao usoPuntosDao;

    @Inject
    private DetalleUsoDao detalleUsoDao;
    
    @POST
    @Path("/cargar")
    public Response cargar(@QueryParam("clienteId") Integer clienteId, @QueryParam("montoDeLaOperacion") Integer amount) {
        BolsaPuntos bolsaDePuntos = new BolsaPuntos();
        Cliente cliente = this.clienteDao.getCliente(clienteId);
        if (cliente == null) {
            return Response.status(404).build();
        }
        if (bolsaDePuntosDao.calcularPuntos(amount) <= 0) {
            return Response.ok("Monto de operacion muy pequeno para generar puntos").build();
        }
        bolsaDePuntos.setCliente(this.clienteDao.getCliente(clienteId));
        
        bolsaDePuntos.setPuntosAsignados(bolsaDePuntosDao.calcularPuntos(amount));
        //bolsaDePuntos.setMontoOperacion((BigInteger)(amount.doubleValue()));
        Integer id = bolsaDePuntosDao.agregarBolsaPuntos(bolsaDePuntos);
        return Response.ok(bolsaDePuntosDao.getBolsaPunto(id)).build();
    }


    @POST
    @Path("/canjear")
    public Response canjear(@QueryParam("clienteId") Integer clienteId, @QueryParam("conceptoId") Integer conceptoId, @QueryParam("mail") String mail) {
        ConceptoUso concept = conceptoUsoDao.getConceptoUso(conceptoId);
        Cliente cliente = clienteDao.getCliente(clienteId);
        if (cliente == null || concept == null) {
            return Response.status(404).build();
        }

        Integer requiredPoints = concept.getPuntosRequeridos();
        
        List<BolsaPuntos> bolsaDePuntosList = bolsaDePuntosDao.listaOrdenadoPorFecha(clienteId);
        Integer pointsCounter = 0;
        for (BolsaPuntos bolsaDePuntos : bolsaDePuntosList) {
            
            pointsCounter = +bolsaDePuntos.getSaldo();
            if (pointsCounter >= requiredPoints) {
                break;
            }
        }
        if (pointsCounter < requiredPoints) {
            return Response
                    .notModified("Puntos Del Clientee " + pointsCounter + " \nPuntos Requeridos " + requiredPoints)
                    .build();
        }
        UsoPuntos usoPuntos = new UsoPuntos();
        
        usoPuntos.setPuntosUsados(requiredPoints);
        usoPuntos.setCliente(clienteDao.getCliente(clienteId));
        
        usoPuntos.setUseConcept(conceptoUsoDao.getConceptoUso(conceptoId));
            Integer usoPuntosId = usoPuntosDao.createUsoPunto(usoPuntos);
        for (BolsaPuntos bolsaDePuntos : bolsaDePuntosList) {
            Integer avaiblePoints = bolsaDePuntos.getSaldo();
            if (avaiblePoints <= 0) {
                continue;
            }
            if (avaiblePoints < requiredPoints) {
                requiredPoints = requiredPoints - avaiblePoints;
                DetalleUso detail = new DetalleUso();
                detail.setBolsaPuntos(bolsaDePuntos);
                detail.setPuntajeUtilizado(avaiblePoints);
                detail.setUsoPuntos(usoPuntosDao.getUsoPunto(usoPuntosId));
                detalleUsoDao.CreateDetalleUso(detail);
                bolsaDePuntos.setSaldo(0);
                bolsaDePuntos.setPuntosUsados(bolsaDePuntos.getPuntosAsignados());
                bolsaDePuntosDao.updateBolsaPuntos(bolsaDePuntos);
            } else {
                DetalleUso detail = new DetalleUso();
                detail.setBolsaPuntos(bolsaDePuntos);
                detail.setPuntajeUtilizado(requiredPoints);
                detail.setUsoPuntos(usoPuntosDao.getUsoPunto(usoPuntosId));
                detalleUsoDao.CreateDetalleUso(detail);
                bolsaDePuntos.setSaldo(avaiblePoints - requiredPoints);
                bolsaDePuntos.setPuntosUsados(bolsaDePuntos.getPuntosUsados()+ requiredPoints);
                bolsaDePuntosDao.updateBolsaPuntos(bolsaDePuntos);
                break;
            }
        }
        /*if (mail != null) {
            try {
                this.sendMail(cliente.getEmail(), usoPuntos.getConceptoUso().getDescription(), requiredPoints);
            } catch (MessagingException e) {
                System.out.println("Email invalido " + cliente.getEmail());
                return Response.status(400).build();
            }
        }*/
        return Response.ok(usoPuntosDao.getUsoPunto(usoPuntosId)).build();
    }

   /* private void sendMail(String receipient, String concept, Long points) throws MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session newSession = Session.getDefaultInstance(properties, null);

        String emailReceipient = receipient;
        String emailSubject = "Canjeo de Puntos";
        String emailBody = "Has usado " + points + " puntos para canjear \n"
                + concept + ".";

        MimeMessage mimeMessage = new MimeMessage(newSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));
        mimeMessage.setSubject(emailSubject);
        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody, "text/html; charset=UTF-8");
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);;

        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect("giul1297.g@gmail.com", "ccp191231");
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }*/
}
