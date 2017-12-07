/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import UI.PanelPrimario;
import UI.PanelSecundario;
import UI.Principal;
import algoritmoapriori.Regla;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author Propietario
 */
public class ControladorPanelSecundario implements ActionListener{
    
    PanelSecundario ps;
    PanelPrimario pp = new PanelPrimario();
    Principal principal;
    List<Regla>reglas;
    ImageIcon image = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/flecha.png"));
    
    public ControladorPanelSecundario(Principal principal) {
        this.principal=principal;     
        this.ps=principal.panelSecundario;
        this.ps.volver.addActionListener(this);
    }
    
    public void cargarListas( List<Regla> lista){
        for (int i = 0; i < lista.size(); i++) {
          JLabel antecedente = new JLabel(""+lista.get(i).antecedentes);
          JLabel consecuente = new JLabel(lista.get(i).consecuentes+"");
          String conf = Float.toString(lista.get(i).getConfianza());
          JLabel confianza = new JLabel (conf);
          String sup = Float.toString(lista.get(i).getSoporte());
          JLabel soporte = new JLabel (sup);
            JLabel icono = new JLabel(image);
             principal.panelSecundario.panel2.add(antecedente);
              principal.panelSecundario.panel2.add(icono);
               principal.panelSecundario.panel2.add(consecuente);
                principal.panelSecundario.panel2.add(confianza);
                 principal.panelSecundario.panel2.add(soporte);
         } 
          // BoxLayout boxLayout = new BoxLayout( this.principal.panelSecundario.panel2, BoxLayout.Y_AXIS);
           principal.panelSecundario.panel2.setLayout(new GridLayout(lista.size(), 3));
           principal.panelSecundario.reglas.setText(String.valueOf(lista.size()));
           principal.panelSecundario.soporte.setText(principal.panelPrimario.soporteTxf.getText());
           principal.panelSecundario.confianza.setText(principal.panelPrimario.confianzaTxf.getText());
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
