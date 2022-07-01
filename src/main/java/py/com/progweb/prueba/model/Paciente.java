/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author nruiz
 */

@Entity
@Data
@Table(name="paciente")
public class Paciente {
    @Id
    @Column(name="id")
    @GeneratedValue(generator="pacienteSec",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name="pacienteSec",sequenceName="seq_paciente")
    private Integer id;
    
    @Column(name="nombre")
    private String nombre;
    
    @Column (name="apellido")
    private String apellido;
    
    @Column (name="cedula")
    private String cedula;
    
    @Column (name="email")
    private String email;
    
    @Column(name="telefono")
    private String telefono;
    
    @Column(name="fechaNacimient")
    private Date fechaNacimiento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
}
