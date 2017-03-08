/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "AlquilerItems")
@SessionScoped

public class AlquilerItemsBean implements Serializable {
    Cliente nuevoCliente;
    Cliente selecCliente;
    ServiciosAlquiler sp = ServiciosAlquiler.getInstance();

    public AlquilerItemsBean() {
        nuevoCliente =new Cliente();
        selecCliente =new Cliente();
    }
    
    public List<Cliente> getConsultarClientes() throws ExcepcionServiciosAlquiler {
        return  sp.consultarClientes();
    }

    public Cliente getSelecCliente() {
        return selecCliente;
    }

    public void setSelecCliente(Cliente selecCliente) {
        this.selecCliente = selecCliente;
    }
    public Cliente getNuevoCliente() {
        return nuevoCliente;
    }

    public void setNuevoCliente(Cliente nuevoCliente) {
        this.nuevoCliente = nuevoCliente;
    }
    
    public void registrarNuevoCliente() throws ExcepcionServiciosAlquiler {
        sp.registrarCliente(nuevoCliente);
        nuevoCliente =new Cliente();
    }
    
    
    
    
}
