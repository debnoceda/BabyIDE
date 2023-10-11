package packages.baby.components;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextArea extends JTextArea {
    
    private boolean unsavedChanges = false;
    
    public TextArea() {
        initComponents();
        addDocumentListenerToTextArea();
    }
    
    private void initComponents() {
        setBackground(new java.awt.Color(31, 31, 31));
        setColumns(20);
        setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        setForeground(new java.awt.Color(255, 255, 255));
        setRows(5);
        setBorder(null);
    }
    
    private void addDocumentListenerToTextArea() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setUnsavedChanges(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setUnsavedChanges(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setUnsavedChanges(true);
            }
        });
    }

    public boolean isUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean unsavedChanges) {
        this.unsavedChanges = unsavedChanges;
    }
}
