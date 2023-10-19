/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.components;

import javax.swing.JTextArea;

/**
 *
 * @author liaminakigillamac
 */
public class LineNumber extends JTextArea {
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
