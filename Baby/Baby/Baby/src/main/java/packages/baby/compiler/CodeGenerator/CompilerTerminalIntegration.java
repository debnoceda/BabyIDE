package packages.baby.compiler.CodeGenerator;

import java.io.*;

public class CompilerTerminalIntegration {
    private Process spimProcess;
    private OutputStream spimInput;
    private InputStream spimOutput;

    public void startQtSPIM(String filePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("path/to/qtspim", filePath);
        spimProcess = processBuilder.start();
        spimInput = spimProcess.getOutputStream();
        spimOutput = spimProcess.getInputStream();
    }

    public void sendMIPSCode(String code) throws IOException {
        try (Writer writer = new OutputStreamWriter(spimInput)) {
            writer.write(code);
        }
    }

    public void readOutput() {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(spimOutput))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Process the output as needed
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendUserInput(String userInput) throws IOException {
        try (Writer writer = new OutputStreamWriter(spimInput)) {
            writer.write(userInput);
        }
    }

    public int waitForCompletion() throws InterruptedException {
        return spimProcess.waitFor();
    }

    public void close() {
        if (spimProcess != null) {
            spimProcess.destroy();
        }
    }

    public static void main(String[] args) {
        try {
            CompilerTerminalIntegration compilerIntegration = new CompilerTerminalIntegration();
            compilerIntegration.startQtSPIM("your_mips_file.asm");

            // Example: Sending MIPS code
            compilerIntegration.sendMIPSCode("Your MIPS assembly code here\n");

            // Example: Reading output in a separate thread
            compilerIntegration.readOutput();

            // Example: Sending user input
            compilerIntegration.sendUserInput("User input here");

            // Example: Waiting for the completion of the MIPS compiler
            int exitCode = compilerIntegration.waitForCompletion();

            // Close the resources
            compilerIntegration.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
