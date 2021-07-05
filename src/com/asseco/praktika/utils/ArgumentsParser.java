package com.asseco.praktika.utils;

public class ArgumentsParser {

    private String inputFileName;
    private String outputFileName;
    private String outputFileType;

    public ArgumentsParser(String[] args) {
        if ((args != null) && (args.length >= 3)) {
            this.inputFileName = args[0];
            this.outputFileName = args[1];
            this.outputFileType = args[2];
        } else {
            System.out.println("Invalid user input");
            System.exit(1);
        }
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getOutputFileType() {
        return outputFileType;
    }
}
