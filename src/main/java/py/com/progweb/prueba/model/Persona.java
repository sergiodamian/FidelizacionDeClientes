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

@Entity
@Table(name = "persona") //Agregar como se llama en la BD

/**
 * Entidad que mapea a Persona
 *
 * @author Sergio D. Riveros Vazquez
 */
public class Persona {

    @Id
    @Column(name = "id_persona")
    @Basic(optional = false)
    @GeneratedValue(generator = "personaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "personaSec", sequenceName = "persona_sec", allocationSize = 0) //debe ser como en la BD
    private Integer idPersona;

    @Column(name = "nombre", length = 50)
    @Basic(optional = false)
    private String nombre;

    @Column(name = "apellido", length = 50)
    @Basic(optional = false)
    private String apellido;

    /**
     * Constructor
     */
    public Persona() {
    }
    //<editor-fold defaultstate="collapsed" desc="***Get y Set***">

    public Persona(Integer idPersona, String nombre, String apellido) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    //</editor-fold>

}
