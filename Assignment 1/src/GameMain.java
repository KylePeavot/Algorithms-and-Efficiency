import java.util.Scanner;

/**
 * @author (Kyle Peavot)
 * @version (30 / 10 / 18)
 * Written in intelliJ IDEA
 */
public class GameMain {

    private Board board;
    private Scanner sc;

    /**
     * constructor creates a scanner and defines a new board
     * @param boardSize
     */
    public GameMain(int boardSize)
    {
        sc = new Scanner(System.in);
        board = new Board(boardSize);
    }

    public void run()
    {
        int userInput = 0;
        while (userInput != 6) { //until the user quits
            printMenu(); //print the menu

            System.out.print("Menu choice: ");
            userInput = sc.nextInt(); //determine user's choice

            System.out.println();

            callFunctions(userInput); //carry out operation
        }
    }

    /**
     * prints the menu
     */
    public void printMenu()
    {
        System.out.println();
        System.out.println("1. Create a monster");
        System.out.println("2. Launch an attack");
        System.out.println("3. View the board");
        System.out.println("4. Retrieve a score of a monster");
        System.out.println("5. List the players");
        System.out.println("6. Quit playing");
        System.out.println();
    }

    /**
     * takes user input and carries out the operation user has chosen
     */
    public void callFunctions(int userIn)
    {
        Monster monster;
        switch (userIn) {
            case 1: //add monster
                if (!board.isFull()) {
                    System.out.print("Name of monster to add to game: ");
                    monster = new Monster(board.findPlaceMonster(), sc.next());

                    if (board.findMonster(monster.getName()) != null) { //if the new monster's name matches an already existing monster name
                        System.out.println("That name has already been taken! Monster not added.");
                    } else monster.addMonster(board);

                } else {
                    System.out.println("The board is full - kill some monsters!");
                }
                break;
            case 2: //attack monster
                System.out.print("Name of monster that is attacking: ");
                if ((monster = board.findMonster(sc.next())) != null) {
                    monster.attackMonster(board);
                } else System.out.println("There is no monster with that name");
                break;
            case 3: //view board
                board.viewBoard();
                break;
            case 4: //print score of a monster
                System.out.print("Name of monster to retrieve score for: ");
                if ((monster = board.findMonster(sc.next())) != null) {
                    System.out.println("Monster " + monster.getName() + "'s Score : " + monster.getScore());
                } else System.out.println("There is no monster with that name");

                break;
            case 5: //prints all players
                System.out.println("All players on the board: ");
                System.out.println();
                for (Monster monst : board.getMonsters()) {
                    System.out.println(monst.getName());
                }
                break;
        }
    }

    /**
     * main method
     */
    public static void main(String[] args)
    {
        GameMain gm = new GameMain(5); //create a new GameMain and determine size of board
        gm.run();
    }
}
