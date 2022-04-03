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

    public Integer createConceptoUso(ConceptoUso conceptoUso) {
        entityManager.persist(conceptoUso);
        return conceptoUso.getIdConceptoUso();
    }

    public ConceptoUso getConceptoUso(Integer idConceptoUso) {
        return entityManager.find(ConceptoUso.class, idConceptoUso);
    }

    public List<ConceptoUso> getConceptoUso() {
        Query q = this.entityManager.createQuery("select p from ConceptoUso p");
        return (List<ConceptoUso>) q.getResultList();
    }

    public void updateConceptoUso(ConceptoUso conceptoUso) {
        if (entityManager.find(ConceptoUso.class, conceptoUso.getIdConceptoUso()) != null) {
            entityManager.merge(conceptoUso);
        }
    }

    public void deleteConceptoUso(ConceptoUso conceptoUso) {
        entityManager.remove(entityManager.contains(conceptoUso) ? conceptoUso:
                entityManager.merge(conceptoUso));
    }
}
