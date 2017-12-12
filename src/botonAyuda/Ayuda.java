/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botonAyuda;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author Propietario
 */
public class Ayuda extends JLabel {
    private String link = "";
    private boolean dentro;
    
    public Ayuda(){
        addMouseListener(new MouseAdapter() {
           
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    abrirLink();
                    
                    //super.mousePressed(e);
                } catch (IOException ex) {
                    Logger.getLogger(Ayuda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                dentro = true;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                dentro = false;
                repaint();
            }
  
        });
    }
    private void abrirLink() throws IOException {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception e) {
        }
       
}  
      
    @Override
    public void paint(Graphics g) {
        Graphics2D gd = (Graphics2D)g;
        
        if (dentro){
          gd.rotate(Math.toRadians(30),getWidth()/2, getHeight()/2);
        }
        super.paint(gd);
        
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
