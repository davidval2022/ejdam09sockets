package main.java.ioc.dam.m9.uf3.eac2.b2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Usuari
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * 
 */
public class SoftCatalaClient {

 
  public String translate(String word) {

        try {
            
            String urlS = "https://www.softcatala.org/diccionari-multilingue/paraula/";
            String result = ""; 
            //IMPLEMENTA
            urlS = urlS+word;
            Elements childs = parsejador(urlS);
            result = childs.text();

                               
            //pruebas
            //System.out.println(childs.size());
            //System.out.println(childs.html());
            //System.out.println(childs.text());//me quedo con este, creo que es el más facil de manejar
            //System.out.println(childs.toString());
            /*
            List nodes = new ArrayList(childs.textNodes());
            for(int i=0; i<nodes.size();i++){
                System.out.println(nodes.get(i));
            }*/
                          
            return result;
                 
        } catch (Exception ex) {
            System.out.println("Errorrrrrr");
            //return null;
            return "No hay resultado para esta respuesta en el servidor ... ...";
        }
    }
  
  public Elements parsejador(String urlS) throws IOException{
      
      Document doc = Jsoup.connect(urlS).get();
            Element elem = doc.getElementsByClass("col-sm-8 multilingue_list").first();
            Element ul = elem.children().get(0);
            Elements childs = ul.children();
      
      
      return childs;
  }
  

}