package org.example;

import org.example.Controller.PasswordGenerator;
import org.example.View.CommandLineReader;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Readable input = new InputStreamReader(System.in);
        Appendable output = new PrintStream(System.out);

        PasswordGenerator passwordGenerator = new PasswordGenerator(output, input, new Random());

        passwordGenerator.run();
    }
}