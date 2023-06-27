package org.example.Controller;


import org.example.View.CommandLineReader;
import org.example.View.View;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class PasswordGenerator implements Controller {

    /*usage: xkcdpwgen [-h] [-w WORDS] [-c CAPS] [-n NUMBERS] [-s SYMBOLS]

    Generate a secure, memorable password using the XKCD method

    optional arguments:
    -h, --help            show this help message and exit
    -w WORDS, --words WORDS
                          include WORDS words in the password (default=4)
    -c CAPS, --caps CAPS  capitalize the first letter of CAPS random words
                          (default=0)
    -n NUMBERS, --numbers NUMBERS
                          insert NUMBERS random numbers in the password
                          (default=0)
    -s SYMBOLS, --symbols SYMBOLS
                          insert SYMBOLS random symbols in the password
                          (default=0)*/

    List<String> password;

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

    private Random random;


    //FIRST IS WHERE IT SHOULD GO, SECOND INTEGER IS THE ACTUAL NUMBER
    private Map<Integer, Integer> numberImplementation;

    private Map<Integer, String> characterImplementation;

    private List<String> allPossibleCharacters;

    /**
     * Constructor for UserDisplay
     *
     * @param out appendable to write output
     * @param in  readable to get input
     */
    public PasswordGenerator(Appendable out, Readable in, Random random) {
        this.password = new ArrayList<>();
        this.numberImplementation = new LinkedHashMap<>();
        this.characterImplementation = new LinkedHashMap<>();
        this.allPossibleCharacters = Arrays.asList("~", "!", "@", "#", "$", "%", "^", "&", "*", ".", ":", ";");
        this.out = out;
        this.in = in;
        this.scanner = new Scanner(this.in);
        this.random = random;
    }

    private void print() {
        for (int i = 0; i < password.size(); i++) {
            if (this.numberImplementation.containsKey(i)){
                System.out.print(this.numberImplementation.get(i));
            }
            if (this.characterImplementation.containsKey(i)){
                System.out.print(this.characterImplementation.get(i));
            }
            System.out.print(this.password.get(i));
        }
        System.out.println();
    }

    @Override
    public void run() {

        try {
            this.createRandPassword(4);
            this.print();
        } catch (IOException e) {
            System.err.println(e);
        }

        View view = new CommandLineReader(out, in);
        List<String> input = view.read();
        try {
            while (!input.contains("-x") && !input.contains("--exit")) {
                List<String> listOfCodes = input;
                this.delegateCodes(listOfCodes);
                input = view.read();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        scanner.close();
    }

    private void createRandPassword(int maxPasswords) throws IOException {
        this.password = new ArrayList<>();
        StringBuilder content = new StringBuilder();
        //creates scanner
        Scanner input = new Scanner(Path.of("src/main/resources/words.txt"));
        while (input.hasNextLine()) {
            content.append(input.nextLine()).append("\n");
        }
        String[] totalList = content.toString().split("\n");

        for (int i = 0; i < maxPasswords; i++) {
            String addToPassword = totalList[this.random.nextInt(0, 466550)].toLowerCase();
            this.password.add(addToPassword);
        }
    }

    private void delegateCodes(List<String> listOfCodes) throws IOException {
        if (listOfCodes.contains("-w") || listOfCodes.contains("--words")) {
            //include WORDS words in the password (default=4)
            int maxPasswords = 0;
            if (listOfCodes.contains("-w")) {
                maxPasswords = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("-w") + 1));
            } else {
                maxPasswords = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("--words") + 1));
            }
            this.createRandPassword(maxPasswords);
        } else {
            //the password will stay the same
        }
        if (listOfCodes.contains("-c") || listOfCodes.contains("--caps")) {
            //capitalize the first letter of CAPS random words
            //                          (default=0)
            int numOfUpCase = 0;
            if (listOfCodes.contains("-c")) {
                numOfUpCase = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("-c") + 1));
            } else {
                numOfUpCase = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("--caps") + 1));
            }
            for (int i = 0; i < numOfUpCase; i++) {
                int randomNumber = this.random.nextInt(this.password.size());
                String currentWord =  this.password.get(randomNumber);
                currentWord = currentWord.substring(0, 1).toUpperCase() + currentWord.substring(1);
                this.password.set(randomNumber, currentWord);
            }
        }
        if (listOfCodes.contains("-n") || listOfCodes.contains("-numbers")) {
            //insert NUMBERS random numbers in the password
            //                          (default=0)
            int numbers = 0;
            if (listOfCodes.contains("-n")) {
                numbers = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("-n") + 1));
            } else {
                numbers = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("--numbers") + 1));
            }
            for (int i = 0; i < numbers; i++) {
                int randomNumber = this.random.nextInt(10);
                int randomIndex = this.random.nextInt(this.password.size());
                if (this.numberImplementation.containsKey(randomIndex)) {
                    randomIndex = this.random.nextInt(this.password.size());
                }
                this.numberImplementation.put(randomIndex, randomNumber);
            }
        }
        if (listOfCodes.contains("-s") || listOfCodes.contains("-symbols")) {
            //insert SYMBOLS random symbols in the password
            //                          (default=0)*/
            //insert NUMBERS random numbers in the password
            //                          (default=0)
            int numbers = 0;
            if (listOfCodes.contains("-s")) {
                numbers = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("-s") + 1));
            } else {
                numbers = Integer.parseInt(listOfCodes.get(listOfCodes.indexOf("--symbols") + 1));
            }
            for (int i = 0; i < numbers; i++) {
                String randomCharacter = this.allPossibleCharacters.get(this.random.nextInt(10));
                int randomIndex = this.random.nextInt(this.password.size());
                if (this.numberImplementation.containsKey(randomIndex)) {
                    randomIndex = this.random.nextInt(this.password.size());
                }
                this.characterImplementation.put(randomIndex, randomCharacter);
            }
        }
        if (listOfCodes.contains("-h")) {
            System.out.println("""
                    usage: xkcdpwgen [-h] [-w WORDS] [-c CAPS] [-n NUMBERS] [-s SYMBOLS] [-x EXIT]

                        Generate a secure, memorable password using the XKCD method

                        optional arguments:
                        -x, --exit            exit the program !
                        -h, --help            show this help message and exit
                        -w WORDS, --words WORDS
                                              include WORDS words in the password (default=4)
                        -c CAPS, --caps CAPS  capitalize the first letter of CAPS random words
                                              (default=0)
                        -n NUMBERS, --numbers NUMBERS
                                              insert NUMBERS random numbers in the password
                                              (default=0)
                        -s SYMBOLS, --symbols SYMBOLS
                                              insert SYMBOLS random symbols in the password
                                              (default=0)""");
        }

        this.print();
    }


}
