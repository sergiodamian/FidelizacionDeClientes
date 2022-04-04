/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author nruiz
 */
@Entity
@Data
@Table(name="bolsa_puntos")
public class BolsaPuntos {

    public BolsaPuntos() {
    }
    
    @Id
    @GeneratedValue(generator = "bolsaPuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bolsaPuntosSec", sequenceName = "bolsa_sec", allocationSize = 0)
    @Basic(optional = false)
    @Column(name="bolsa_id")
    private Integer bolsaId;
    @Column(name="fecha_asignacion")
    private Date fechaAsignacion;
    @Column(name="fecha_vencimiento")
    private Date fechaVencimiento;
    @Column(name="puntos_asignados")
    private Integer puntosAsignados;
    @Column(name="puntos_usados")
    private Integer puntosUsados;
    @Column(name="saldo")
    private Integer saldo;
    @Column(name="montoOperacion")
    private BigInteger montoOperacion;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="cliente_id")
    @JsonBackReference(value="bolsa-cliente")
    private Cliente cliente;

    public Integer getBolsaId() {
        return bolsaId;
    }

    public void setBolsaId(Integer bolsaId) {
        this.bolsaId = bolsaId;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getPuntosAsignados() {
        return puntosAsignados;
    }

    public void setPuntosAsignados(Integer puntosAsignados) {
        this.puntosAsignados = puntosAsignados;
    }

    public Integer getPuntosUsados() {
        return puntosUsados;
    }

    public void setPuntosUsados(Integer puntosUsados) {
        this.puntosUsados = puntosUsados;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public BigInteger getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(BigInteger montoOperacion) {
        this.montoOperacion = montoOperacion;
    }
    
    
}
