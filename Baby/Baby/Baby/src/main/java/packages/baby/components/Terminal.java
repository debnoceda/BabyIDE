/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.components;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

/**
 *
 * @author liaminakigillamac
 */
public class Terminal extends JScrollPane{
    private static Color borderColor = new Color(31, 31, 31);
    private static int borderWidth = 20;
    
    private JTextField terminalHeader;
    private JTextArea textArea;
    
    public Terminal(){
        textArea = new JTextArea();
        terminalHeader = new JTextField();
        initComponents();
    }
    
    public void setText(String output){
        textArea.setText(output);
    }

    public String getText(){
        return textArea.getText();
    }
    
    public void initComponents(){
        
       
        // Create a scroll pane to contain both textArea and lineNumber
        setViewportView(textArea);
        setColumnHeaderView(terminalHeader);
                
        customizeCaret();
        customizeScrollPane(this);
        customizeTextArea(textArea);
        customizeTerminalHeader(terminalHeader);
    }
    
    private void customizeCaret(){
        // Add a custom caret that sets the caret color
        CustomCaret customCaret = new CustomCaret(textArea);
        textArea.setCaret(customCaret);
    }
    
    private static void customizeScrollPane(JScrollPane scrollPane) {
        // Set a custom border with increased width and RGB color
        int borderWidth = 20; // Set the desired width of the border
        Color borderColor = new Color(31, 31, 31); 

        Border customBorder = BorderFactory.createMatteBorder(borderWidth, 0, 0, 0, borderColor);
        scrollPane.setBorder(customBorder);
    }

    private static void customizeTextArea(JTextArea textArea) {
        textArea.setMargin(new Insets(0, 10, 0, 10));
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setBackground(new Color(31, 31, 31));
                   
        Border customBorder = BorderFactory.createMatteBorder(0, 20, 0, 0, borderColor);
        textArea.setBorder(customBorder);
    }
    
    private static void customizeTerminalHeader(JTextField terminalHeader) {
        terminalHeader.setMargin(new Insets(0, 10, 0, 10));
        terminalHeader.setFont(new Font("Arial", Font.PLAIN, 14));
        terminalHeader.setForeground(new Color(51, 51, 51));
        terminalHeader.setText("TERMINAL");
        terminalHeader.setBackground(new Color(31, 31, 31));
//        terminalHeader.setBorder(null);
        terminalHeader.setEditable(false);
        
        Border customBorder = BorderFactory.createMatteBorder(0, borderWidth, borderWidth/2, 0, borderColor);
        terminalHeader.setBorder(customBorder);
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
