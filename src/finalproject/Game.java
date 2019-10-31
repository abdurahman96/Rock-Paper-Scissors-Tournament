package finalproject;
// necessary imports
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


// this class represents a Game which is the driver class of our application
public class Game {

    // Scanner object to take input from user
    static Scanner keyboard;

    
    /**
     * The main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        keyboard = new Scanner(System.in);
        // create an instance of the game
        Game game = new Game();
        // run the game.
        game.startGame();
    }

    // start the game
    private void startGame() {
        // temporary variable to store user input
        String userChoice;

        // take input from user. Namely The game type. Single or Tournament
        // repeat the process until user has entered a valid input
        do {
            System.out.print("Tournament or Single Game?\b(T = Tournament & S = Single Game)\t? : ");
            userChoice = keyboard.nextLine();
        } while (!userChoice.equalsIgnoreCase("T")
                && !userChoice.equalsIgnoreCase("S"));
        
        // if the game is Single Game
        if (userChoice.equalsIgnoreCase("S")) {
            
            int totalPlayers;
            // take input from user. Namely the number of players in this game
            // repeat the process until user has entered a valid input
            do {
                System.out.print("Enter Number of Players per game: ");
                totalPlayers = keyboard.nextInt();
            } while (totalPlayers <= 0);
            // skip the next line character
            keyboard.nextLine();
            
            ArrayList<Player> players = new ArrayList<>();
            // get the name of human player
            System.out.print("Enter name of your player: ");
            String name = keyboard.nextLine();
            players.add(new Player(name, true));
            // create the rest of players as Computer Players
            for (int i = 1; i < totalPlayers; i++) {
                players.add(new Player(String.format("ComputerPlayer # %d", i), false));
            }
            // start the match between the players
            Player winner = match(players);
            // print the winner
            System.out.printf("Winner is : %s\n", winner.getName());

        } else {
            // fixed number of players per match
            int players = 2;
            // fixes number of total participants in the tournaments
            int participants = 4;

            // create and start the tournament
            Player winner = tournament(participants, players);
            // print the winner of this tournament
            System.out.printf("Tournament Winner : %s\n", winner.getName());

        }
    }

    // create the tournament from given number of participants
    private Player tournament(int participants, int players) {
        
        ArrayList<Player> playersList = new ArrayList<>();
        // get the name of human player
        System.out.print("Enter name of your player: ");
        String name = keyboard.nextLine();
        playersList.add(new Player(name, true));
        // create the rest of players as computer players
        for (int i = 1; i < participants; i++) {
            playersList.add(new Player(String.format("ComputerPlayer # %d", i), false));
        }

        // create the tournament
        Tournament tournament = new Tournament(playersList);
        // schedule the tournament
        tournament.scheduleMatches();

        // start the tournament
        return tournament.start();
    }

    // simulate a match between the list of players provided
    public Player match(ArrayList<Player> players) {
        // while there are more players than one 
        while (players.size() != 1) {
            System.out.println("\n");
            for (int i = 0; i < players.size(); i++) {
                Player tempPlayer = players.get(i);
                // turn of human player
                if (tempPlayer.isHuman()) {
                    tempPlayer.setSign(getUserSymbol());
                } 
                // turn of computer player
                else {
                    tempPlayer.setSign(getComputerSymbol());

                }
                // print the choice of each player
                System.out.printf("%s chooses %s\n", tempPlayer.getName(), tempPlayer.getSign());
            }
            // decide winners and lossers for this turn
            matchup(players);
            // remove lossers from game
            for (int i = 0; i < players.size(); i++) {
                Player tempPlayer = players.get(i);
                if (tempPlayer.haveLost()) {
                    players.remove(tempPlayer);
                    System.out.printf("%s is out of game. Sign = %s\n", tempPlayer.getName(), tempPlayer.getSign());
                    i = -1;
                }
            }
        }
        // return the winner of this game
        return players.get(0);
    }

    // this method contains the logic of game to decide the winners and lossers
    private void matchup(ArrayList<Player> players) {
        // if there are only two players
        if (players.size() == 2) {
            Player p1 = players.get(0);
            Player p2 = players.get(1);

            // if both players have same choices, game is draw
            if (p1.getSign().equalsIgnoreCase(p2.getSign())) {
                p1.setGameStatus(0);
                p2.setGameStatus(0);
            } else if (p1.getSign().equalsIgnoreCase("R")) {
                // Paper folds the rock
                if (p2.getSign().equalsIgnoreCase("P")) {
                    p2.setGameStatus(1);
                    p1.setGameStatus(-1);
                }
                // Rock crashes the scissors
                else if (p2.getSign().equalsIgnoreCase("S")) {
                    p1.setGameStatus(1);
                    p2.setGameStatus(-1);
                }
            } else if (p1.getSign().equalsIgnoreCase("P")) {
                // Paper folds the rock
                if (p2.getSign().equalsIgnoreCase("R")) {
                    p1.setGameStatus(1);
                    p2.setGameStatus(-1);
                } 
                // Scissors cuts the paper
                else if (p2.getSign().equalsIgnoreCase("S")) {
                    p2.setGameStatus(1);
                    p1.setGameStatus(-1);
                }
            } else if (p1.getSign().equalsIgnoreCase("S")) {
                // Rock crashes the scissors
                if (p2.getSign().equalsIgnoreCase("R")) {
                    p2.setGameStatus(1);
                    p1.setGameStatus(-1);
                }
                // Scissors cuts the paper
                else if (p2.getSign().equalsIgnoreCase("P")) {
                    p1.setGameStatus(1);
                    p2.setGameStatus(-1);
                }
            }
        } 
        // if there are more than two players
        else {
            // check if all players have choosen same choice
            String sign = players.get(0).getSign();
            boolean allEqual = true;
            for (int i = 1; allEqual && i < players.size(); i++) {
                if (!players.get(i).getSign().equalsIgnoreCase(sign)) {
                    allEqual = false;
                }
            }
            // if all players have choosen same choice the game is a draw
            if (allEqual) {
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setGameStatus(0);
                }
            } 
            // else the majority wins
            else {
                // count Rocks, papers and Scissors
                int r = 0, p = 0, s = 0;
                for (int i = 0; i < players.size(); i++) {
                    switch (players.get(i).getSign().toLowerCase()) {
                        case "r":
                            r++;
                            break;
                        case "p":
                            p++;
                            break;
                        case "s":
                            s++;
                            break;
                        default:
                            break;
                    }
                }
                System.out.printf("Rock : %d, Paper: %d, Scissors: %d\n", r, p, s);
                
                // determine which choice was choosen by most of the players
                int max = r;
                if (p > max) {
                    max = p;
                }
                if (s > max) {
                    max = s;
                }

                // suppose all players have lost the game.
                // then we will set the gameStatus as "1=Win" of those player
                // who have choosen a choice which was repeated most
                // this is to filter the lossers and winners
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setGameStatus(-1);
                }

                // if "Rock" was choosen by maximum players
                if (max == r) {
                    for (int i = 0; i < players.size(); i++) {
                        Player player = players.get(i);
                        // declare the players who have choosen "Rock" as winners
                        if (player.getSign().equalsIgnoreCase("r")) {
                            players.get(i).setGameStatus(1);
                        }
                    }
                }
                // if "Paper" was choosen by maximum players
                if (max == p) {
                    for (int i = 0; i < players.size(); i++) {
                        Player player = players.get(i);
                        // declare the players who have choosen "Paper" as winners
                        if (player.getSign().equalsIgnoreCase("p")) {
                            players.get(i).setGameStatus(1);
                        }
                    }
                }
                // if "Scissors" was choosen by maximum players
                if (max == s) {
                    for (int i = 0; i < players.size(); i++) {
                        Player player = players.get(i);
                        // declare the players who have choosen "Scissors" as winnerss
                        if (player.getSign().equalsIgnoreCase("s")) {
                            players.get(i).setGameStatus(1);
                        }
                    }
                }

            }
        }

    }

    // get the choice of user
    private String getUserSymbol() {
        String symbol;
        do {
            System.out.print("Enter your symbol: ");
            symbol = keyboard.nextLine();
        } while (!symbol.equalsIgnoreCase("r") && !symbol.equalsIgnoreCase("p")
                && !symbol.equalsIgnoreCase("s"));
        return symbol;
    }

    // select randomly and return the choice of a computer player
    private String getComputerSymbol() {
        int num;
        Random randomFactory = new Random();
        num = randomFactory.nextInt(3);
        String sign = "";
        switch (num) {
            case 0:
                sign = "r";
                break;
            case 1:
                sign = "p";
                break;
            case 2:
                sign = "s";
                break;
            default:
                break;
        }
        return sign;
    }
}
