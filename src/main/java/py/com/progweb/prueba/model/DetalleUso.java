/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author nruiz
 */
@Entity
@Data
@Table(name = "detalle_uso_de_puntos")
public class DetalleUso {

    public DetalleUso() {
    }

    @Id
    @GeneratedValue(generator = "delalleUsoSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "delalleUsoSec", sequenceName = "detalle_uso_de_puntos_sec", allocationSize = 0)
    @Basic(optional = false)
    @Column(name = "detalle-uso-id")
    private Integer detalleId;

    @Column(name = "id-uso-de-puntos")
    private Integer usoDePuntosId;

    @Column(name = "puntaje-utilizado")
    private Integer puntosUsados;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uso_puntos_id")
    @JsonBackReference(value = "detalle-usodepuntos")
    private UsoPuntos puntosUsadosObject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bolsa_id")
    @JsonBackReference(value = "detalle-bolsa")
    private BolsaDePuntos bolsaDePuntos;

    public Integer getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Integer detalleId) {
        this.detalleId = detalleId;
    }

    public Integer getUsoDePuntosId() {
        return usoDePuntosId;
    }

    public void setUsoDePuntosId(Integer usoDePuntosId) {
        this.usoDePuntosId = usoDePuntosId;
    }

    public Integer getPuntosUsados() {
        return puntosUsados;
    }

    public void setPuntosUsados(Integer puntosUsados) {
        this.puntosUsados = puntosUsados;
    }

    public UsoPuntos getPuntosUsadosObject() {
        return puntosUsadosObject;
    }

    public void setPuntosUsadosObject(UsoPuntos puntosUsadosObject) {
        this.puntosUsadosObject = puntosUsadosObject;
    }

    public BolsaDePuntos getBolsaDePuntos() {
        return bolsaDePuntos;
    }

    public void setBolsaDePuntos(BolsaDePuntos bolsaDePuntos) {
        this.bolsaDePuntos = bolsaDePuntos;
    }

}
