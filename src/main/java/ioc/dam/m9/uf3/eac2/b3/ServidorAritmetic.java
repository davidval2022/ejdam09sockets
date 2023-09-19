package main.java.ioc.dam.m9.uf3.eac2.b3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class ServidorAritmetic {

    protected ServeiAritmetic servei;
    protected Socket socket;

    public void setServei(ServeiAritmetic servei) {
        this.servei = servei;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void executa() {       
        //IMPLEMENTA       
        try {
            //Llegeix el missatge del client i l'analitza
            BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client rebut");
            String orden = lector.readLine();
            System.out.println("Me piden que haga: " + orden);
            
            //lo analizo y guardo el resultado
            Double resultado = analitza(orden);
            //Double resultado = 11.00; //pruebas 
            
            //Escriu el resultat cap al client
            BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String envio=String.valueOf(resultado);//convertimos el double en string
            escriptor.write(envio);
            escriptor.newLine();
            escriptor.flush();            

            //Tanca el flux
            lector.close();
            escriptor.close();
            
        } catch (IOException ex) {            
            System.out.println("Error en el metodo executa: "+ex);
        }            

     

            
    }

    //El protocol per a la operació és operador:valor1:valor2
    private double analitza(String operacio)throws IllegalArgumentException {        
        //IMPLEMENTA
        String resultat[]=operacio.split(":");
        String tipo_op = resultat[0];//lo dejamos como string ya que es el operador
        double operando1 = Double.valueOf(resultat[1]);//convertimos a double para la operacion       
        double operando2 = Double.valueOf(resultat[2]);//convertimos a double para la operacion
        double resultado_final = 0;
        //System.out.println(tipo_op); //pruebas
        //System.out.println(operando1);
        //System.out.println(operando2);
        
        //según la operación ofrecemos el tipo de operación correcto
        switch (tipo_op){
            case  "+":
                resultado_final = servei.suma(operando1, operando2);
                break;
            case "-":
                resultado_final = servei.resta(operando1, operando2);
                break;
            case "*":
                resultado_final = servei.mult(operando1, operando2);
                break;
            case "/":
                resultado_final = servei.div(operando1, operando2);
                break;
            default:
                resultado_final = 0;//por defecto devolvemos un 0;
        }

        return resultado_final;
        
    }

    
    public static void main(String[] args) throws Exception {
        
        //IMPLEMENTA      
        // Crea el socket servidor i espera la connexió del client
        ServidorAritmetic server = new ServidorAritmetic();//creamos un instancia del programa
        ServeiAritmeticImpl calculadora = new ServeiAritmeticImpl();//Tambien necesitamos una instancia de ServeiAritmeticImpl,
        //de nuestro servicio
        
        
        ServerSocket serverSocket= new ServerSocket(9999); //creamos el socket del server  
        System.out.println("El servidor matemàtic està executant...");
        System.out.println("Esperant clients...");
        Socket socket = serverSocket.accept();//creamos el socket del cliente
        
        server.setSocket(socket);//inicializamos el valor del socket de nuestra instancia
        server.setServei(calculadora);// lo mismo con la instancia del servicio
        
        
        server.executa();//ejecutamos
        serverSocket.close();//cerramos el socket del server
        socket.close();//cerramos el socket de cliente.
        

        
        
        
        
    }
}
