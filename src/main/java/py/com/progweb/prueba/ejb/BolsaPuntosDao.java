/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.ejb;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.*;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.ReglaDeAsignacion;
import py.com.progweb.prueba.model.VencimientoDePuntos;
/**
 *
 * @author nruiz
 */
@Stateless
public class BolsaPuntosDao {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    
    @Inject 
    private VencimientoDePuntosDao vencimientoPuntosDao;
    @Inject 
    private ReglasDeAsignacionDao reglaAsignacionDao;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer agregarBolsaPuntos(BolsaPuntos bolsaPuntos){
        em.persist(bolsaPuntos);
        vencimientoPuntosDao.createVencimientoDePuntos(new VencimientoDePuntos(
         bolsaPuntos.getFechaAsignacion(),bolsaPuntos.getFechaVencimiento(),bolsaPuntos));
        return bolsaPuntos.getBolsaId();
        
    }
    
    public BolsaPuntos getBolsaPunto(Integer id){
        return em.find(BolsaPuntos.class, id);
    }
    
    public List<BolsaPuntos> getBolsaPuntos(){
        Query q=this.em.createQuery("select p from BolsaPuntos p");
        return (List<BolsaPuntos>) q.getResultList();
    }
    
    public void updateBolsaPuntos(BolsaPuntos bolsaPuntos){
           if(em.find(BolsaPuntos.class, bolsaPuntos.getBolsaId())!=null){
               em.merge(bolsaPuntos);
           }
    }
    
    public List<BolsaPuntos> listaOrdenadoPorFecha( Integer cliente){
        Query q= this.em.createQuery(
        "select c from BolsaPuntos c where c.cliente.cliente_id=:client and c.saldo>0 ORDER BY c.fechaAsignacion")
        .setParameter("client",cliente);        
        return (List<BolsaPuntos>) q.getResultList();
    }
    
    public List<BolsaPuntos> listaPorClienteYRango( Integer clienteId,Integer infRange,Integer supRange){
        Query q= this.em.createQuery(
        "select c from BolsaPuntos c where c.cliente.cliente_id=:client and c.saldo>0 BETWEEN :inf and :sup")
        .setParameter("client",clienteId)
        .setParameter("inf", infRange)
        .setParameter("sup", supRange);        
        return (List<BolsaPuntos>) q.getResultList();
    }
    
    public List<BolsaPuntos> listaPorDiasDeExperiacion( Integer dias){
        Query q= this.em.createQuery(
        "select c from BolsaPuntos c where c.vencimientoDePuntos.duracionDias= :dias")
        .setParameter("dias",dias);        
        return (List<BolsaPuntos>) q.getResultList();
    }
    
    public void deleteBolsaPuntos(BolsaPuntos bolsaPunto){
        em.remove(em.contains(bolsaPunto) ? bolsaPunto : em.merge(bolsaPunto));
    }
    
    public Integer calcularPuntos(Integer montoOperacion){
        List<ReglaDeAsignacion> reglasAsignacion=reglaAsignacionDao.getReglaAsignaciones();
        Integer puntos=0;
        if(reglasAsignacion.isEmpty()){
        
            if(montoOperacion<=199999){
                montoOperacion=montoOperacion/50000;
                puntos=montoOperacion;
            }else if (montoOperacion<=499999){
                montoOperacion=montoOperacion/30000;
                puntos=montoOperacion;
            }else{
                montoOperacion=montoOperacion/20000;
                puntos=montoOperacion;
            }
        }else{
            for(ReglaDeAsignacion reglaAsignacion: reglasAsignacion){
               if(montoOperacion>reglaAsignacion.getLimiteInferior()){
                   montoOperacion=montoOperacion/reglaAsignacion.getMontoEquivalente();
                   puntos=montoOperacion;
                   break;
               }
            }
        
        }
        
    return puntos;
    }
}
