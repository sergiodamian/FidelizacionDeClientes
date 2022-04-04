/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.model;

import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author nruiz
 */
@Entity
@Table(name = "regla_de_asignacion")
@Data
public class ReglaDeAsignacion {

    @Id
    @Basic(optional = false)
    @Column(name = "id_asignacion")
    @GeneratedValue(generator = "reglaAsignacionSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reglaAsignacionSec", sequenceName = "regla_asignacion_sec", allocationSize = 0) //debe ser como en la BD
    private Integer idAsignacion;

    @Column(name = "limite_inferior")
    private String limiteInferior;

    @Column(name = "limite_superior")
    private Double limiteSuperior;

    @Column(name = "monto_equivalente")
    private Double montoEquivalente;

    //<editor-fold defaultstate="collapsed" desc="**Get y Set**">
    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(String limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public Double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(Double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public Double getMontoEquivalente() {
        return montoEquivalente;
    }

    public void setMontoEquivalente(Double montoEquivalente) {
        this.montoEquivalente = montoEquivalente;
    }
    //</editor-fold>

    public ReglaDeAsignacion() {
    }

   
}