/**
 * @author (Kyle Peavot)
 * @version (30 / 10 / 18)
 * Written in intelliJ IDEA
 */
public class Monster {
    private int[] monsterPosition; //xy position of monster on the board
    private int score; //monster's score
    private String name; //monster's name

    public Monster(int[] monsterXY, String monstName)
    {
        monsterPosition = monsterXY;
        score = 0; //score always starts at 0
        name = monstName;
    }

    /**
     * adds monster to the board
     */
    public void addMonster(Board board)
    {
        board.setBoard(this.monsterPosition, this.name);
        board.addToMonsters(this);
    }

    /**
     * calls launchAttack() to get position of monster to attack OR get monster to attack
     * increments points
     * removes monster from monsters
     * updates board
     */
    public void attackMonster(Board board)
    {
        Monster monstToDie;
        if ((monstToDie = board.launchAttack(this.monsterPosition)) != null) {
            board.setBoard(monstToDie.getMonsterPosition(), "*"); //removes monster from board
            board.removeFromMonsters(monstToDie); // removes monster from monsters

            this.score++;
        } else System.out.println("This monster can't attack anyone");
    }

    /**
     * getter for score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * getter for name
     */
    public String getName()
    {
        return name;
    }

    /**
     * returns monsters' xy position on the board
     */
    public int[] getMonsterPosition()
    {
        return monsterPosition;
    }
}
