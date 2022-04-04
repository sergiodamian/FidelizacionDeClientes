/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.com.progweb.prueba.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import py.com.progweb.prueba.model.Cliente;

/**
 *
 * @author nruiz
 */
@Stateless
public class ClienteDao {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void crearCliente(Cliente cliente) {
        em.persist(cliente);
        // return cliente.getCliente_id();  
    }

    public Cliente getCliente(Integer id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> getClientes() {
        Query q = this.em.createQuery("select cliente from Cliente cliente");
        List<Cliente> clientes = (List<Cliente>) q.getResultList();
        return clientes;
    }

    public void UpdateCliente(Cliente cliente) {
        if (em.find(Cliente.class, cliente.getCliente_id()) != null) {
            em.merge(cliente);
        }
    }
    // @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void DeleteCliente(Cliente cliente) {
        em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
    }

    public List<Cliente> listByNameOrLastnameOrBirthday(String nombre, String apellido, Date fechaNacimiento) {
        Query q;
        if (fechaNacimiento == null) {
            q = this.em.createQuery(
                    "select c from Cliente c where c.nombre LIKE :nombre or c.apellido = :apellido")
                    .setParameter("nombre", nombre + "%")
                    .setParameter("apellido", apellido + "%");
        } else {
            q = this.em.createQuery(
                    "select c from Cliente c where c.nombre LIKE :nombre or c.apellido = :apellido or c.fechaNacimiento = :fechaNacimiento")
                    .setParameter("nombre", nombre + "%")
                    .setParameter("apellido", apellido + "%")
                    .setParameter("birthday", fechaNacimiento, TemporalType.DATE);
        }
        return (List<Cliente>) q.getResultList();
    }

}
