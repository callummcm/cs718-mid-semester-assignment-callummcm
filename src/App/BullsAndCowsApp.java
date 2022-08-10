package App;

import App.BullsAndCows.*;

import java.io.*;
import java.util.*;
import java.util.List;

public class BullsAndCowsApp {


    //SET THE ALLOWED CHARACTERS FOR INPUT HERE (AFFECTS BOTH EASY AI AND PLAYER INPUTS)
    private static final Character[] ALLOWED_CHARACTER_ARRAY = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    public static final List<Character> ALLOWED_CHARACTERS = Arrays.asList(ALLOWED_CHARACTER_ARRAY);
    public static final int CODE_LENGTH = 4;

    //Sets lists up for saving the game to a text file at the end of the game
    private List<String> playerGuessHistory = new ArrayList<>();
    private List<String> playerBullsHistory = new ArrayList<>();
    private List<String> playerCowsHistory = new ArrayList<>();
    private List<String> computerGuessHistory = new ArrayList<>();
    private List<String> computerBullsHistory = new ArrayList<>();
    private List<String> computerCowsHistory = new ArrayList<>();
    private String playerCode;
    private String computerCode;
    private boolean didPlayerWin;
    private boolean didComputerWin;

    public void start() {

        System.out.println("Welcome to Bulls and Cows.");
        System.out.println("Would you like your guesses to be read from a supplied text file? (1 for yes, 2 for no)");
        askReadFile();

        System.out.println("Would you like your game log to be saved to a text file? (1 for yes, 2 for no)");
        askSaveToFile();

        }

    public void askReadFile() {
        String readFile = Keyboard.readInput();

        switch (readFile) {
            case "1":
                System.out.println("Guesses will now be read from the supplied text file.");
                selectAI(true);
                break;
            case "2":
                System.out.println("Game will proceed as normal.");
                selectAI(false);
                break;
            default:
                System.out.println("Error in selected option. Please try again.");
                askReadFile();
        }
    }


    public void selectAI(boolean doReadFile) {
        System.out.println("Please select which AI you'd like to vs. (1, 2 or 3)");
        System.out.println("1. EasyAI");
        System.out.println("2. MediumAI");
        System.out.println("3. HardAI");

        String selectAI = Keyboard.readInput();

        switch (selectAI) {
            case "1":
                Computer easyAI = new EasyAI();
                System.out.println("---");
                System.out.println("Easy AI has been selected, game will now begin");
                System.out.println("---");
                startGame(easyAI, doReadFile);
                break;
            case "2":
                Computer mediumAI = new MediumAI();
                System.out.println("---");
                System.out.println("Medium AI has been selected, game will now begin");
                System.out.println("---");
                startGame(mediumAI, doReadFile);
                break;
            case "3":
                Computer hardAI = new HardAI();
                System.out.println("---");
                System.out.println("Hard AI has been selected, game will now begin");
                System.out.println("---");
                startGame(hardAI, doReadFile);
                break;
            default:
                System.out.println("AI was not selected properly.");
                System.out.println("---");
                selectAI(doReadFile);
        }
    }

    public void startGame(Computer AI, boolean doReadFile) {
        Player player = new Player(ALLOWED_CHARACTERS, CODE_LENGTH);
        player.setPlayerSecretCode();
        boolean checkPlayerWon = false;
        boolean checkComputerWon = false;
        int playerGuesses = 0;
        int computerGuesses = 0;
        Scanner scanner = null;

        if (doReadFile) {
            System.out.println("Enter file name to read guesses from (default would be 'readGuesses.txt' as supplied): ");
            scanner = player.enterFileName();
        }

        while (!checkPlayerWon && !checkComputerWon) {

            System.out.println("----");

            playerGuesses++;
            String playerGuess = playerMakeGuess(doReadFile, player, scanner);
            playerGuessHistory.add(playerGuess);
            checkPlayerWon = getPlayerResult(playerGuess, AI);
            if (checkPlayerWon) {
                System.out.println("You won in "+playerGuesses+" guesses!");
                didPlayerWin = true;
                break;
            }

            computerGuesses++;
            String computerGuess = computerGuess(AI, player);
            computerGuessHistory.add(computerGuess);
            System.out.println();
            System.out.println("The computer guesses: " + computerGuess);
            checkComputerWon = getComputerResult(computerGuess, player);
            if (checkComputerWon) {
                System.out.println("The computer won in "+computerGuesses+" guesses :(");
                didComputerWin = true;
                break;
            }
            if (playerGuesses == 7) {
                System.out.println("The maximum guesses of 7 has been reached. The game ends in a draw.");
                break;
            }
        }
    }

    public String playerMakeGuess(boolean doReadFile, Player player, Scanner scanner) {
        String playerGuess;

        if (doReadFile && scanner.hasNextLine()) {
            playerGuess = scanner.nextLine();
            System.out.println("Player guessed from file: " + playerGuess);
        } else {
            playerGuess = player.makeGuess();
        }
        return playerGuess;
    }

    public boolean getPlayerResult(String playerGuess, Computer AI) {

        boolean playerGuessed;
        playerGuessed = displayBullsAndCows(playerGuess, AI.getSecretCode());
        playerCowsHistory.add(Integer.toString(getCows(playerGuess, AI.getSecretCode())));
        playerBullsHistory.add(Integer.toString(getBulls(playerGuess, AI.getSecretCode())));
        return playerGuessed;
    }

    public boolean getComputerResult(String computerGuess, Player player) {

        boolean computerGuessed;
        computerGuessed = displayBullsAndCows(computerGuess, player.getPlayerSecretCode());
        computerCowsHistory.add(Integer.toString(getCows(computerGuess, player.getPlayerSecretCode())));
        computerBullsHistory.add(Integer.toString(getBulls(computerGuess, player.getPlayerSecretCode())));
        return computerGuessed;
    }

    public String computerGuess(Computer AI, Player player) {
        if (AI instanceof HardAI) {
            String computerGuess = ((HardAI) AI).makeIntelligentGuess();
            int bulls = getBulls(computerGuess, player.getPlayerSecretCode());
            int cows = getCows(computerGuess, player.getPlayerSecretCode());
            ((HardAI) AI).purgeCombinations(computerGuess, bulls, cows);
            return computerGuess;
        } else {
            return AI.makeGuess();
        }
    }

    public boolean displayBullsAndCows(String guess, String secretCode) {

        int bulls = getBulls(guess, secretCode);
        int cows = getCows(guess, secretCode);

        if (bulls == 4) {
            System.out.println("Result: "+bulls+ " Bulls and "+cows+" Cows.");
            return true;
        } else {
            System.out.println("Result: "+bulls+ " Bulls and "+cows+" Cows.");
        }
        return false;
    }

    public static int getBulls(String guess, String secretCode) {
        int bulls = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            }
        }

        return bulls;
    }

    public static int getCows(String guess, String secretCode) {
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (secretCode.contains(guess.charAt(i)+"") && !(guess.charAt(i) == secretCode.charAt(i))) {
                cows++;
            }
        }

        return cows;
    }

    public void askSaveToFile() {
        String saveToFile = Keyboard.readInput();

        switch (saveToFile) {
            case "1":
                System.out.println("Game log will now be saved to the text file: ");
                saveGameToFile();
                break;
            case "2":
                System.out.println("Good game.");
                break;
            default:
                System.out.println("Error in selected option. Please try again.");
                askSaveToFile();
        }
    }

    private void saveGameToFile() {

        System.out.println("Enter desired file name to save the game output to: ");
        String fileName = Keyboard.readInput();
        File gameResultFile = new File(fileName);

        try {
            PrintStream output = new PrintStream(gameResultFile);

            output.println("Bulls and Cows game result.");
            output.println("Your code: " + playerCode);
            output.println("Computer code: " + computerCode);
            output.println("---");

            for (int turnNumber = 0; turnNumber < playerGuessHistory.size(); turnNumber++) {
                int actualTurnNumber = turnNumber+1;
                output.println("Turn " + (actualTurnNumber) + ":");
                output.println("You guessed " + playerGuessHistory.get(turnNumber) + ", scoring " + playerBullsHistory.get(turnNumber) + " bull and " + playerCowsHistory.get(turnNumber) + " cows");
                if (playerBullsHistory.get(turnNumber).equals("4")) {
                    output.println("You win! :)");
                    break;
                }
                output.println("Computer guessed " + computerGuessHistory.get(turnNumber) + ", scoring " + computerBullsHistory.get(turnNumber) + " bull and " + computerCowsHistory.get(turnNumber) + " cows");
                if (computerBullsHistory.get(turnNumber).equals("4")) {
                    output.println("You lost :(");
                    break;
                }
                output.println("---");
                }
            if (!didPlayerWin && !didComputerWin) {
                output.println("Game ended in a draw.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String [] args) {
        BullsAndCowsApp app = new BullsAndCowsApp();
        app.start();
    }
}


