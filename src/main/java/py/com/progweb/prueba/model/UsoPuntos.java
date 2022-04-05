package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author nruiz
 */
@Entity
@Data
@Table(name="uso_de_puntos")
public class UsoPuntos {

    public UsoPuntos() {
    }
    @Id
    @GeneratedValue(generator = "delalleSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "delalleSec", sequenceName = "detalle_uso_de_puntos", allocationSize = 0)
    @Basic(optional = false)
    @Column(name="uso_puntos_id")
    private Integer UsoPuntosId;
    @Column(name="puntos_usados")
    private Integer PuntosUsados;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name="fecha_uso")
    private Date FechaUso;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="id_cliente")
    @JsonBackReference(value="usodepuntos-cliente")
    private Cliente cliente;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="concepto_uso_id")
    @JsonBackReference(value="usodepuntos-conceptodeuso")
    private ConceptoUso useConcept;
    
    @OneToMany(fetch = FetchType.EAGER ,mappedBy ="usoPuntos", cascade =  CascadeType.ALL)
    @JsonBackReference(value="detalle-usodepuntos")
    private List<DetalleUso> detalleUso=null;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ConceptoUso getUseConcept() {
        return useConcept;
    }

    public void setUseConcept(ConceptoUso useConcept) {
        this.useConcept = useConcept;
    }

    public List<DetalleUso> getDetalleUso() {
        return detalleUso;
    }

    public void setDetalleUso(List<DetalleUso> detalleUso) {
        this.detalleUso = detalleUso;
    }

    public Integer getUsoPuntosId() {
        return UsoPuntosId;
    }

    public void setUsoPuntosId(Integer UsoPuntosId) {
        this.UsoPuntosId = UsoPuntosId;
    }

    public Integer getPuntosUsados() {
        return PuntosUsados;
    }

    public void setPuntosUsados(Integer PuntosUsados) {
        this.PuntosUsados = PuntosUsados;
    }

    public Date getFechaUso() {
        return FechaUso;
    }

    public void setFechaUso(Date FechaUso) {
        this.FechaUso = FechaUso;
    }
    
    @PrePersist
    void Dates(){
        this.FechaUso=new Date();
    }
    
}