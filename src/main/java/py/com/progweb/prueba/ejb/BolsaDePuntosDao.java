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
import py.com.progweb.prueba.model.BolsaDePuntos;
import py.com.progweb.prueba.model.ReglaDeAsignacion;
import py.com.progweb.prueba.model.VencimientoDePuntos;

/**
 * Clase que se encarga de bolsa de puntos
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
    private ReglaDeAsignacionDao reglaDeAsignacionDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer addPSac(BolsaDePuntos pSac) {
        entityManager.persist(pSac);
        vencimientoDePuntosDao.createVencimientoDePuntos(
                new VencimientoDePuntos(pSac.getFechaAsignacion(), pSac.getFechaVencimiento(), pSac));
        return pSac.getBolsaId();
    }

    public BolsaDePuntos getBolsaPuntos(Integer id) {
        return entityManager.find(BolsaDePuntos.class, id);
    }

    public List<BolsaDePuntos> getBolsaPuntoss() {
        Query q = this.entityManager.createQuery("select p from BolsaPuntos p");
        return (List<BolsaDePuntos>) q.getResultList();
    }

    public void updateBolsaPuntos(BolsaDePuntos pointsSac) {
        if (entityManager.find(BolsaDePuntos.class, pointsSac.getBolsaId()) != null) {
            entityManager.merge(pointsSac);
        }
    }

    public List<BolsaDePuntos> listWhereClienteOrderByDate(Integer client) {
        Query q = this.entityManager
                .createQuery("select c from BolsaPuntos c where c.cliente.clientId = :client and c.balance ORDER BY c.fechaAsignacion")
                .setParameter("client", client);
        return (List<BolsaDePuntos>) q.getResultList();
    }

    public List<BolsaDePuntos> listByClientAndRange(Integer clientId, Integer infRange, Integer supRange) {
        Query q = this.entityManager
                .createQuery("SELECT s from BolsaPuntos s WHERE s.cliente.cliente_id=:clientId and s.balance BETWEEN :inf and :sup")
                .setParameter("clientId", clientId)
                .setParameter("inf", infRange)
                .setParameter("sup", supRange);
        return (List<BolsaDePuntos>) q.getResultList();
    }

    public List<BolsaDePuntos> listByDaysForExpiration(Long days) {
        Query q = this.entityManager
                .createQuery("SELECT s from BolsaPuntos s WHERE s.vencimientoDePuntos.duracionDias = :days")
                .setParameter("days", days);
        return (List<BolsaDePuntos>) q.getResultList();
    }

    public void deleteBolsaPuntos(BolsaDePuntos pointsSac) {
        entityManager.remove(entityManager.contains(pointsSac) ? pointsSac
                : entityManager.merge(pointsSac));
    }

    public Integer calculatePoints(Integer operationAmount) {
        List<ReglaDeAsignacion> reglaDeAsignacions = reglaDeAsignacionDao.getReglaDeAsignacions();
        Integer points = 0;
        if (reglaDeAsignacions.isEmpty()) {
            //default
            if (operationAmount <= 199999) {
                operationAmount = operationAmount / 50000;
                points = operationAmount;
            } else if (operationAmount <= 499999) {
                operationAmount = operationAmount / 30000;
                points = operationAmount;
            } else {
                operationAmount = operationAmount / 20000;
                points = operationAmount;
            }
        } else {
            for (ReglaDeAsignacion reglaDeAsignacion : reglaDeAsignacions) {
                if (operationAmount > reglaDeAsignacion.getLimiteInferior()) {
                    operationAmount = operationAmount / (Integer)reglaDeAsignacion.getMontoEquivalente().intValue();
                    points = operationAmount;
                    break;
                }
            }
        }
        return points;
    }
}
