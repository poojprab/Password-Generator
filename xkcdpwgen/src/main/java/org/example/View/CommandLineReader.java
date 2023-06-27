package org.example.View;

import java.io.IOException;
import java.util.*;

public class CommandLineReader implements View {

    /**
     * Appendable to print output
     */
    final Appendable out;
    /**
     * Readable to take input
     */
    final Readable in;

    /**
     * Scanner to read input
     */
    private final Scanner scanner;


    /**
     * Constructor for UserDisplay
     *
     * @param out appendable to write output
     * @param in  readable to get input
     */
    public CommandLineReader(Appendable out, Readable in) {
        this.out = out;
        this.in = in;
        this.scanner = new Scanner(this.in);
    }

    @Override
    public List<String> read() {
        String allCommands = "";
        if (scanner.hasNextLine()) {
            // If we want to read, add to the output
            allCommands = (scanner.nextLine());
        }
        return Arrays.asList(Arrays.asList(allCommands.split("\n")).get(0).split(" "));
        //get the user input and put it into an array list with each command
    }
}
