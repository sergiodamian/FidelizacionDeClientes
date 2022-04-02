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
import py.com.progweb.prueba.model.Persona;

/**
 * Clase que se encarga de la logica de negocio Persona
 *
 * @author Sergio D. Riveros Vazquez
 */
@Stateless
public class PersonaDao {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(Persona entidad) {
        this.em.persist(entidad);
    }

    public List<Persona> lista() {
        Query q = this.em.createQuery("select p from Persona p");
        return (List<Persona>) q.getResultList();
    }
}
