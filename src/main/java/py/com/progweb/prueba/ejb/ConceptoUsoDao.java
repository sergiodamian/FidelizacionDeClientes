/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import py.com.progweb.prueba.model.ConceptoUso;

/**
 * Clase que se encarga de concepto de uso de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Stateless
public class ConceptoUsoDao {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager entityManager;

    public Integer createConceptoUsoDao(ConceptoUso conceptoUso) {
        entityManager.persist(conceptoUso);
        return conceptoUso.getIdConceptoUso();
    }

    public ConceptoUsoDao getConceptoUsoDao(Integer id) {
        return entityManager.find(ConceptoUsoDao.class, id);
    }

    public List<ConceptoUsoDao> getConceptoUsoDaos() {
        Query q = this.entityManager.createQuery("select p from ConceptoUso p");
        return (List<ConceptoUsoDao>) q.getResultList();
    }

    public void updateConceptoUsoDao(ConceptoUso conceptoUso) {
        if (entityManager.find(ConceptoUsoDao.class, conceptoUso.getIdConceptoUso()) != null) {
            entityManager.merge(conceptoUso);
        }
    }

    public void deleteConceptoUsoDao(ConceptoUsoDao conceptoUso) {
        entityManager.remove(conceptoUso);
    }
}
