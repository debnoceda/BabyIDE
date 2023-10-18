package packages.baby.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.WindowConstants;

/**
 *
 * @author liaminakigillamac
 */

public class Ide extends javax.swing.JFrame {
    private String afileName;

    public Ide() {
        setTitle("Baby");
        initComponents();
        setupKeyboardShortcuts();
        WindowClosingHandler();
        Image img = new ImageIcon(this.getClass().getResource("/icons/Logo.png")).getImage();
        this.setIconImage(img);
        updateFileName();  
        setTitle(afileName);
    }
    
    public void updateFileName() {
        afileName = editor.getFileName();
        fileName.setText(afileName);
        setTitle(afileName);
    }
   
    
    
    public class CustomCloseDialog extends JDialog {
        private int dialogResult = -1; // Default: Cancel

        public CustomCloseDialog(JFrame parentFrame) {
            super(parentFrame, "Confirm Exit", true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel messagePanel = new JPanel();
            JLabel messageLabel = new JLabel("You have unsaved changes. What do you want to do?");
            messagePanel.add(messageLabel);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton deleteButton = new JButton("Delete");
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogResult = 0; // Delete
                    dispose();
                }
            });

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogResult = 1; // Save
                    dispose();
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogResult = 2; // Cancel
                    dispose();
                }
            });

            buttonPanel.add(deleteButton);
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            add(messagePanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(parentFrame);
        }

        public int getDialogResult() {
            return dialogResult;
        }
    }

    public class CustomOpenDialog extends JDialog {
        private int dialogResult = -1; // Default: Cancel

        public CustomOpenDialog(JFrame parentFrame) {
            super(parentFrame, "Confirm Open New File?", true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel messagePanel = new JPanel();
            JLabel messageLabel = new JLabel("You have unsaved changes. What do you want to do?");
            messagePanel.add(messageLabel);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton openFileButton = new JButton("Open File");
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");

            openFileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogResult = 0; // Open File
                    dispose();
                }
            });

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogResult = 1; // Save
                    dispose();
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogResult = 2; // Cancel
                    dispose();
                }
            });

            buttonPanel.add(openFileButton);
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            add(messagePanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(parentFrame);
        }

        public int getDialogResult() {
            return dialogResult;
        }
    }

    
    private void WindowClosingHandler() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
                
            }
        });
        
    }

    public void handleWindowClosing() {
        
        if (editor != null && editor.hasUnsavedChanges()) {
            CustomCloseDialog closeDialog = new CustomCloseDialog(this);
            closeDialog.setVisible(true);

            int choice = closeDialog.getDialogResult();

            if (choice == 0) {
                // User clicked "Delete"
                dispose();
            } else if (choice == 1) {
                // User clicked "Save"
                editor.save();
                return;
            } else if (choice == 2) {
                // User clicked "Cancel" or closed the dialog
                return;
            }
        }
        dispose();
    }
    
    //checks if the HomeScreen window is open
    public boolean isHomeScreenOpen() {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof HomeScreen && frame.isVisible()) {
                return true;
            }
        }
        return false;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idePnl = new javax.swing.JPanel();
        sidebarPnl = new javax.swing.JPanel();
        Home = new packages.baby.components.SidebarBtn();
        Save = new packages.baby.components.SidebarBtn();
        SaveAs = new packages.baby.components.SidebarBtn();
        Open = new packages.baby.components.SidebarBtn();
        TempUndo = new packages.baby.components.SidebarBtn();
        TempRedo = new packages.baby.components.SidebarBtn();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        TempRedo2 = new packages.baby.components.SidebarBtn();
        fileNamePanel = new javax.swing.JPanel();
        fileName = new javax.swing.JTextArea();
        jSplitPane1 = new javax.swing.JSplitPane();
        editor = new packages.baby.components.CodeEditor();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newWindow = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        openFile = new javax.swing.JMenuItem();
        closeFile = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        save = new javax.swing.JMenuItem();
        saveAs = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        undo = new javax.swing.JMenuItem();
        redo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        cut = new javax.swing.JMenuItem();
        copy = new javax.swing.JMenuItem();
        paste = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        runCode = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Baby");
        setBackground(new java.awt.Color(31, 31, 31));
        setPreferredSize(new java.awt.Dimension(1024, 768));

        idePnl.setBackground(new java.awt.Color(51, 51, 51));
        idePnl.setForeground(new java.awt.Color(31, 31, 31));
        idePnl.setToolTipText("");

        sidebarPnl.setBackground(new java.awt.Color(31, 31, 31));

        Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home.png"))); // NOI18N
        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomeMouseEntered(evt);
            }
        });
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });

        Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveMouseEntered(evt);
            }
        });
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        SaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/saveAs.png"))); // NOI18N
        SaveAs.setToolTipText("");
        SaveAs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveAsMouseEntered(evt);
            }
        });
        SaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAsActionPerformed(evt);
            }
        });

        Open.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openFile.png"))); // NOI18N
        Open.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OpenMouseEntered(evt);
            }
        });
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        TempUndo.setForeground(new java.awt.Color(255, 255, 255));
        TempUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/undo.png"))); // NOI18N
        TempUndo.setToolTipText("");
        TempUndo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TempUndoMouseEntered(evt);
            }
        });
        TempUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempUndoActionPerformed(evt);
            }
        });

        TempRedo.setForeground(new java.awt.Color(255, 255, 255));
        TempRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/redo.png"))); // NOI18N
        TempRedo.setToolTipText("");
        TempRedo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TempRedoMouseEntered(evt);
            }
        });
        TempRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempRedoActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sidebarLogo.png"))); // NOI18N

        TempRedo2.setForeground(new java.awt.Color(255, 255, 255));
        TempRedo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/run.png"))); // NOI18N
        TempRedo2.setToolTipText("");
        TempRedo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TempRedo2MouseEntered(evt);
            }
        });
        TempRedo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempRedo2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidebarPnlLayout = new javax.swing.GroupLayout(sidebarPnl);
        sidebarPnl.setLayout(sidebarPnlLayout);
        sidebarPnlLayout.setHorizontalGroup(
            sidebarPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarPnlLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(sidebarPnlLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(sidebarPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TempRedo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Open, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TempUndo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TempRedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sidebarPnlLayout.setVerticalGroup(
            sidebarPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPnlLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Open, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TempUndo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TempRedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TempRedo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(47, 47, 47))
        );

        fileNamePanel.setBackground(new java.awt.Color(31, 31, 31));

        fileName.setEditable(false);
        fileName.setBackground(new java.awt.Color(31, 31, 31));
        fileName.setColumns(20);
        fileName.setForeground(new java.awt.Color(255, 255, 255));
        fileName.setRows(5);

        javax.swing.GroupLayout fileNamePanelLayout = new javax.swing.GroupLayout(fileNamePanel);
        fileNamePanel.setLayout(fileNamePanelLayout);
        fileNamePanelLayout.setHorizontalGroup(
            fileNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fileNamePanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        fileNamePanelLayout.setVerticalGroup(
            fileNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fileNamePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jSplitPane1.setBackground(new java.awt.Color(51, 51, 51));
        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setTopComponent(editor);

        jTextField1.setBackground(new java.awt.Color(31, 31, 31));
        jTextField1.setBorder(null);
        jSplitPane1.setRightComponent(jTextField1);

        javax.swing.GroupLayout idePnlLayout = new javax.swing.GroupLayout(idePnl);
        idePnl.setLayout(idePnlLayout);
        idePnlLayout.setHorizontalGroup(
            idePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(idePnlLayout.createSequentialGroup()
                .addComponent(sidebarPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(idePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(idePnlLayout.createSequentialGroup()
                        .addComponent(fileNamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 704, Short.MAX_VALUE))
                    .addComponent(jSplitPane1))
                .addContainerGap())
        );
        idePnlLayout.setVerticalGroup(
            idePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebarPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(idePnlLayout.createSequentialGroup()
                .addComponent(fileNamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        getContentPane().add(idePnl, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(31, 31, 31));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorderPainted(false);
        jMenuBar1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jMenu1.setText("File");

        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newWindow.setText("New Window");
        newWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newWindowActionPerformed(evt);
            }
        });
        jMenu1.add(newWindow);
        jMenu1.add(jSeparator1);

        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openFile.setText("Open File");
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });
        jMenu1.add(openFile);

        closeFile.setText("Close File");
        closeFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeFileActionPerformed(evt);
            }
        });
        jMenu1.add(closeFile);
        jMenu1.add(jSeparator2);

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jMenu1.add(save);

        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveAs.setText("Save As");
        saveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsActionPerformed(evt);
            }
        });
        jMenu1.add(saveAs);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undo.setText("Undo");
        undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoActionPerformed(evt);
            }
        });
        jMenu2.add(undo);

        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        redo.setText("Redo");
        redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoActionPerformed(evt);
            }
        });
        jMenu2.add(redo);
        jMenu2.add(jSeparator3);

        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        cut.setText("Cut");
        cut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutActionPerformed(evt);
            }
        });
        jMenu2.add(cut);

        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        copy.setText("Copy");
        copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyActionPerformed(evt);
            }
        });
        jMenu2.add(copy);

        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        paste.setText("Paste");
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteActionPerformed(evt);
            }
        });
        jMenu2.add(paste);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Run");

        runCode.setText("Run Code");
        jMenu3.add(runCode);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        
        if (!isHomeScreenOpen()) {
            HomeScreen home = new HomeScreen();
            home.setVisible(true);
        }
        
        else {
            System.out.println("Failed");
        }
//        //opens the home screen
//        HomeScreen home = new HomeScreen ();
//        home.show(); //display homeScreen here
//        
//        dispose(); //close the current frame after opening homeScreen
        
    }//GEN-LAST:event_HomeActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        editor.save();
        updateFileName();  
    }//GEN-LAST:event_SaveActionPerformed

    private void SaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAsActionPerformed
        editor.saveAs();
        updateFileName();  
    }//GEN-LAST:event_SaveAsActionPerformed

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        if (editor.hasUnsavedChanges()){
            CustomOpenDialog closeDialog = new CustomOpenDialog(this);
            closeDialog.setVisible(true);

            int choice = closeDialog.getDialogResult();

            if (choice == 0) {
                // User clicked "Open File"
                editor.open();
                return;
            } else if (choice == 1) {
                // User clicked "Save"
                editor.save();
                return;
            } else if (choice == 2) {
                // User clicked "Cancel" or closed the dialog
                return;
            }
        }
        
        editor.open();
        updateFileName();  
    }//GEN-LAST:event_OpenActionPerformed

    private void newWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newWindowActionPerformed
        Ide ide = new Ide();
        ide.setVisible(true);
    }//GEN-LAST:event_newWindowActionPerformed

    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
        if (editor.hasUnsavedChanges()){
            int choice = JOptionPane.showConfirmDialog(
            Ide.this,
            "You have unsaved changes. Do you want to open another file without saving?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION
            );
            
            if (choice == JOptionPane.NO_OPTION) {
                return;
            }
        }
        
        editor.open();
        updateFileName();  
    }//GEN-LAST:event_openFileActionPerformed

    private void undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoActionPerformed
        editor.undo();
    }//GEN-LAST:event_undoActionPerformed

    private void TempUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TempUndoActionPerformed
        editor.undo();
    }//GEN-LAST:event_TempUndoActionPerformed

    private void TempRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TempRedoActionPerformed
        editor.redo();
    }//GEN-LAST:event_TempRedoActionPerformed

    private void redoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoActionPerformed
        editor.redo();
    }//GEN-LAST:event_redoActionPerformed

    private void closeFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeFileActionPerformed
        if (editor.hasUnsavedChanges()){
            int choice = JOptionPane.showConfirmDialog(
            Ide.this,
            "You have unsaved changes. Do you want to open another file without saving?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION
            );
            
            if (choice == JOptionPane.NO_OPTION) {
                return;
            }
        }
        
        editor.close();
        updateFileName();  
    }//GEN-LAST:event_closeFileActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        editor.save();
        updateFileName();  
    }//GEN-LAST:event_saveActionPerformed


    private void saveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsActionPerformed
        editor.saveAs();
        updateFileName();  
    }//GEN-LAST:event_saveAsActionPerformed


    private void cutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutActionPerformed
        try {
            editor.cut();
        } catch (BadLocationException ex) {
            Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cutActionPerformed

    private void copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyActionPerformed
        System.out.println("Mid");
        try {
            editor.copy();
        } catch (BadLocationException ex) {
            Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_copyActionPerformed

    private void pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteActionPerformed
        editor.paste();

    }//GEN-LAST:event_pasteActionPerformed

    private void SaveAsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveAsMouseEntered
        SaveAs.setToolTipText("SaveAs");
    }//GEN-LAST:event_SaveAsMouseEntered

    private void HomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseEntered
        Home.setToolTipText("Home");
    }//GEN-LAST:event_HomeMouseEntered

    private void OpenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OpenMouseEntered
         Open.setToolTipText("Open");
    }//GEN-LAST:event_OpenMouseEntered

    private void SaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveMouseEntered
        Save.setToolTipText("Save");
    }//GEN-LAST:event_SaveMouseEntered

    private void TempUndoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TempUndoMouseEntered
        undo.setToolTipText("Undo");
    }//GEN-LAST:event_TempUndoMouseEntered

    private void TempRedoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TempRedoMouseEntered
        redo.setToolTipText("Redo");
    }//GEN-LAST:event_TempRedoMouseEntered

    private void TempRedo2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TempRedo2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TempRedo2MouseEntered

    private void TempRedo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TempRedo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TempRedo2ActionPerformed
          

    public void openAction(java.awt.event.ActionEvent evt) {
        OpenActionPerformed(evt);
    }
    
    private void setupKeyboardShortcuts() {
        int menuShortcutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
          
        // Set up the Save keyboard shortcut (Ctrl + S or Cmd + S)
        setupKeyboardShortcut("Save", KeyEvent.VK_S, menuShortcutKey);

        // Set up the Save As keyboard shortcut (Shift + Ctrl + S or Shift + Cmd + S)
        setupKeyboardShortcut("Save As", KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK | menuShortcutKey);
        
        // Set up the Save As keyboard shortcut (Ctrl + S or Cmd + Z)
        setupKeyboardShortcut("Undo", KeyEvent.VK_Z, menuShortcutKey);
        
        // Set up the Save As keyboard shortcut (Shift + Ctrl + Z or Shift + Cmd + Z)
        setupKeyboardShortcut("Redo", KeyEvent.VK_Z, KeyEvent.SHIFT_DOWN_MASK | menuShortcutKey);
        
        // Set up the New Window keyboard shortcut (Ctrl + N or Cmd + N)
        setupKeyboardShortcut("New Window", KeyEvent.VK_N, menuShortcutKey);
        
        // Set up the Open File keyboard shortcut (Ctrl + O) or Cmd + O)
        setupKeyboardShortcut("Open File", KeyEvent.VK_O, menuShortcutKey);
        
        // Set up the Open File keyboard shortcut (Ctrl + C) or Cmd + C)
        setupKeyboardShortcut("Copy", KeyEvent.VK_C, menuShortcutKey);
    }

    private void setupKeyboardShortcut(String actionName, int keyCode, int modifier) {
        
        Action action = new AbstractAction(actionName) {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(actionName);
            }
        };

        action.putValue(Action.MNEMONIC_KEY, keyCode);

        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifier);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionName);
        getRootPane().getActionMap().put(actionName, action);
    }
    
    private void handleAction(String actionName) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionName);

        switch (actionName) {
            case "Save" -> SaveActionPerformed(event);
            case "Save As" -> SaveAsActionPerformed(event);
            case "Undo" -> TempUndoActionPerformed(event);
            case "Redo" -> TempRedoActionPerformed(event);
            case "New Window" -> newWindowActionPerformed(event);
            case "Open File" -> OpenActionPerformed(event);
            case "Copy" -> copyActionPerformed(event);
        }
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private packages.baby.components.SidebarBtn Home;
    private packages.baby.components.SidebarBtn Open;
    private packages.baby.components.SidebarBtn Save;
    private packages.baby.components.SidebarBtn SaveAs;
    private packages.baby.components.SidebarBtn TempRedo;
    private packages.baby.components.SidebarBtn TempRedo2;
    private packages.baby.components.SidebarBtn TempUndo;
    private javax.swing.JMenuItem closeFile;
    private javax.swing.JMenuItem copy;
    private javax.swing.JMenuItem cut;
    private packages.baby.components.CodeEditor editor;
    private javax.swing.JTextArea fileName;
    private javax.swing.JPanel fileNamePanel;
    private javax.swing.JPanel idePnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JMenuItem newWindow;
    private javax.swing.JMenuItem openFile;
    private javax.swing.JMenuItem paste;
    private javax.swing.JMenuItem redo;
    private javax.swing.JMenuItem runCode;
    private javax.swing.JMenuItem save;
    private javax.swing.JMenuItem saveAs;
    private javax.swing.JPanel sidebarPnl;
    private javax.swing.JMenuItem undo;
    // End of variables declaration//GEN-END:variables
}
