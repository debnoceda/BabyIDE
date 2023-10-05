/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package packages.baby;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;

/** URGENT! Topic: Unsaved Changes
 * MIKO HERE. Working on the process regarding unsaved changes especially during opening new file and exiting application
 * work has been done in the event of exiting application when there are unsaved changes
 * HOWEVER, THERE IS A BUG! Due to setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE), the application only exits when 'YES' is chosen
 * from confirmation dialogue. The problem is that the confirmation dialogue appears only when there is/are unsaved change/s.
 * In the case of a new file with no changes/updates, the confirmation dialogue does not appear, hence, there is no way to exit application.
 * Currently finding ways to solve the problem
 * 
 * In the case of unsaved changes in the event of opening file, no work has been done yet.
 */
/**
 *
 * @author liaminakigillamac
 */
public class Ide extends javax.swing.JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private homeScreen homeScreenFrame;
    
    private File savedFile = null; // var to get copy file to save
    private boolean unsavedChanges = false; // var to know if update to txt is present
    /**
     * Creates new form Ide
     */
    public Ide() {
        initComponents();
        JButton [] btns =  {btnHome, btnOpenFile, btnSave, btnSaveAs};
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//        homeScreenFrame = new homeScreen ();
//        cardPanel.add(homeScreenFrame, "homeScreen");
//        getContentPane().add(cardPanel);
        
        jTextArea1.getDocument().addDocumentListener(new DocumentListener() { // bai wa ko kahibaw asa ni ibutang, diri lang sa hehe :3
            @Override
            public void insertUpdate(DocumentEvent e) {
                unsavedChanges = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                unsavedChanges = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                unsavedChanges = true;
            }
        });
               
        addWindowListener(new WindowAdapter() { // bai wa ko kahibaw asa ni ibutang, diri lang sa hehe :3 part 2
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                if (unsavedChanges) {
                    int choice = JOptionPane.showConfirmDialog(
                        Ide.this,
                        "You have unsaved changes. Do you want to exit without saving?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (choice == JOptionPane.NO_OPTION) {
                        return;
                    }
                    
                    // debating if option 'CANCEL' is necessary hmmmmmm
                    // this path connects when 'YES' is chosen
                    //dispose(); // Close the window // might be unnecessary idunno actually
                    System.exit(0); // Exit the application
                }           
            }
        });

        for (JButton btn : btns){
            btn.setUI(new BasicButtonUI());
//            btn.setBackground(Color.red);
            btn.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                    btn.setBackground(Color.red);
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    btn.setBackground(Color.red);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    btn.setBackground(Color.red);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
//                    btn.setBackground(Color.red);
                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    btn.setBackground(Color.red);
                }
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        pnlSide = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnOpenFile = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnSaveAs = new javax.swing.JButton();
        pnlCenter = new javax.swing.JPanel();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlRoot.setLayout(new java.awt.BorderLayout());

        pnlSide.setBackground(new java.awt.Color(31, 31, 31));
        pnlSide.setPreferredSize(new java.awt.Dimension(70, 0));

        btnHome.setBackground(new java.awt.Color(31, 31, 31));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home.png"))); // NOI18N
        btnHome.setBorder(null);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHome.setPreferredSize(new java.awt.Dimension(40, 40));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        pnlSide.add(btnHome);

        btnOpenFile.setBackground(new java.awt.Color(31, 31, 31));
        btnOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openFile.png"))); // NOI18N
        btnOpenFile.setBorder(null);
        btnOpenFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpenFile.setPreferredSize(new java.awt.Dimension(40, 40));
        btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenFileActionPerformed(evt);
            }
        });
        pnlSide.add(btnOpenFile);

        btnSave.setBackground(new java.awt.Color(31, 31, 31));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        btnSave.setBorder(null);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        pnlSide.add(btnSave);

        btnSaveAs.setBackground(new java.awt.Color(31, 31, 31));
        btnSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/saveAs.png"))); // NOI18N
        btnSaveAs.setBorder(null);
        btnSaveAs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveAs.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAsActionPerformed(evt);
            }
        });
        pnlSide.add(btnSaveAs);

        pnlRoot.add(pnlSide, java.awt.BorderLayout.WEST);

        pnlCenter.setBackground(new java.awt.Color(51, 51, 51));

        jTextArea1.setBackground(new java.awt.Color(31, 31, 31));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);

        javax.swing.GroupLayout pnlCenterLayout = new javax.swing.GroupLayout(pnlCenter);
        pnlCenter.setLayout(pnlCenterLayout);
        pnlCenterLayout.setHorizontalGroup(
            pnlCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCenterLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jTextArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addGap(72, 72, 72))
        );
        pnlCenterLayout.setVerticalGroup(
            pnlCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCenterLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jTextArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addGap(292, 292, 292))
        );

        pnlRoot.add(pnlCenter, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlRoot, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFileActionPerformed
        JFileChooser fileChooser = new JFileChooser();

        // use if you want to filter s.t. only .txt file can be opened
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.bby)", "bby");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                jTextArea1.setText(content.toString());

                reader.close();
                
                savedFile = fileChooser.getSelectedFile();
                String fileName = savedFile.getAbsolutePath();
                if (!fileName.endsWith(".bby")) {
                    fileName += ".bby";
                    savedFile = new File(fileName);
                }
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnOpenFileActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String code = jTextArea1.getText();

        if (savedFile == null) {
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.bby)", "bby");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Ensure the file has the ".bby" extension
                String fileName = selectedFile.getAbsolutePath();
                if (!fileName.endsWith(".bby")) {
                    fileName += ".bby";
                    selectedFile = new File(fileName);

                }

                try {
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(code);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "File saved successfully.");
                    savedFile = new File(fileName);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
                }
            }
            unsavedChanges = false;
        }

        if (savedFile != null) {
            try {
                FileWriter writer = new FileWriter(savedFile);
                writer.write(code);
                writer.close();
                unsavedChanges = false;
                JOptionPane.showMessageDialog(null, "File saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAsActionPerformed
        String code = jTextArea1.getText();

        JFileChooser fileChooser = new JFileChooser();

        // Create a file filter for .txt files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.bby)", "bby");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Ensure the file has the ".txt" extension
            String fileName = selectedFile.getAbsolutePath();
            if (!fileName.endsWith(".bby")) {
                fileName += ".bby";
                selectedFile = new File(fileName);
                
            }

            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(code);
                writer.close();
                JOptionPane.showMessageDialog(null, "File saved successfully.");
                savedFile = new File(fileName);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
            }
        }
        unsavedChanges = false;

    }//GEN-LAST:event_btnSaveAsActionPerformed

    
    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
//        cardLayout.show(cardPanel, "homeScreen");
    }//GEN-LAST:event_btnHomeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ide.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ide().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnOpenFile;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveAs;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel pnlCenter;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JPanel pnlSide;
    // End of variables declaration//GEN-END:variables
}
