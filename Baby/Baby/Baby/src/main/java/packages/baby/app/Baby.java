/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.app;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import packages.baby.components.CodeFile;
import packages.baby.frames.HomeScreen;
import packages.baby.frames.Ide;

/**
 *
 * @author liaminakigillamac
 */
public class Baby {
    public static void main (String[] args){
        try {
            Thread.sleep(3350);
        } catch (InterruptedException ex) {
            Logger.getLogger(Baby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Check if the operating system is macOS
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            
        }
              
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeScreen().setVisible(true);
            }
        });
    }
}
