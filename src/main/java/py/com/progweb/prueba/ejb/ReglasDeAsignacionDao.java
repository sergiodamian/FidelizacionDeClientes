/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.ejb;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.ReglaDeAsignacion;
/**
 *
 * @author nruiz
 */
@Stateless
public class ReglasDeAsignacionDao {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    
    public Integer createReglaAsignacion(ReglaDeAsignacion reglaAsignacion){
        if(reglaAsignacion.getMontoEquivalente()<=0){
            return null;
        }
        Query q= this.em.createQuery("select a from ReglaDeAsignacion a");
        List<ReglaDeAsignacion> reglasAsignacion=(List<ReglaDeAsignacion>) q.getResultList();
        int b=1;
        int b2=1;
        ReglaDeAsignacion reglaAsignacion2=null;
        for(ReglaDeAsignacion reglaAsignacion1:reglasAsignacion){
            if((reglaAsignacion.getLimiteInferior())< (reglaAsignacion1.getLimiteInferior())){
                b=0;
                break;
            }
            if((reglaAsignacion1.getLimiteInferior())==0.0){
                b2=2;
                reglaAsignacion2=reglaAsignacion1;
            }
        }
        
        if (b==1 && b2==2){
            reglaAsignacion2.setLimiteSuperior(reglaAsignacion.getLimiteInferior()-1);
            reglaAsignacion.setLimiteSuperior(0.0);
            em.merge(reglaAsignacion2);
            em.persist(reglaAsignacion);
            return reglaAsignacion.getIdAsignacion();
        }else if(b==1){
            reglaAsignacion.setLimiteSuperior(0.0);
            em.persist(reglaAsignacion);
        }else{
            return null;
        }
        
        return reglaAsignacion.getIdAsignacion();
    }
    
    public ReglaDeAsignacion getReglaAsignacion( Integer id){
        return em.find(ReglaDeAsignacion.class, id);
    }
    
    public List<ReglaDeAsignacion> getReglaAsignaciones( ){
        Query q=this.em.createQuery("select p from ReglaDeAsignacion p order by p.limiteInferior DESC");
        return (List<ReglaDeAsignacion>) q.getResultList();
        
    }
    
    public Integer updateReglaAsignacion(ReglaDeAsignacion reglaAsignacion){
        if(reglaAsignacion.getMontoEquivalente()<=0){
            return null;
        }
        
        Query q=this.em.createQuery("select a from ReglaDeAsignacion a where a.idAsignacion=:id")
                .setParameter("id", reglaAsignacion.getIdAsignacion());
        ReglaDeAsignacion reglaAsignacion1=(ReglaDeAsignacion) q.getSingleResult();
        reglaAsignacion1.setMontoEquivalente(reglaAsignacion.getMontoEquivalente());
        em.merge(reglaAsignacion1);
        return 1;
    }
    
    public Integer deleteReglaAsignacion(){
        
        Query q= this.em.createQuery("select a from ReglaDeAsignacion a where a.limiteSuperior=0");
        ReglaDeAsignacion reglaAsignacion1 = (ReglaDeAsignacion) q.getSingleResult();
        Query q1= this.em.createQuery("select a from ReglaDeAsignacion a where a.limiteSuperior=:linf")
                .setParameter("linf", reglaAsignacion1.getLimiteInferior()-1);
        ReglaDeAsignacion reglaAsignacion2=(ReglaDeAsignacion) q1.getSingleResult();
        if(reglaAsignacion1==null && reglaAsignacion2==null){
            return null;
        }else if(reglaAsignacion2==null){
           em.remove(em.contains(reglaAsignacion1) ? reglaAsignacion1 : em.merge(reglaAsignacion1));
        }else{
            reglaAsignacion2.setLimiteSuperior(0.0);
            em.merge(reglaAsignacion2);
            em.remove(em.contains(reglaAsignacion1) ? reglaAsignacion1 : em.merge(reglaAsignacion1));
            
        }
        
        return 1;
    }
    
    
    
}
