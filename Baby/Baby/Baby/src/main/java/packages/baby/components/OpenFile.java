
package packages.baby.components;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import packages.baby.frames.Ide;

public class OpenFile extends SidebarBtn{
    
    private Ide ide;
    
    public OpenFile(Ide ide) {
        super();
        this.ide = ide;
    }
    
    public OpenFile() {
        super();
    }
    
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked (override)!");
//        ide.handleWindowClosing();
    }

    
}
