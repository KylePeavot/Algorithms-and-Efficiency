/** Critique of launchAttack()
 * I believe that this is quite an efficient solution to the problem given. Up until the last
 * line, the code is quite simple in its complexity. The last line calls a function that is
 * primarily a for loop checking the coordinates that have been chosen against each
 * monster in monsters coordinates. This gives this part of the algorithm a time complexity
 * of O(n) which is quite efficient. This part of the function is the most complex and therefore,
 * determines how complex the rest of the function is, therefore the algorithm I have made is also
 * O(n).
 *
 * A more efficient way of doing this could be to utilise the fact that this program is quite
 * small and will likely never have a very big board and create a new ArrayList containing all
 * monsters on the board except the one attacking, randomly select a monster in the ArrayList
 * if that monster is next to the monster attacking, "kill" it, if not, remove it from that
 * ArrayList
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author (Kyle Peavot)
 * @version (30 / 10 / 18)
 * Written in intelliJ IDEA
 */
public class Board {
    private final int SIZE; //the size of the board
    public char board[][];  //the board that the user sees
    private Random rand;    //random numbers
    private int first, last; //where the field of play starts and ends
    private ArrayList<Monster> monsters; //holds all monsters currently on the board

    /**
     * Constructor fills the board initially with *
     * and sets up the surrounding edges / hedge "="
     */

    public Board(int boardSize) {
        SIZE = boardSize;

        first = 1;
        last = SIZE - 1;

        board = new char[SIZE][SIZE];
        rand = new Random();

        monsters = new ArrayList<>();

        for (int i = first; i < last; i++) {
            for (int j = first; j < last; j++)
                board[i][j] = '*';

        }
        for (int i = 0; i < SIZE; i++) {
            board[0][i] = '=';
            board[i][0] = '=';
            board[i][SIZE - 1] = '=';
            board[SIZE - 1][i] = '=';
        }
    }

    /**
     * Displays the board
     */
    public void viewBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * returns a valid position for a new monster to be placed
     */
    public int[] findPlaceMonster() {
        int[] monsterXY = new int[2];
        do { //find coordinates that aren't occupied
            monsterXY[0] = rand.nextInt(SIZE - 2) + 1; //always going to be within the field of play
            monsterXY[1] = rand.nextInt(SIZE - 2) + 1;
        } while (checkValidSpace(monsterXY[0], monsterXY[1]));

        return monsterXY;
    }

    /**
     * returns valid monster to attack from given monster's position
     */
    public Monster launchAttack(int[] monstPos) {
        ArrayList<int[]> validSpaces = new ArrayList<>();

        int x = monstPos[0], y = monstPos[1];

        if (checkValidSpace(x + 1, y)) validSpaces.add(new int[]{x + 1, y}); //right of monster
        if (checkValidSpace(x - 1, y)) validSpaces.add(new int[]{x - 1, y}); //left of monster
        if (checkValidSpace(x, y + 1)) validSpaces.add(new int[]{x, y + 1}); //above monster
        if (checkValidSpace(x, y - 1)) validSpaces.add(new int[]{x, y - 1}); //below monster

        if (validSpaces.size() == 0) {
            return null;
        } else return findMonster(validSpaces.get(rand.nextInt(validSpaces.size())));
    }

    /**
     * checks if there is a monster in the coordinates provided
     */
    public boolean checkValidSpace(int x, int y) {
        char tempChar = board[x][y];
        if (!(x > SIZE - 1 || y > SIZE - 1)) {
            return tempChar != '=' && tempChar != '*';
        }
        return false;
    }

    /**
     * returns true if all spaces on the board are occupied, false if not
     */
    public boolean isFull() {
        for (int i = first; i < last; i++) {
            for (int j = first; j < last; j++) {
                if (board[i][j] == '*') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * looks for a monster in monsters with a given name
     * if one isn't found, return null
     */
    public Monster findMonster(String name) {
        for (Monster m : monsters) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }
    public Monster findMonster(int[] monstXY) {
        for (Monster m : monsters) {
            if (Arrays.equals(m.getMonsterPosition(), monstXY)) {
                return m;
            }
        }
        return null;
    }

    /**
     * getter for monsters
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * adds an element to monsters
     */
    public void addToMonsters(Monster monster) {

        monsters.add(monster);
    }

    /**
     * removes an element from monsters
     */
    public void removeFromMonsters(Monster monster) {
        monsters.remove(monster);
    }

    /**
     * replaces a value on the board with the first letter of the given string
     * @param name the name of the new monster or * when a monster is killed
     */
    public void setBoard(int[] xy, String name) {
        board[xy[0]][xy[1]] = name.charAt(0);
    }
}