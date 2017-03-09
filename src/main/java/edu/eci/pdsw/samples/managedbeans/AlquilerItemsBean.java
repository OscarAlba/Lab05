/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import static java.time.Instant.now;
import static java.time.LocalDate.now;
import java.time.Month;
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
    private Cliente nuevoCliente;
    private Cliente selecCliente;
    private Item nuevoItem;
    private int dias;
    private long total;
    private long costo;
    ServiciosAlquiler sp = ServiciosAlquiler.getInstance();

    public AlquilerItemsBean() {
        nuevoCliente =new Cliente();
        selecCliente =new Cliente();
        nuevoItem    =new Item();
        dias=0;
        total=0;
        costo=0;
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
    
    public List<ItemRentado> getConsultarItemsCliente() throws ExcepcionServiciosAlquiler {
        return sp.consultarItemsCliente(selecCliente.getDocumento());
    }
    public long getM() throws ExcepcionServiciosAlquiler{
        total=0;
        System.out.print(selecCliente.getNombre());
        List<ItemRentado> lista = getConsultarItemsCliente();
        System.out.print(lista.size());
        for(int i=0;i<lista.size();i++){
            System.out.print(selecCliente.getNombre());
            total+=sp.consultarMultaAlquiler(lista.get(i).getItem().getId(),java.sql.Date.valueOf(LocalDate.now()));
        }
        return total;
    };
    public long getTotal() {
        return total;
    }
    public int getDias() {
        return dias;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setCosto(long costo) {
        this.costo = costo;
    }
    

    public void setDias(int dias) {
        this.dias = dias;
    }
    
    public Item getNuevoItem() {
        return nuevoItem;
    }

    public void setNuevoItem(Item nuevoItem) {
        this.nuevoItem = nuevoItem;
    }


    public long getCosto(){
        
        return costo;
    }
    public long getC() throws ExcepcionServiciosAlquiler{
        costo=0;
        costo= sp.consultarCostoAlquiler(nuevoItem.getId(), dias);
        return costo;
    }
     public void registrarAlquiler() throws ExcepcionServiciosAlquiler{
        nuevoItem=sp.consultarItem(nuevoItem.getId());
        //ItemRentado nuevo=new  ItemRentado(nuevoItem,java.sql.Date.valueOf(LocalDate.now()),java.sql.Date.valueOf(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfYear()+dias)));
        sp.registrarAlquilerCliente(java.sql.Date.valueOf(LocalDate.now()), selecCliente.getDocumento(), nuevoItem, dias);
        
    };
}
