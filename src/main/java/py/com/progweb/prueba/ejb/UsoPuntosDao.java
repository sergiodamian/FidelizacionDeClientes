/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import py.com.progweb.prueba.model.UsoPuntos;

/**
 *
 * @author nruiz
 */
@Stateless
public class UsoPuntosDao {
     @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer createUsoPunto(UsoPuntos usoPuntos){
        em.persist(usoPuntos);
        return usoPuntos.getUsoPuntosId();
    }
    
    public UsoPuntos getUsoPunto(Integer id){
        return em.find(UsoPuntos.class, id);
    }
    
    public List <UsoPuntos> getUsoPuntos(){
        Query q=this.em.createQuery("select p from UsoPuntos p");
        return (List<UsoPuntos>) q.getResultList();
    }
    
    public void UpdateUsoPunto(UsoPuntos usoPuntos){
         if (em.find(UsoPuntos.class,usoPuntos.getUsoPuntosId()) != null){
            em.merge(usoPuntos);
        }
    }
    
    public List<UsoPuntos> ListPorConceptoFechaCliente(Integer idConcepto, Date fecha, Integer idCliente){
        Query q;
        if(fecha==null){
            q=this.em.createQuery(
                    "select c from UsoPuntos c where c.useConcept.idConceptoUso=:idConcepto or c.cliente.cliente_id")
                    .setParameter("idConcepto",idConcepto)
                    .setParameter("cliente_id", idCliente);
            
        }else{
            q=this.em.createQuery(
            "select c form UsoPuntos c where c.useConcept.idConceptoUso= :idConcepto or c.FechaUso or  c.cliente.cliente_id "
            ).setParameter("idConcepto",idConcepto)
            .setParameter("FechaUso", fecha,TemporalType.DATE)
            .setParameter("cliente_id", idCliente);
        }
        return (List<UsoPuntos>) q.getResultList();
    }
    public void DeleteUsoPuntos(UsoPuntos usoPuntos){
         em.remove(em.contains(usoPuntos) ? usoPuntos : em.merge(usoPuntos));
    }
}
