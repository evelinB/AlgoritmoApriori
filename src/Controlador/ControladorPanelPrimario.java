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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Propietario
 */
public class ControladorPanelPrimario implements ActionListener, KeyListener{
    
    PanelPrimario pp;
    Principal principal;
    String directorio;
    String path="";
    File configFile = new File("config.properties");
    JProgressBar barraProgreso = new JProgressBar();
    boolean banderaSoporte = false;
    boolean banderaConfianza = false;
    boolean banderaSupMayorConf = false;
    int sopAux;     

    public ControladorPanelPrimario(PanelPrimario pp,Principal principal) {
        this.pp=pp;
        this.principal=principal;
        this.pp.barraProgreso.setVisible(false);
        this.pp.seleccionarBtn.addActionListener(this);
        this.pp.calcularReglas.addActionListener(this);
        this.pp.soporteTxf.addKeyListener(this);        
        this.pp.confianzaTxf.addKeyListener(this);                             
        this.pp.path.addActionListener(this);
        cargarPath();
    }
    
    
    
    public void cargarPath (){
        FileReader reader =  null;
        try {
            reader = new FileReader(configFile);
            Properties properties = new Properties();
            properties.load(reader);
            path = properties.getProperty("directorio");
            pp.path.setText(path);
            reader.close();       
            } catch (IOException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }         

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pp.calcularReglas){
            if(!pp.soporteTxf.getText().equals("")|| !pp.confianzaTxf.getText().equals("")){
            if(banderaSoporte){   
                if(banderaConfianza){   
                    new LlamaApriori().execute();}
                else{
                    JOptionPane.showMessageDialog(pp, "El valor de la confianza debe ser menor a 100", "Error", JOptionPane.ERROR_MESSAGE);         
                } 
            }
            else{
            JOptionPane.showMessageDialog(pp, "El valor del soporte debe ser menor a 100", "Error", JOptionPane.ERROR_MESSAGE);
            } 
             }else{
                JOptionPane.showMessageDialog(pp, "Complete todos los campos", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
               }                                
        }           
           
        if(e.getSource()==pp.seleccionarBtn){            
           JFileChooser chooser = new JFileChooser();
           FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text","dat");
           chooser.setFileFilter(filter); 
           chooser.setAcceptAllFileFilterUsed(false);
           //JFileChooser chooser = new JFileChooser();
           chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
           chooser.setCurrentDirectory(new File(path));
           int returnVal = chooser.showOpenDialog(pp);
           //Verifico que aprete botón aceptar
           if (returnVal == JFileChooser.APPROVE_OPTION){               
                File file = chooser.getSelectedFile();
                directorio = file.getAbsolutePath();
                path=directorio;
                pp.path.setText(directorio);
                properties();
           }
        }
    }  
    
    public void properties(){
        //FileWriter fw = null;
        BufferedWriter fw = null;
        try {
            Properties properties = new Properties();  
            properties.setProperty("directorio", directorio);            
            //fw = new FileWriter(configFile);            
            fw = new BufferedWriter(new FileWriter(configFile));
            fw.write("");
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
        if(e.getSource()==pp.soporteTxf || e.getSource()==pp.confianzaTxf){
                char c = e.getKeyChar();
                 if(!(Character.isDigit(c))){
                    e.consume();
                 }          
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
         if (e.getKeyCode()==KeyEvent.VK_ENTER){
              new LlamaApriori().execute();      
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()==pp.soporteTxf){
          if(!pp.soporteTxf.getText().equals("")){    
                 if((Integer.parseInt(pp.soporteTxf.getText())>100)||(Integer.parseInt(pp.soporteTxf.getText())<1)) {  
                       Border border = BorderFactory.createLineBorder(Color.red);
                       pp.soporteTxf.setBorder(border);
                       banderaSoporte=false;                       
                    }else{
                      Border border = BorderFactory.createLineBorder(Color.black);
                      pp.soporteTxf.setBorder(border);
                      banderaSoporte=true;
                    }}
        }
        if(e.getSource()==pp.confianzaTxf){
            char c = e.getKeyChar();
                 if(Character.isDigit(c)){
                    if((Integer.parseInt(pp.confianzaTxf.getText()))<(Integer.parseInt(pp.soporteTxf.getText()))){                       
                       Border border = BorderFactory.createLineBorder(Color.red);
                       pp.confianzaTxf.setBorder(border);
                       banderaSupMayorConf=false;
                    }
                    else{
                       if(Integer.parseInt(pp.confianzaTxf.getText())>100) {  
                            Border border = BorderFactory.createLineBorder(Color.red);
                            pp.confianzaTxf.setBorder(border);
                            banderaConfianza=false;

                      }else{
                         Border border = BorderFactory.createLineBorder(Color.black);
                         pp.confianzaTxf.setBorder(border);
                         banderaConfianza=true;
                      }
                    }                     
                }
        }
    }
    
    public class LlamaApriori extends SwingWorker<Void, Void>{
                
        Apriori ap;
        List<Regla> reglas = null ;
         float minSup = 0;
            float minConf = 0;
            String path = "";
        
        @Override
        //doInBackground() ejecuta en segundo plano todo el proceso principal, el algoritmo Apriori
        protected Void doInBackground() throws Exception {
            pp.barraProgreso.setIndeterminate(true);
            pp.barraProgreso.setVisible(true);
           
            //debe llamar al metodo q llame a todo
            if(pp.confianzaTxf.getText().length() == 0 || pp.soporteTxf.getText().length() == 0 || pp.path.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Debe completar todos los campos","Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                minSup = Float.parseFloat(pp.soporteTxf.getText());
                minConf = Float.parseFloat(pp.confianzaTxf.getText());
                path = pp.path.getText();
                ap = new Apriori(minSup,minConf,path);
                ap.correrAlgoritmo();
                reglas = ap.reglasFinales;
            }                      
            return null;
        }
        
        @Override
        //cuando termina el algoritmo debe mostrar en el done
        public void done(){
           pp.barraProgreso.setVisible(false);
           new ControladorPanelSecundario(principal).cargarListas(reglas);
           CardLayout cl = (CardLayout) principal.Contenedor.getLayout();
           cl.show(principal.Contenedor,"card3");
           pp.confianzaTxf.setText(pp.confianzaTxf.getText());
           pp.soporteTxf.setText(pp.soporteTxf.getText());
        }
    }        
}
