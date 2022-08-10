package App.BullsAndCows;

import java.util.ArrayList;

public abstract class Computer {
    protected String secretCode;

    public Computer() {
        this.secretCode = guessOrSetSecretCode();

    }

    public String getSecretCode() {
        return secretCode;
    }

    public String makeGuess() {
        return null;
    }

    public String guessOrSetSecretCode() {
        ArrayList<Integer> fourUniqueRandomNumbers = new ArrayList<>();
        int maxItems = 4;
        String uniqueFourDigits = "";
        while (fourUniqueRandomNumbers.size()<maxItems){
            int randomCharacter = (int)(Math.random() * 9);
            if(!fourUniqueRandomNumbers.contains(randomCharacter)){
                fourUniqueRandomNumbers.add(randomCharacter);
                uniqueFourDigits += randomCharacter;
            }
        }
        return uniqueFourDigits;
    }
}
