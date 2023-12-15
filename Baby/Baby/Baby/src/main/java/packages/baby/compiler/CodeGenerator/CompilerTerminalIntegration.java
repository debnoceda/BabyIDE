package packages.baby.compiler.CodeGenerator;

import java.io.*;

public class CompilerTerminalIntegration {
    private Process spimProcess;
    private OutputStream spimInput;
    private InputStream spimOutput;

    public void runMIPSFile(String filePath) throws IOException, InterruptedException {
        String qtspimPath = "C:\\Program Files (x86)\\QtSpim\\QtSpim.exe";
        ProcessBuilder processBuilder = new ProcessBuilder(qtspimPath, filePath);
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

    // public static void main(String[] args) {
    //     try {
    //         CompilerTerminalIntegration compilerIntegration = new CompilerTerminalIntegration();
    //         // Specify the path to your MIPS assembly file
    //         String mipsFilePath = "your_mips_file.asm";

    //         // Optional: Provide user input if needed
    //         String userInput = "User input here";

    //         // Run the MIPS file and print output in the terminal
    //         compilerIntegration.runMIPSFile(mipsFilePath, userInput);
    //     } catch (IOException | InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }
}
