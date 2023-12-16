package packages.baby.compiler.CodeGenerator;

import java.io.*;

public class CompilerTerminalIntegration {
    private Process spimProcess;
    private OutputStream spimInput;
    private InputStream spimOutput;

    public void runMIPSFile(String filePath) throws IOException, InterruptedException {
        String qtspimPath = "C:\\Program Files (x86)\\QtSpim\\QtSpim.exe"; // Adjust if necessary!!
        ProcessBuilder processBuilder = new ProcessBuilder(qtspimPath, filePath);

        // if you want to use MARS compiler
        // String qtspimPath = "C:\\Users\\Acer\\Documents\\Mars4_5.jar"; // Adjust if necessary!!
        // ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", qtspimPath, filePath); //Adjust if necessary!


        spimProcess = processBuilder.start();
        spimInput = spimProcess.getOutputStream();
        spimOutput = spimProcess.getInputStream();

        // try (Writer writer = new OutputStreamWriter(spimInput)) {
        //     // Send user input if needed
        //     writer.write(userInput);
        // }

        // Read and print the output in the current terminal
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(spimOutput))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // Wait for the completion of the MIPS compiler
        int exitCode = spimProcess.waitFor();
        if (exitCode != 0) {
            System.err.println("Error: MIPS compilation failed with exit code " + exitCode);
        }

        // Close the resources
        close();
    }

    public void close() {
        if (spimProcess != null) {
            spimProcess.destroy();
        }
    }
}
