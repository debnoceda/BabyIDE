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
    private String currentFileName;
    private String currentPath;
    
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
        boolean opened = false; // Flag to track whether a file was successfully opened

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
                
                opened = true; // File was successfully opened
                currentFileName = selectedFile.getName(); // Set the currentFileName to be used in getFileName()
                currentPath = selectedFile.getAbsolutePath(); // Set the currentPath to be used in getFilePath()
                
                return content.toString();
                                            
            } catch (IOException e) {
                // Display an error message if file opening fails
                JOptionPane.showMessageDialog(null, "Error opening file: " + e.getMessage());
            }
        }
        
        if (!opened) {
            unsavedChanges = true;
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
            
            if (selectedFile.exists()) {
                int result = JOptionPane.showConfirmDialog(null,
                        "A file with the same name already exists. Do you want to replace it?",
                        "File Overwrite Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.NO_OPTION) {
                    unsavedChanges = true;
                    
                    // User chose not to overwrite the existing file
                    return;
                }

            }   


            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(codeContent);
                writer.close();
                JOptionPane.showMessageDialog(null, "File saved successfully.");
                currentFileName = selectedFile.getName(); // Set the currentFileName to be used in getFileName()
                currentPath = selectedFile.getAbsolutePath(); // Set the currentPath to be used in getFilePath()
                savedFile = new File(fileName);
                unsavedChanges = false;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
            }
        }
        
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
    
    public void close(){
        // Clear references to the current file
        savedFile = null;

        // Mark that there are no unsaved changes
        unsavedChanges = false;
        currentFileName = getDefaultName();
        currentPath = "";
    }
    
    public String getDefaultName(){
        
        String defaultName = "Untitled.bby";
        //if in tab, there exists, another tab with the name "BabyI" where I defaults to 1 and increments if it finds a tab with same name
        //else-
        
        return defaultName;
    }
    
    public String getFileName(){
         
        if (currentFileName == null){
            return getDefaultName();
        }
        else {
            return currentFileName;
        }
    }
    
    public String getFilePath(){
        
        if (currentPath == null){
            return null;
        }
        else {
            return currentPath;
        }
 
    }


}
