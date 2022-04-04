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
@Table(name="detalle_uso_de_puntos")
public class DetalleUso {

    public DetalleUso() {
    }
    
    @Id
    @GeneratedValue(generator = "delalleSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "delalleSec", sequenceName = "detalle_uso_de_puntos", allocationSize = 0)
    @Basic(optional = false)
    @Column(name="bolsa_id")
    private Integer detalleId;
    @Column(name="puntaje_utilizado")
    private Integer puntajeUtilizado;
    @ManyToOne(optional=false)
    @JoinColumn(name="bolsaId")
    @JsonBackReference(value="detalle-bolsa")
    private BolsaPuntos bolsaPuntos;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="UsoPuntosId")
    @JsonBackReference(value="detalle-usodepuntos")
    private  UsoPuntos usoPuntos;

    public Integer getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Integer detalleId) {
        this.detalleId = detalleId;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }
}
