/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import UI.Principal;
import algoritmoapriori.Apriori;
import java.awt.CardLayout;
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
 * @author 
 */
public class ControladorPrincipal  {
    Principal principal;
    String directorio;
    File configFile = new File("config.properties");
    ControladorPanelPrimario cpp;
    JProgressBar barraProgreso = new JProgressBar(); 
   
    public ControladorPrincipal() {
        principal = new Principal();     
        new ControladorPanelPrimario(principal.panelPrimario, principal);
        
        principal.setLocationRelativeTo(null);
        principal.setVisible(true);
        
    } 
       
   
}