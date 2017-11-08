/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import UI.PanelPrimario;
import UI.Principal;
import algoritmoapriori.Apriori;
import algoritmoapriori.Regla;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author Propietario
 */
public class ControladorPanelPrimario implements ActionListener, KeyListener{
    
    PanelPrimario pp;
    Principal principal;
    String directorio;
    File configFile = new File("config.properties");
    JProgressBar barraProgreso = new JProgressBar(); 

    public ControladorPanelPrimario(PanelPrimario pp,Principal principal) {
        this.pp=pp;
        this.principal=principal;
        this.pp.barraProgreso.setVisible(false);
        this.pp.seleccionarBtn.addActionListener(this);
        this.pp.calcularReglas.addActionListener(this);
        this.pp.confianzatxf.addKeyListener(this);
        this.pp.path.addActionListener(this);
        cargarPath();
    }
    
    
    
     public void cargarPath (){
        FileReader reader =  null;
        try {
            reader = new FileReader(configFile);
            Properties properties = new Properties();
            properties.load(reader);
            String directori = properties.getProperty("directorio");
            pp.path.setText(directori);
            reader.close();       
            } catch (IOException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }         

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pp.calcularReglas){
          new LlamaApriori().execute();          
        }
        
        if(e.getSource()==pp.seleccionarBtn){
           JFileChooser chooser = new JFileChooser();
           chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
           chooser.showOpenDialog(chooser);
           File file = chooser.getSelectedFile();
           directorio = file.getAbsolutePath();
           pp.path.setText(directorio);
           properties();          
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
         if (e.getKeyCode()==KeyEvent.VK_ENTER){
              new LlamaApriori().execute();      
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public class LlamaApriori extends SwingWorker<Void, Void>{
                
        List<Regla>regla ;
        Apriori ap;
        
        @Override
        //doInBackground() ejecuta en segundo plano todo el proceso principal, el algoritmo Apriori
        protected Void doInBackground() throws Exception {
            pp.barraProgreso.setIndeterminate(true);
            pp.barraProgreso.setVisible(true);
            
            //barraProgreso.setIndeterminate(true);
            float minSup = 0;
            float minConf = 0;
            String path = null;
            //debe llamar al metodo q llame a todo
            if(pp.confianzatxf.getText().length() == 0 || pp.Soportetxf.getText().length() == 0 || pp.path.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Debe completar todos los campos","Aviso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                minSup = Float.parseFloat(pp.Soportetxf.getText());
                minConf = Float.parseFloat(pp.confianzatxf.getText());
                path = pp.path.getText();
                ap = new Apriori(minSup,minConf,path);
               ap.correrAlgoritmo();
            }
           
            
            return null;
        }
        
        @Override
        //cuando termina el algoritmo debe mostrar en el done
        public void done(){
           pp.barraProgreso.setVisible(false);
           new ControladorPanelSecundario(principal).cargarListas(ap.reglasFinales);
           CardLayout cl = (CardLayout) principal.Contenedor.getLayout();
           cl.show(principal.Contenedor,"card3");
        
        }
    }
    
}
