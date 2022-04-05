/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad para mapear reglas de asignacion
 *
 * @author Sergio D. Riveros Vazquez
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
    private Double limiteInferior;

    @Column(name = "limite_superior")
    private Double limiteSuperior;

    @Column(name = "monto_equivalente")
    private Double montoEquivalente;

    //<editor-fold defaultstate="collapsed" desc="***Get y Set***">
    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(Double limiteInferior) {
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

    public ReglaDeAsignacion(Integer idAsignacion, Double limiteInferior, Double limiteSuperior, Double montoEquivalente) {
        this.idAsignacion = idAsignacion;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.montoEquivalente = montoEquivalente;
    }

}
