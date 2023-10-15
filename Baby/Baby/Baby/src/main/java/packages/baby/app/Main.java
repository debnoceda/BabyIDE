/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.app;

import javax.swing.SwingUtilities;
import packages.baby.components.CodeFile;
import packages.baby.frames.HomeScreen;
import packages.baby.frames.Ide;

/**
 *
 * @author liaminakigillamac
 */
public class Main {
    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                HomeScreen home = new HomeScreen();
                home.setVisible(true);
            }
        });
    }
}
