/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad para mapear vencimiento de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Entity
@Table(name = "vencimiento_de_puntos")
@Data
public class VencimientoDePuntos {

    @Id
    @Basic(optional = false)
    @Column(name = "vencimiento_id")
    @GeneratedValue(generator = "vencimientoPuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vencimientoPuntosSec", sequenceName = "punto_venc_sec", allocationSize = 0) //debe ser como en la BD
    private Integer idVencimiento;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin_validez")
    private Date fechaFinValidez;

    @Column(name = "duracion_dias")
    private Long duracionDias;

    public BolsaPuntos getBolsaPuntos() {
        return bolsaPuntos;
    }

    public void setBolsaPuntos(BolsaPuntos bolsaPuntos) {
        this.bolsaPuntos = bolsaPuntos;
    }

    //<editor-fold defaultstate="collapsed" desc="***Get y Set***">
    /**
     * @return the idVencimiento
     */
    public Integer getIdVencimiento() {
        return idVencimiento;
    }

    /**
     * @param idVencimiento the idVencimiento to set
     */
    public void setIdVencimiento(Integer idVencimiento) {
        this.idVencimiento = idVencimiento;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFinValidez
     */
    public Date getFechaFinValidez() {
        return fechaFinValidez;
    }

    public Long getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(Long duracionDias) {
        this.duracionDias = duracionDias;
    }

    //</editor-fold>
    public VencimientoDePuntos() {
    }

    

    
    @OneToOne(optional = false)
    @JoinColumn(name = "bolsa_id")
    @JsonBackReference("vencimiento-bolsa")
    private BolsaPuntos bolsaPuntos;

    public VencimientoDePuntos( Date fechaInicio, Date fechaFinValidez,BolsaPuntos bolsaPuntos) {
        
        this.fechaInicio = fechaInicio;
        this.fechaFinValidez = fechaFinValidez;
        this.bolsaPuntos = bolsaPuntos;
    }
     
    @PrePersist
    void days() {
        Date current = new Date();
        this.duracionDias = (Long) ((fechaFinValidez.getTime() - current.getTime()) / (1000 * 60 * 60 * 24));
    }
}