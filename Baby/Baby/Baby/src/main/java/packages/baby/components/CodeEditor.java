/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

public class CodeEditor extends JScrollPane{
    
    private CodeFile code;
    private JTextArea textArea;
    private LineNumber lineNumber;
    private UndoManager undoManager;
    

    public CodeEditor(){
        code = new CodeFile();
        textArea = new JTextArea();
        lineNumber = new LineNumber(textArea);
        undoManager = new UndoManager(); // Create an UndoManager
        
        initComponents();
        
        addDocumentListenerToTextArea();
        
        // Attach the UndoManager to the textArea's document
        textArea.getDocument().addUndoableEditListener(undoManager);
        textArea.setTabSize(2);

    }
    
    public void save(){
        code.save(textArea.getText());
    }
    
    public void saveAs(){
        code.saveAs(textArea.getText());
    }
    
    public void open(){
        
        String codeContent = code.open();
        if (codeContent != null) {
            textArea.setText(codeContent);
        }
        
        code.setUnsavedChanges(false);
    }
    
    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo(); // Perform undo through UndoManager
        }
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo(); // Perform redo through UndoManager
        }
    }
    
    public void close() {
        code.close();
        textArea.setText("");
    }
    
    public void copy() throws BadLocationException{
        if (isSelectionEmpty()){
            selectLineAtCaretPosition();
        }

        textArea.copy();
    }
    
    public void paste() {
        textArea.paste();
    }
    
    public void cut() throws BadLocationException{
        if (isSelectionEmpty()){
            selectLineAtCaretPosition();
        }
        textArea.cut();
    }
    
    public boolean isSelectionEmpty(){
        String selectedText = textArea.getSelectedText();
        return selectedText == null;
    }
    
    public void selectLineAtCaretPosition() throws BadLocationException {
        int caretPosition = textArea.getCaretPosition();
        int lineNumberClicked = textArea.getLineOfOffset(caretPosition);
        int lineStartOffset = textArea.getLineStartOffset(lineNumberClicked);
        int lineEndOffset = textArea.getLineEndOffset(lineNumberClicked);
        int lineLength = lineEndOffset - lineStartOffset;

        // Check if the line end includes a line break, and if so, adjust the selection
        if (lineLength > 0 && textArea.getText().charAt(lineEndOffset - 1) == '\n') {
            lineEndOffset--;
        }

        textArea.setSelectionStart(lineStartOffset);
        textArea.setSelectionEnd(lineEndOffset);
    }

        
    public boolean hasUnsavedChanges() {
        return code.hasUnsavedChanges();
    }

    public void setUnsavedChanges(boolean unsavedChanges) {
        code.setUnsavedChanges(unsavedChanges);
    }
    
    @SuppressWarnings("empty-statement")
    public String getFileName() {
        return code.getFileName();
    }
    
    public void initComponents(){
        
        // Create a scroll pane to contain both textArea and lineNumber
        setViewportView(textArea);
        setRowHeaderView(lineNumber);
        
        customizeCaret();
        customizeScrollPane(this);
        customizeLineNumber(lineNumber);
        customizeTextArea(textArea);
    }
    
    private void customizeCaret(){
        // Add a custom caret that sets the caret color
        CustomCaret customCaret = new CustomCaret(textArea);
        textArea.setCaret(customCaret);
    }
    
    private void addDocumentListenerToTextArea(){
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                lineNumber.updateLineNumbers();
                code.setUnsavedChanges(true);               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lineNumber.updateLineNumbers();               
                code.setUnsavedChanges(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lineNumber.updateLineNumbers();
                code.setUnsavedChanges(true);
            }
        });
    }
    
    private static void customizeScrollPane(JScrollPane scrollPane) {
        // Set a custom border with increased width and RGB color
        int borderWidth = 20; // Set the desired width of the border
        Color borderColor = new Color(31, 31, 31); 

        Border customBorder = BorderFactory.createMatteBorder(borderWidth, 0, 0, 0, borderColor);
        scrollPane.setBorder(customBorder);
    }
    
    private static void customizeLineNumber(JTextArea lineNumber) {
        lineNumber.setEditable(false);
        lineNumber.setMargin(new Insets(0, 0, 0, 0));
        lineNumber.setBackground(new Color(31, 31, 31));
        lineNumber.setColumns(3); // Adjust this as needed
        lineNumber.setFont(new Font("Arial", Font.PLAIN, 15));
        lineNumber.setForeground(new Color(62, 62, 62));
        
        int borderWidth = 20; // Set the desired width of the border
        Color borderColor = new Color(31, 31, 31); 

        Border customBorder = BorderFactory.createMatteBorder(0, 0, 0, borderWidth, borderColor);
        lineNumber.setBorder(customBorder);
        
        lineNumber.setText("1"); // Ensure line numbering starts with one
        lineNumber.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    private static void customizeTextArea(JTextArea textArea) {
        textArea.setMargin(new Insets(0, 0, 0, 0));
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setBackground(new Color(31, 31, 31));
    }
    
    class LineNumber extends JTextArea {
        private JTextArea textArea;
        private int lineCount;

        public LineNumber(JTextArea textArea) {
            this.textArea = textArea;
        }

        public void updateLineNumbers() {
            String text = textArea.getText();
            String[] lines = text.split("\n", -1);
            lineCount = lines.length;

            StringBuilder lineNumbers = new StringBuilder();

            for (int i = 1; i <= lineCount; i++) {
                lineNumbers.append(i).append("\n");
            }

            setText(lineNumbers.toString());
        }


    }

    class CustomCaret extends DefaultCaret {
        private JTextComponent textComponent;

        public CustomCaret(JTextComponent textComponent) {
            this.textComponent = textComponent;
        }

        @Override
        public void install(JTextComponent c) {
            super.install(c);

            // Set the caret color to red
            setBlinkRate(500); // Adjust the blink rate (if needed)
            setSelectionVisible(true); // Show selection when the caret is active
            setBlinkRate(500); // Adjust the blink rate (if needed)
            textComponent.setCaretColor(Color.WHITE);
        }

    }
}

