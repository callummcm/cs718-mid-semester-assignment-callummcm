package App.BullsAndCows;

import java.util.ArrayList;

public class MediumAI extends Computer{

    private ArrayList<String> previousGuesses = new ArrayList<>();

    public MediumAI() {
        super();
        this.secretCode = getSecretCode();
    }

    @Override
    public String makeGuess() {
        String currentGuess = guessOrSetSecretCode();

        while (previousGuesses.contains(currentGuess)) {
            System.out.println(currentGuess);
            currentGuess = guessOrSetSecretCode();
        }

        previousGuesses.add(currentGuess);

        return currentGuess;
    }
}
