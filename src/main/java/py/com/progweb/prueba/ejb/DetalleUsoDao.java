/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.prueba.model.DetalleUso;

/**
 *
 * @author nruiz
 */
@Stateless
public class DetalleUsoDao {
    
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    
    public void CreateDetalleUso(DetalleUso detalleUso ){
        em.persist(detalleUso);    
    }
    
    public DetalleUso getDetalleUso(Integer id){
        return em.find(DetalleUso.class, id);
    }
    
    public List<DetalleUso> getDetallesUso(){
        Query q=this.em.createQuery("select p from DetalleUso p");
        return (List<DetalleUso>) q.getResultList();
    }
    
    
}
