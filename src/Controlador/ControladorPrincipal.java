/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import UI.Principal;
import algoritmoapriori.Apriori;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author Eve
 */
public class ControladorPrincipal implements ActionListener, KeyListener {
    Principal principal;
    String directorio;
    File configFile = new File("config.properties");
    //public static float minSup = (float) 0.1;
    //public static int minConf = 0;
    JProgressBar barraProgreso = new JProgressBar(); 
   
    public ControladorPrincipal() {
        principal = new Principal();
        principal.seleccionarBtn.addActionListener(this);
        principal.calcularReglas.addActionListener(this);
        principal.Soportetxf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
              
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        principal.confianzatxf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
           
            }

            @Override
            public void keyReleased(KeyEvent e) {
           
            }
        });
        
        principal.setLocationRelativeTo(null);
        cargarPath();
        principal.setVisible(true);
        
        
        
    }
   
    public void cargarPath (){
        FileReader reader =  null;
        try {
            reader = new FileReader(configFile);
            Properties properties = new Properties();
            properties.load(reader);
            String directori = properties.getProperty("directorio");
            principal.path.setText(directori);
            reader.close();       
            } catch (IOException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == principal.seleccionarBtn){
           JFileChooser chooser = new JFileChooser();
           chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
           chooser.showOpenDialog(chooser);
           File file = chooser.getSelectedFile();
           directorio = file.getAbsolutePath();
           principal.path.setText(directorio);
           properties();
           
       }
       if(e.getSource() == principal.calcularReglas){
           new LlamaApriori().execute();
       }
    }
    public void properties(){
        FileWriter fw = null;
        try {
            Properties properties = new Properties();  
            properties.setProperty("directorio", directorio);
            fw = new FileWriter(configFile);
            properties.store(fw,"config");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    
    //a traves del boton CALCULAR REGLAS dispara toda la ejecucion del algoritmo
    public class LlamaApriori extends SwingWorker<Void, Void>{
        
        @Override
        //doInBackground() ejecuta en segundo plano todo el proceso principal, el algoritmo Apriori
        protected Void doInBackground() throws Exception {
            principal.barraProgreso.setIndeterminate(true);
            principal.barraProgreso.setVisible(true);
            
            //barraProgreso.setIndeterminate(true);
            float minSup = 0;
            float minConf = 0;
            String path = null;
            //debe llamar al metodo q llame a todo
            if(principal.confianzatxf.getText().length() == 0 || principal.Soportetxf.getText().length() == 0 || principal.path.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Debe completar todos los campos","Aviso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                minSup = Float.parseFloat(principal.Soportetxf.getText());
                minConf = Float.parseFloat(principal.confianzatxf.getText());
                path = principal.path.getText();
                Apriori ap = new Apriori(minSup,minConf,path);
               ap.correrAlgoritmo();
            }
           
            
            return null;
        }
        
        @Override
        //cuando termina el algoritmo debe mostrar en el done
        public void done(){
            principal.barraProgreso.setVisible(false);
        }
    }
}