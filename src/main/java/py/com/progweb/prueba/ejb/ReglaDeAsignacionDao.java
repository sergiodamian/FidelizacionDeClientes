/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.prueba.model.ReglaDeAsignacion;

/**
 * Clase que se encarga de regla de asignacion
 *
 * @author Sergio D. Riveros Vazquez
 */
@Stateless
public class ReglaDeAsignacionDao {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager entityManager;

    public Integer createReglaDeAsignacion(ReglaDeAsignacion reglaDeAsignacion) {
        if (reglaDeAsignacion.getMontoEquivalente() <= 0) {
            return null;
        }

        Query q = this.entityManager.createQuery("select a from ReglaDeAsignacion a");
        List<ReglaDeAsignacion> reglaDeAsignacions = (List<ReglaDeAsignacion>) q.getResultList();
        int b = 1;
        int b2 = 1;
        ReglaDeAsignacion reglaDeAsignacion2 = null;
        for (ReglaDeAsignacion reglaDeAsignacion1 : reglaDeAsignacions) {
            if (reglaDeAsignacion.getLimiteInferior() < reglaDeAsignacion1.getLimiteInferior()) {
                b = 0;
                break;
            }
            if (reglaDeAsignacion1.getLimiteSuperior() == 0.0) {
                b2 = 2;
                reglaDeAsignacion2 = reglaDeAsignacion1;
            }
        }
        if (b == 1 && b2 == 2) {
            reglaDeAsignacion2.setLimiteSuperior(reglaDeAsignacion.getLimiteInferior() - 1);
            reglaDeAsignacion.setLimiteSuperior(0.0);
            entityManager.merge(reglaDeAsignacion2);
            entityManager.persist(reglaDeAsignacion);
            return reglaDeAsignacion.getIdAsignacion();
        } else if (b == 1) {
            reglaDeAsignacion.setLimiteSuperior(0.0);
            entityManager.persist(reglaDeAsignacion);
        } else {
            return null;
        }
        return reglaDeAsignacion.getIdAsignacion();
    }

    public ReglaDeAsignacion getReglaDeAsignacion(Integer id) {
        return entityManager.find(ReglaDeAsignacion.class, id);
    }

    public List<ReglaDeAsignacion> getReglaDeAsignacions() {
        Query q = this.entityManager.createQuery("select p from ReglaDeAsignacion p order by p.limiteInferior DESC");
        return (List<ReglaDeAsignacion>) q.getResultList();
    }

    public Double updateReglaDeAsignacion(ReglaDeAsignacion reglaDeAsignacion) {
        if (reglaDeAsignacion.getMontoEquivalente() <= 0) {
            return null;
        }
        Query q = entityManager.createQuery("select a from ReglaDeAsignacion a where a.idAsignacion=:id")
                .setParameter("id", reglaDeAsignacion.getIdAsignacion());
        ReglaDeAsignacion reglaDeAsignacion1 = (ReglaDeAsignacion) q.getSingleResult();
        reglaDeAsignacion1.setMontoEquivalente(reglaDeAsignacion.getMontoEquivalente());
        entityManager.merge(reglaDeAsignacion1);
        return 1D;
    }

    public Double deleteReglaDeAsignacion() {
        Query q = entityManager.createQuery("select a from ReglaDeAsignacion a where a.limiteSuperior=0");
        ReglaDeAsignacion reglaDeAsignacion1 = (ReglaDeAsignacion) q.getSingleResult();
        Query q1 = entityManager.createQuery("select a from ReglaDeAsignacion a where a.limiteSuperior=:limiteInferior")
                .setParameter("limiteInferior", reglaDeAsignacion1.getLimiteInferior() - 1);
        ReglaDeAsignacion reglaDeAsignacion2 = (ReglaDeAsignacion) q1.getSingleResult();
        if (reglaDeAsignacion1 == null && reglaDeAsignacion2 == null) {
            return null;
        } else if (reglaDeAsignacion2 == null) {
            entityManager.remove(reglaDeAsignacion1);
        } else {
            reglaDeAsignacion2.setLimiteSuperior(0.0);
            entityManager.merge(reglaDeAsignacion2);
            entityManager.remove(reglaDeAsignacion1);
        }
        return 1D;
    }
}
