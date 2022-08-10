package App.BullsAndCows;

import java.util.ArrayList;
import java.util.Random;
import static App.BullsAndCowsApp.getCows;
import static App.BullsAndCowsApp.getBulls;

public class HardAI extends Computer{

    private ArrayList<String> combinations = new ArrayList<>();
    private String secretCode;

    public HardAI() {
        super();
        this.secretCode = getSecretCode();
        makeCombinations("", "0123456789", 4);
    }

    public String makeIntelligentGuess() {
        Random random = new Random();
        return combinations.get(random.nextInt(combinations.size()));
    }

    public void makeCombinations(String currentCombination, String digitsUnused, int maxDigits) {
        if (currentCombination.length() == maxDigits) {
            combinations.add(currentCombination);
        }

        for (int i = 0; i < digitsUnused.length(); i++) {
            makeCombinations(currentCombination + digitsUnused.charAt(i), digitsUnused.substring(0, i) + digitsUnused.substring(i + 1), maxDigits);
        }
    }

    public void purgeCombinations(String currentGuess, int bulls, int cows) {
        if (bulls == 0 && cows == 0) {
            for (int i = 0; i < 4; i++) {
                String currentNumber = Character.toString(currentGuess.charAt(i));
                combinations.removeIf(number -> (number.contains(currentNumber)));
            }
        } else {
            combinations.removeIf(combination -> !((getBulls(combination, currentGuess) == bulls) && (getCows(combination, currentGuess) == cows)));
        }
    }

}
