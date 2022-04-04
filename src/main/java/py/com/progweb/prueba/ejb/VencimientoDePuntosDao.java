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
import py.com.progweb.prueba.model.VencimientoDePuntos;

/**
 * Clase que se encarga de concepto de uso de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Stateless

public class VencimientoDePuntosDao {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager entityManager;

    public Integer createVencimientoDePuntos(VencimientoDePuntos vencimientoDePuntos) {
        entityManager.persist(vencimientoDePuntos);
        return vencimientoDePuntos.getIdVencimiento();
    }

    public VencimientoDePuntos getVencimientoDePuntos(Integer id) {
        return entityManager.find(VencimientoDePuntos.class, id);
    }

    public List<VencimientoDePuntos> getVencimientoDePuntos() {
        Query q = this.entityManager.createQuery("select p from VencimientoDePuntos p");
        return (List<VencimientoDePuntos>) q.getResultList();
    }

    
    public void updateVencimientoDePuntos(VencimientoDePuntos vencimientoDePuntos) {
        if (entityManager.find(VencimientoDePuntos.class, vencimientoDePuntos.getIdVencimiento()) != null) {
            VencimientoDePuntos vencimientoDePuntos1 = this.getVencimientoDePuntos(vencimientoDePuntos.getIdVencimiento());
            vencimientoDePuntos.setBolsaPuntos(vencimientoDePuntos1.getBolsaPuntos());
            entityManager.merge(vencimientoDePuntos);
        }
    }
     
    public void deleteVencimientoDePuntos(VencimientoDePuntos vencimientoDePuntos) {
        entityManager.remove(entityManager.contains(vencimientoDePuntos) ? vencimientoDePuntos
                : entityManager.merge(vencimientoDePuntos));
    }
}
