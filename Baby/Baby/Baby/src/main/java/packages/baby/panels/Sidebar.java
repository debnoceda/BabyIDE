/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package packages.baby.panels;

/**
 *
 * @author liaminakigillamac
 */
public class Sidebar extends javax.swing.JPanel {

    /**
     * Creates new form Sidebar
     */
    public Sidebar() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        home1 = new packages.baby.components.Home();
        openFile1 = new packages.baby.components.OpenFile();
        save1 = new packages.baby.components.Save();
        saveAs1 = new packages.baby.components.SaveAs();

        setBackground(new java.awt.Color(31, 31, 31));

        home1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home.png"))); // NOI18N
        add(home1);

        openFile1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openFile.png"))); // NOI18N
        add(openFile1);

        save1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        add(save1);

        saveAs1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/saveAs.png"))); // NOI18N
        add(saveAs1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private packages.baby.components.Home home1;
    private packages.baby.components.OpenFile openFile1;
    private packages.baby.components.Save save1;
    private packages.baby.components.SaveAs saveAs1;
    // End of variables declaration//GEN-END:variables
}
