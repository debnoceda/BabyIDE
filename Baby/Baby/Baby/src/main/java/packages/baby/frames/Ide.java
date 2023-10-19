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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author liaminakigillamac
 */

public class Ide extends javax.swing.JFrame {
    private String afileName;

    public Ide() {
        setTitle("Baby");
        initComponents();
        initButtonActivity();
        setupButtonActivity();
        setupKeyboardShortcuts();
        WindowClosingHandler();
        Image img = new ImageIcon(this.getClass().getResource("/icons/Logo.png")).getImage();
        this.setIconImage(img);
        updateFileName();  
        setTitle(afileName);
    }
    
    public void updateFileName() {
        afileName = editor.getFileName();
//        fileName.setText(afileName);
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
                //return; // I don't know why but by commenting this out, the program works :)
            }
        }
        //dispose(); // when this exists, if you close the program and close the confirm dialog, the frame exits regardless
                     // when this disappears, if you close the program and close the confirm dialog, the next instance of closing the program will not occur
        
        
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
    
    public void setupButtonActivity(){
        editor.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonActivity();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonActivity();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonActivity();
            }
        });
    }
    
    public void initButtonActivity(){
        Save.setEnabled(false);
        save.setEnabled(false);
        
        Redo.setEnabled(false);
        redo.setEnabled(false);
        
        Undo.setEnabled(false);
        undo.setEnabled(false);
        
        closeFile.setEnabled(false);
    }
    
    public void updateButtonActivity(){
        boolean canUndo = editor.getUndoManager().canUndo();
        boolean canRedo = editor.getUndoManager().canRedo();
        boolean hasUnsavedChanges = editor.hasUnsavedChanges();
        
        Save.setEnabled(hasUnsavedChanges);
        save.setEnabled(hasUnsavedChanges);
        
        Redo.setEnabled(canRedo);
        redo.setEnabled(canRedo);
        
        Undo.setEnabled(canUndo);
        undo.setEnabled(canUndo);
        
        closeFile.setEnabled(true);
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
        Undo = new packages.baby.components.SidebarBtn();
        Redo = new packages.baby.components.SidebarBtn();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        Run = new packages.baby.components.SidebarBtn();
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

        Undo.setForeground(new java.awt.Color(255, 255, 255));
        Undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/undo.png"))); // NOI18N
        Undo.setToolTipText("");
        Undo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                UndoMouseEntered(evt);
            }
        });
        Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoActionPerformed(evt);
            }
        });

        Redo.setForeground(new java.awt.Color(255, 255, 255));
        Redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/redo.png"))); // NOI18N
        Redo.setToolTipText("");
        Redo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RedoMouseEntered(evt);
            }
        });
        Redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoActionPerformed(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(51, 51, 51));

        jSeparator5.setForeground(new java.awt.Color(51, 51, 51));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sidebarLogo.png"))); // NOI18N

        jSeparator6.setForeground(new java.awt.Color(51, 51, 51));

        Run.setForeground(new java.awt.Color(255, 255, 255));
        Run.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/run.png"))); // NOI18N
        Run.setToolTipText("");
        Run.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RunMouseEntered(evt);
            }
        });
        Run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunActionPerformed(evt);
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
                    .addComponent(Run, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Open, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Redo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sidebarPnlLayout.setVerticalGroup(
            sidebarPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPnlLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Open, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Redo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Run, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(19, 19, 19))
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
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
        );
        idePnlLayout.setVerticalGroup(
            idePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebarPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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
        updateButtonActivity();
    }//GEN-LAST:event_SaveActionPerformed

    private void SaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAsActionPerformed
        editor.saveAs();
        updateFileName();
        updateButtonActivity();
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
        editor.resetUndoManager();
        updateButtonActivity();
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
        editor.resetUndoManager();
        updateButtonActivity();
    }//GEN-LAST:event_openFileActionPerformed

    private void undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoActionPerformed
        editor.undo();
    }//GEN-LAST:event_undoActionPerformed

    private void UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoActionPerformed
        editor.undo();
    }//GEN-LAST:event_UndoActionPerformed

    private void RedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedoActionPerformed
        editor.redo();
    }//GEN-LAST:event_RedoActionPerformed

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
        editor.resetUndoManager();
    }//GEN-LAST:event_closeFileActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        editor.save();
        updateFileName();  
        updateButtonActivity();
    }//GEN-LAST:event_saveActionPerformed


    private void saveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsActionPerformed
        editor.saveAs();
        updateFileName();  
        updateButtonActivity();
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

    private void UndoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UndoMouseEntered
        Undo.setToolTipText("Undo");
    }//GEN-LAST:event_UndoMouseEntered

    private void RedoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RedoMouseEntered
        Redo.setToolTipText("Redo");
    }//GEN-LAST:event_RedoMouseEntered

    private void RunMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RunMouseEntered
        Run.setToolTipText("Run");
    }//GEN-LAST:event_RunMouseEntered

    private void RunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RunActionPerformed
          

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
                if (actionName.equals("Save") && !editor.hasUnsavedChanges()) {
                    // If there are no unsaved changes, don't perform the action
                    return;
                }
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
            case "Undo" -> UndoActionPerformed(event);
            case "Redo" -> RedoActionPerformed(event);
            case "New Window" -> newWindowActionPerformed(event);
            case "Open File" -> OpenActionPerformed(event);
            case "Copy" -> copyActionPerformed(event);
        }
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private packages.baby.components.SidebarBtn Home;
    private packages.baby.components.SidebarBtn Open;
    private packages.baby.components.SidebarBtn Redo;
    private packages.baby.components.SidebarBtn Run;
    private packages.baby.components.SidebarBtn Save;
    private packages.baby.components.SidebarBtn SaveAs;
    private packages.baby.components.SidebarBtn Undo;
    private javax.swing.JMenuItem closeFile;
    private javax.swing.JMenuItem copy;
    private javax.swing.JMenuItem cut;
    private packages.baby.components.CodeEditor editor;
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
