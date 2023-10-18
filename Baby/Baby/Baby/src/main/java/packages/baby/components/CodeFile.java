package packages.baby.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author liaminakigillamac
 */
public class CodeFile {

    private boolean unsavedChanges = false;
    private File savedFile = null; // Determine if previously saved
    
    public CodeFile() {
    }
    
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean unsavedChanges) {
        this.unsavedChanges = unsavedChanges;
    }

    public String open() {
        
        StringBuilder content = new StringBuilder();

        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();

        // Apply a filter to restrict file selection to .bby files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.bby)", "bby");
        fileChooser.setFileFilter(filter);

        // Show the open file dialog
        int returnValue = fileChooser.showOpenDialog(null);

        // If a file is selected and 'Open' is chosen
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file

            File selectedFile = fileChooser.getSelectedFile();

            try {

                // Read and display the contents of the selected file
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                
                reader.close();

                // Set the savedFile variable to the selected file with .bby extension
                savedFile = fileChooser.getSelectedFile();
                String fileName = savedFile.getAbsolutePath();

                if (!fileName.endsWith(".bby")) {
                    fileName += ".bby";
                    savedFile = new File(fileName);
                }
                
                return content.toString();
                                            
            } catch (IOException e) {
                // Display an error message if file opening fails
                JOptionPane.showMessageDialog(null, "Error opening file: " + e.getMessage());
            }
        }
        
        return null;
    }
    
    public void saveAs(String codeContent){
        
        
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
                writer.write(codeContent);
                writer.close();
                JOptionPane.showMessageDialog(null, "File saved successfully.");
                savedFile = new File(fileName);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
            }
        }
        unsavedChanges = false;
    }
    
    public void save(String codeContent){
        if(savedFile == null)
            saveAs(codeContent);
        else{
            try {
                FileWriter writer = new FileWriter(savedFile);
                writer.write(codeContent);
                writer.close();
                unsavedChanges = false;
                JOptionPane.showMessageDialog(null, "File saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
            }
        }
            
    }
    




}
