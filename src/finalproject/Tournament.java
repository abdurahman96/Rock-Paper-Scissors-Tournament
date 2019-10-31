package finalproject;
// necessary imports
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

// this class represents a Tournament of the Game
public class Tournament {

    // players in this tournament
    ArrayList<Player> players;
    // current round of the tournament
    int round;
    // total players in this tournament
    int totalPlayers;
    // this Linked List stores the heirarchy of matches in this tournaments
    LinkedList<Player> branches;

    // constructor
    Tournament(ArrayList<Player> playersList) {
        setPlayers(playersList);
        totalPlayers = playersList.size();
        branches = new LinkedList<>();
        round = 0;
    }

    // set the players of this tournament
    private void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // randomly schedule matches for this tournament
    public void scheduleMatches() {
        
        Random random = new Random();
        // create match branches
        while(!players.isEmpty()) {
            int num = random.nextInt(players.size());
            branches.add(players.remove(num));
        }
        round = 1;
        // print the tournament schedule
        System.out.println("\nTournament Schedule");
        System.out.printf("Match 1 : %s v %s\n", branches.get(2), branches.get(3));
        System.out.printf("Match 2 : %s v %s\n", branches.get(0), branches.get(1));
        System.out.println("Match 3 : Winner of Match1 v Winner of Match2");
    }

    // start the tournament
    public Player start() {

        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        int counter = 0;
        // while the winner can not be decided start next match
        while (branches.size() > 1) {
            // match number
            System.out.printf("\nMatch # %d\n", ++counter);
            // fetch opponents
            players.add(branches.removeLast());
            players.add(branches.removeLast());
            // save the winner
            Player winner = game.match(players);
            branches.addFirst(winner);
            players.clear();
        }
        
        // tournament winner
        return branches.peek();
    }

}
