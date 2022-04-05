/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

/**
 *
 * @author nruiz
 */
@Entity
@Table(name="cliente")
public class Cliente {

    public Cliente() {
        this.bolsaPuntosList = new ArrayList<BolsaPuntos>();
        
    }
    @Id
    @GeneratedValue(generator = "clienteSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "clienteSec", sequenceName = "cliente_sec", allocationSize = 0)
    @Basic(optional = false)   
    @Column (name="cliente_id")
    private Integer cliente_id;
    @Column (name="nombre")
    private String nombre;
    @Column(name="apellido")
    private String apellido;
    @Column(name="numero_documento")
    private String numeroDocumento;
    @Column (name="tipo_documento")
    private String tipoDocumento;
    @Column (name="nacionalidad")
    private String nacionalidad;
    @Column (name="email")
    private String email;
    @Column (name="telefono")
    private String telefono;
    @Column (name="fecha_de_nacimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "cliente", cascade = {CascadeType.ALL})
    @JsonManagedReference(value="usodepuntos-cliente")
    private Set<UsoPuntos> usoPuntosList=null;    
    
    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "cliente", cascade = {CascadeType.ALL})
    @JsonManagedReference(value="bolsa-cliente")
    private List<BolsaPuntos> bolsaPuntosList;

    public Set<UsoPuntos> getUsoPuntosList() {
        return usoPuntosList;
    }

    public void setUsoPuntosList(Set<UsoPuntos> usoPuntosList) {
        this.usoPuntosList = usoPuntosList;
    }

    public List<BolsaPuntos> getBolsaPuntosList() {
        return bolsaPuntosList;
    }

    public void setBolsaPuntosList(List<BolsaPuntos> bolsaPuntosList) {
        this.bolsaPuntosList = bolsaPuntosList;
    }

    

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
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

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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