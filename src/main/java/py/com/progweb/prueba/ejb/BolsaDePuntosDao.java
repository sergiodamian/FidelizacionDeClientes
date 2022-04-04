/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.VencimientoDePuntos;

/**
 * Clase que se encarga de concepto de uso de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Stateless

public class BolsaDePuntosDao {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager entityManager;

    @Inject
    private VencimientoDePuntosDao vencimientoDePuntosDao;

    @Inject
    // private ReglaDeAsignacionDao reglaDeAsignacionDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    /*public Integer addPSac(BolsaPuntos pSac) {
        entityManager.persist(pSac);
        vencimientoDePuntosDao.createVencimientoDePuntos(
                new VencimientoDePuntos(pSac.getPuntosAsignados(), pSac.getFechaVencimiento(), pSac));
        return pSac.getBolsaId();
    }
     */
    public BolsaPuntos getBolsaPuntos(Integer id) {
        return entityManager.find(BolsaPuntos.class, id);
    }

    public List<BolsaPuntos> getBolsaPuntoss() {
        Query q = this.entityManager.createQuery("select p from BolsaPuntos p");
        return (List<BolsaPuntos>) q.getResultList();
    }

    public void updateBolsaPuntos(BolsaPuntos pointsSac) {
        if (entityManager.find(BolsaPuntos.class, pointsSac.getBolsaId()) != null) {
            entityManager.merge(pointsSac);
        }
    }

    public List<BolsaPuntos> listWhereClienteOrderByDate(Long client) {
        Query q = this.entityManager
                .createQuery("select c from BolsaPuntos c where c.client.clientId = :client and c.balance > 0 and c.state = 'no-vencido' ORDER BY c.assignDate")
                .setParameter("client", client);
        return (List<BolsaPuntos>) q.getResultList();
    }

    public List<BolsaPuntos> listByClientAndRange(Long clientId, Long infRange, Long supRange) {
        Query q = this.entityManager
                .createQuery("SELECT s from BolsaPuntos s WHERE s.client.clientId=:clientId and s.balance BETWEEN :inf and :sup")
                .setParameter("clientId", clientId)
                .setParameter("inf", infRange)
                .setParameter("sup", supRange);
        return (List<BolsaPuntos>) q.getResultList();
    }

    public List<BolsaPuntos> listByDaysForExpiration(Long days) {
        Query q = this.entityManager
                .createQuery("SELECT s from BolsaPuntos s WHERE s.vencimientoDePuntos.dayDuration = :days")
                .setParameter("days", days);
        return (List<BolsaPuntos>) q.getResultList();
    }

    public void deleteBolsaPuntos(BolsaPuntos pointsSac) {
        entityManager.remove(pointsSac);
    }

    /* public Long calculatePoints(Double operationAmount) {
        List<ReglaDeAsignacion> reglaDeAsignacions = reglaDeAsignacionDao.getReglaDeAsignacions();
        long points = 0;
        if (reglaDeAsignacions.isEmpty()) {
            //default
            if (operationAmount <= 199999) {
                operationAmount = operationAmount / 50000;
                points = operationAmount.longValue();
            } else if (operationAmount <= 499999) {
                operationAmount = operationAmount / 30000;
                points = operationAmount.longValue();
            } else {
                operationAmount = operationAmount / 20000;
                points = operationAmount.longValue();
            }
        } else {
            for (ReglaDeAsignacion reglaDeAsignacion : reglaDeAsignacions) {
                if (operationAmount > reglaDeAsignacion.getLimInf()) {
                    operationAmount = operationAmount / reglaDeAsignacion.getEqAmount();
                    points = operationAmount.longValue();
                    break;
                }
            }
        }
        return points;
    }*/
}
