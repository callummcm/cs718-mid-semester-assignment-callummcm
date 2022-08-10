package App.BullsAndCows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Player {

    private final List<Character> ALLOWED_CHARACTERS;
    private String playerSecretCode;
    private final int CODE_LENGTH;


    public Player(List<Character> ALLOWED_CHARACTERS, int CODE_LENGTH) {
        this.ALLOWED_CHARACTERS = ALLOWED_CHARACTERS;
        this.CODE_LENGTH = CODE_LENGTH;
    }

    public void setPlayerSecretCode() {
        System.out.println("Please enter your secret code: ");
        playerSecretCode = checkValid();
    }

    public String getPlayerSecretCode() {
        return this.playerSecretCode;
    }

    public String makeGuess() {
        System.out.println("You guess: ");
        return checkValid();
    }

    public String checkValid() {
        int allowedCharacterAmount = ALLOWED_CHARACTERS.size();
        boolean isUnique = false;
        String currentInput = "";
        firstLoop:
        while (!isUnique) {
            currentInput = "";

            while (currentInput.length() != CODE_LENGTH) {
                currentInput = Keyboard.readInput();
                if (currentInput.length() != CODE_LENGTH) {
                    System.out.println("Inputs length isn't four long, try again: ");
                }
            }

            Character[] currentCharsArray = new Character[CODE_LENGTH];
            List<Character> currentChars = Arrays.asList(currentCharsArray);

            for (int i = 0; i < currentInput.length(); i++) {
                if (currentChars.contains(currentInput.charAt(i)) || !ALLOWED_CHARACTERS.contains(currentInput.charAt(i))) {
                    System.out.println("Invalid input, please try again: ");
                    continue firstLoop;
                } else if (!currentChars.contains(currentInput.charAt(i)) && ALLOWED_CHARACTERS.contains(currentInput.charAt(i))) {
                    currentChars.set(i, currentInput.charAt(i));
                }
            }
            isUnique = true;
        }
        return currentInput;
    }

    public Scanner enterFileName() {
        String fileName;
        Scanner scanner = null;
        do {
            fileName = Keyboard.readInput();
            try {
                scanner = new Scanner(new File(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("Error! File not found, please enter again: ");
                fileName = null;
            }
        } while (fileName == null);
        return scanner;
    }
}
