/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import UI.PanelSecundario;
import UI.Principal;
import algoritmoapriori.Regla;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author Propietario
 */
public class ControladorPanelSecundario implements ActionListener{
    
    PanelSecundario ps;
    Principal principal;
    List<Regla>reglas;
    private DefaultListModel model;

    public ControladorPanelSecundario(Principal principal) {
        System.out.println("reglascons");
        principal.panelSecundario.volver.addActionListener(this);
        this.principal=principal;
        
        Synchronizer synchronizer = new Synchronizer(principal.panelSecundario.jScrollPane1, principal.panelSecundario.jScrollPane2, principal.panelSecundario.jScrollPane3);
        principal.panelSecundario.jScrollPane1.getVerticalScrollBar().addAdjustmentListener(synchronizer);
        principal.panelSecundario.jScrollPane1.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
        principal.panelSecundario.jScrollPane2.getVerticalScrollBar().addAdjustmentListener(synchronizer);
        principal.panelSecundario.jScrollPane2.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
    }
    
    public void cargarListas( List<Regla> lista){
        DefaultListModel lm1 = (DefaultListModel) principal.panelSecundario.lista1.getModel();
        DefaultListModel lm2 = (DefaultListModel) principal.panelSecundario.lista2.getModel();
        DefaultListModel lm3 = (DefaultListModel) principal.panelSecundario.lista3.getModel();
        for (int i = 0; i < lista.size(); i++) {
            lm1.addElement(lista.get(i).antecedentes);
            lm2.addElement(lista.get(i).consecuentes);
            lm3.addElement("-->");
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          if(e.getSource()==ps.volver){
              CardLayout cl = (CardLayout) principal.Contenedor.getLayout();
              cl.show(principal.Contenedor,"card2");
          }
    }
    
    class Synchronizer implements AdjustmentListener
{
    JScrollBar v1, h1, v2, h2, v3, h3;
  
    public Synchronizer(JScrollPane sp1, JScrollPane sp2, JScrollPane sp3)
    {
        v1 = sp1.getVerticalScrollBar();
        h1 = sp1.getHorizontalScrollBar();
        v2 = sp2.getVerticalScrollBar();
        h2 = sp2.getHorizontalScrollBar();
        v3 = sp3.getVerticalScrollBar();
        h3 = sp3.getHorizontalScrollBar();
    }
  
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        JScrollBar scrollBar = (JScrollBar)e.getSource();
        int value = scrollBar.getValue();
        JScrollBar target = null;
        JScrollBar target2 = null;
  
        if(scrollBar == v1){
            target = v2;target2 = v3;}
        if(scrollBar == h1){
            target = h2;target2 = h3;}
        if(scrollBar == v2){
            target = v1;target2 = v3;}
        if(scrollBar == h2){
            target = h1;target2 = h3;}
        if(scrollBar == v3){
            target = v1;target2 = v2;}
        if(scrollBar == h3){
            target = h1;target2 = h2;}
        target.setValue(value);
        target2.setValue(value);
    }
}
    
}
