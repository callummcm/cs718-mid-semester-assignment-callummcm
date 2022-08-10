package App.BullsAndCows;
import App.BullsAndCowsApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAI extends Computer{

    public EasyAI() {
        super();
        this.secretCode = guessOrSetSecretCode();
    }

    @Override
    public String makeGuess() {
        return guessOrSetSecretCode();
    }

    @Override
    public String guessOrSetSecretCode() {
        Random random = new Random();
        int allowedCharacterAmount = BullsAndCowsApp.ALLOWED_CHARACTERS.size();

        ArrayList<Character> fourUniqueRandomNumbers = new ArrayList<>();
        String uniqueFourDigits = "";
        while (fourUniqueRandomNumbers.size()<BullsAndCowsApp.CODE_LENGTH){
            Character randomCharacter = (BullsAndCowsApp.ALLOWED_CHARACTERS.get(random.nextInt(allowedCharacterAmount)));
            if(!fourUniqueRandomNumbers.contains(randomCharacter)){
                fourUniqueRandomNumbers.add(randomCharacter);
                uniqueFourDigits += randomCharacter;
            }
        }
        return uniqueFourDigits;
    }
}
