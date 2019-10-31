package finalproject;
// this class represents a player of the game

public class Player {
    // name of the player
    private String name;
    // sign the player selects in a game turn .i.e. R, P or S
    private String sign;
    // this tells whether the player is computer or human
    private boolean isHuman;
    // this tells whether the player lost, won or draw a game turn against his opponents
    /*
        0 = Draw
        1 = Won
        -1 = Lost
    */
    private int win;

    
    // constructor
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        win = 0;
        sign = "";
                
    }

    // this method tells that whether the player is human or computer
    public boolean isHuman() {
        return isHuman;
    }
    
    // this method returns the name of the player
    public String getName() {
        return name;
    }

    // this method returns the sign of the player
    public String getSign() {
        return sign;
    }

    // this method sets the sign of the player
    public void setSign(String sign) {
        this.sign = sign;
    }

    // this method tells whether the player has lost or not
    public boolean haveLost() {
        return win == -1;
    }
    
    // this method tells the current status of the player in the game
    /*
        0 = Draw
        1 = Won
        -1 = Lost
    */
    public int getGameStatus(){
        return win;
    }

    // this method sets the current status of the player in the game
    /*
        0 = Draw
        1 = Won
        -1 = Lost
    */
    public void setGameStatus(int winStatus){
        win = winStatus;
    }
    
    // string representation of the player
    @Override
    public String toString(){
        return name;
    }
    
}
