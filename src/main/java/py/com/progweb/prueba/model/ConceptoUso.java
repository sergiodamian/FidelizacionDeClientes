/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigInteger;
import java.util.List;
import lombok.Data;
import javax.persistence.*;

/**
 * Entidad para mapear Conceptos de puntos
 *
 * @author Sergio D. Riveros Vazquez
 */
@Entity
@Table(name = "concepto_uso")
@Data
public class ConceptoUso {

    @Id
    @Basic(optional = false)
    @Column(name = "concepto_uso_id")
    @GeneratedValue(generator = "conceptoUsoSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "conceptoUsoSec", sequenceName = "concepto_uso_sec", allocationSize = 0) //debe ser como en la BD
    private Integer idConceptoUso;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "puntos_requeridos")
    private Integer puntosRequeridos;

    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "useConcept", cascade = {CascadeType.ALL})
    @JsonManagedReference(value = "usodepuntos-conceptodeuso")
    private List<UsoPuntos> UsoPuntosList = null; 

    public List<UsoPuntos> getUsoPuntosList() {
        return UsoPuntosList;
    }

    public void setUsoPuntosList(List<UsoPuntos> UsoPuntosList) {
        this.UsoPuntosList = UsoPuntosList;
    }
    //<editor-fold defaultstate="collapsed" desc="***Get y Set***">
    public Integer getIdConceptoUso() {
        return idConceptoUso;
    }

    public void setIdConceptoUso(Integer idConceptoUso) {
        this.idConceptoUso = idConceptoUso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(Integer puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    //</editor-fold>
    public ConceptoUso() {
    }

    public ConceptoUso(Integer idConceptoUso, String descripcion, Integer puntosRequeridos) {
        this.idConceptoUso = idConceptoUso;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
    }

}
