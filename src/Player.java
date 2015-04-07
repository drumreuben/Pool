/**
 * Created by Reuben on 4/6/2015.
 * players of the pool game
 */
public class Player {

    final static int SOLIDS = 0;
    final static int STRIPES = 1;

    String name;
    int points;
    int type;

    public Player(String name) {
        this.name = name;
    }
}
