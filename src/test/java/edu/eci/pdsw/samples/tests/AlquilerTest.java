/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 * 
 * Calculo Multa:
 * Frontera:
 * CF1: Multas a devoluciones hechas en la fecha exacta (multa 0).
 * 
 * Clases de equivalencia:
 * CE1: Multas hechas a devolciones realizadas en fechas posteriores
 * a la limite. (multa multa_diaria*dias_retraso)
 */
public class AlquilerTest {

    public AlquilerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CF1Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i1=new Item(sa.consultarTipoItem(1), 44, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",3842,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(44);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3842, item, 5);
        
        assertEquals("No se calcula correctamente la multa (0) "
                + "cuando la devolucion se realiza el dia limite."
                ,0,sa.consultarMultaAlquiler(44, java.sql.Date.valueOf("2005-12-25")));
                
    }
    

    @Test
    public void CE1Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i1=new Item(sa.consultarTipoItem(1), 55, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",9843,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(55);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9843, item, 5);
        //prueba: 3 dias de retraso
        assertEquals("No se calcula correctamente la multa "
                + "cuando la devolucion se realiza varios dias despues del limite."
                ,sa.valorMultaRetrasoxDia()*3,sa.consultarMultaAlquiler(55, java.sql.Date.valueOf("2005-12-28")));
                
    }
    /**
     * Registar cliente Multa:
     * Clases de equivalencia:  Multas hechas a devolciones realizadas en fechas anteriores
     * a la limite. (0)
     */
    @Test
    public void CE2Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i2=new Item(sa.consultarTipoItem(2), 2, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        sa.registrarCliente(new Cliente("Juan Perez",1234,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i2);
                
        Item item=sa.consultarItem(2);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9843, item, 5);
        //prueba: 2 dias antes de la entrega
        assertEquals("No se calcula correctamente la multa"
                + "se entrega el dvd 2 dias antes deberia ser 0 no un numero negativo."
                ,0,sa.consultarMultaAlquiler(2, java.sql.Date.valueOf("2005-12-23")));
                
    }
    
    /**
     * Consultar costo de alquiler de un Item:
     * Frontera: CF2: El costo un item con (0) unidades debe ser 0.
     * 
     * Clases de equivalencia: 
     * CE4: Costo de items menores a 0 unidades (0)
     * 
     * Clases de equivalencia: 
     * CE3: Costo de items mayores a 0 unidades (costo*unidades)
     */
    @Test
    public void CF2Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i2=new Item(sa.consultarTipoItem(2), 2, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        sa.registrarCliente(new Cliente("Juan Perez",1225,"24235","calle 123","aa@gmail.com"));
        sa.registrarItem(i2);
                
        Item item=sa.consultarItem(2);
        //prueba: el costo de 0 unidades
        assertEquals("0 unidades de un item debe ser 0 en el costo",0,sa.consultarCostoAlquiler(2,0));
                
    }
    
    /**
     * Consultar costo de alquiler de un Item:
     * Clases de equivalencia: 
     * CE4: Costo de items menores a 0 unidades (0)
     */
    @Test
    public void CE4Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i2=new Item(sa.consultarTipoItem(2), 3, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        sa.registrarCliente(new Cliente("Juan Perez",1236,"24235","calle 123","aa@gmail.com"));
        sa.registrarItem(i2);
                
        Item item=sa.consultarItem(3);
        //prueba: el costo de 0 unidades
        assertEquals("El costo se calcula incorrectamente "+
                "-1 unidades de un item debe ser 0 en el costo o un valor incorrecto"
                ,0,sa.consultarCostoAlquiler(3,-1));
                
    }
    
    /**
     * Clases de equivalencia: 
     * CE4: Costo de items mayores a 0 unidades (costo*unidades)
     */
    @Test
    public void CE3Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i2=new Item(sa.consultarTipoItem(2), 4, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        sa.registrarCliente(new Cliente("Juan Perez",1238,"24235","calle 123","aa@gmail.com"));
        sa.registrarItem(i2);
                
        Item item=sa.consultarItem(4);
        //prueba: el costo de 3 unidades
        assertEquals("El costo se calcula incorrectamente "+
                "unidades de un item debe ser 0 en el costo o un valor incorrecto"
                ,3000*3,sa.consultarCostoAlquiler(4,3));
                
    }
    
    
    /**
     * Registrar cliente:
     * 
     * Clases de equivalencia: 
     * CE5: El cliente registrado es valido
     * 
     * Clases de equivalencia: 
     * CE6: El cliente registrado ya esta registrado 
     */
    
    @Test
    public void CE5Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        
        Item i2=new Item(sa.consultarTipoItem(2), 6, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        Cliente c=new Cliente("Juan Perez",0000,"24235","calle 123","aa@gmail.com");
        sa.registrarCliente(c);
        sa.registrarItem(i2);
                
        Item item=sa.consultarItem(6);
        //prueba: debe registrar el cliente correctamente
        assertEquals("El cliente no se registro correctamente",sa.consultarCliente(0000),c);
                
    }
    @Test
    public void CE6Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        ExcepcionServiciosAlquiler ex=null;
        Item i2=new Item(sa.consultarTipoItem(2), 11, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        Cliente c=new Cliente("Juan Perez",0000,"24235","calle 123","aa@gmail.com");
        sa.registrarItem(i2);
        try{
        sa.registrarCliente(c);
        Item item=sa.consultarItem(11);
        //prueba: Debe arrojar una excepcion
        }catch(ExcepcionServiciosAlquiler e){
            ex=e;
        }finally{
            assertEquals("El cliente no se registro correctamente",true,ex!=null);
        }
        
                
    }
    
    /**
     * Registrar Alquiler a un cliente:
     * 
     * Clases de equivalencia: 
     * CE7: El alquiler del item no es valido 
     */
    
    @Test
    public void CE7Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        ExcepcionServiciosAlquiler ex=null;
        Item i2=new Item(sa.consultarTipoItem(2), 0, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        //prueba: 2 dias antes de la entrega
        try{
            
            Item item = sa.consultarItem(0);
            sa.registrarItem(i2);
            sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3666, item, -5);
            
        }catch(ExcepcionServiciosAlquiler e){
            ex=e;
        }finally{
            assertEquals("El cliente no se registro correctamente",true,ex!=null);
        }
        
    }
    
    
    
}
