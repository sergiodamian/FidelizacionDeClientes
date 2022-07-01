/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.ejb.Stateless;
import javax.persistence.*;
import py.com.progweb.prueba.model.Paciente;

/**
 *
 * @author nruiz
 */
@Stateless
public class PacienteDao {
    @PersistenceContext(unitName="pruebaPU")
    
    private EntityManager em;
    
    public void CrearPaciente(Paciente paciente){
        em.persist(paciente);
    }
}
