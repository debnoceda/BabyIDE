/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author liaminakigillamac
 */
public class SidebarBtn extends JButton{
    
    public SidebarBtn() {
        initComponents();
    }
    
    private void initComponents() {
        setBackground(new java.awt.Color(31, 31, 31));
        setBorder(null);
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setPreferredSize(new java.awt.Dimension(40, 40));
        
        addMouseListener(new MouseListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new java.awt.Color(255, 0, 0));
                setBorder(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }
        });
        
    }
    
}
