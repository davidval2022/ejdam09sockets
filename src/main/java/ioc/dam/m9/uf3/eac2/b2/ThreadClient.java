/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ioc.dam.m9.uf3.eac2.b2;

/**
 *
 * @author tomas
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class ThreadClient extends Thread {
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    

    public ThreadClient(Socket client) {
        try {
            this.client = client;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    

    
    @Override
    public void run() {
        String msg;
        SoftCatalaClient soft = new SoftCatalaClient();
        String traduccio;
        boolean salir = false;
        

        try {
            
            //IMPLEMENTA
            BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader lector = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(!salir){
                //enviamos al cliente la pregunta y el mensaje de bienvenida
                msg = "Escribe la palabra a traducir!!  Finalice con Exit";                
                escriptor.write(msg);//enviamos
                escriptor.newLine();
                escriptor.flush(); 
                //leemos la respuesta con la palabra a buscar                
                String palabra = lector.readLine(); //recibimos              
                if(palabra.equalsIgnoreCase("exit")){
                    System.out.println("Ciente desconectado");
                    salir = true;
                    escriptor.close();
                    lector.close();
                    client.close(); 
                    
                }else{
                    System.out.println("Palabra a traducir: "+palabra);
                    msg = soft.translate(palabra);
                    //devolvemos la respueta
                    escriptor.write(msg);
                    escriptor.newLine();
                    escriptor.flush();                    
                }

                
            
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}