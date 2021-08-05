package battleship;

import java.io.IOException;
import java.util.*;

public class Main {

    private static Player player1 = new Player("Player 1");
    private static Player player2 = new Player("Player 2");

    private static Scanner in;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        initBattleField(player1);
        initBattleField(player2);

        shipsPlaysment(player1);
        System.out.println("The game starts!");
        nextTurn();
        shipsPlaysment(player2);

        boolean battleIsActive = true;
        while (battleIsActive) {
            nextTurn();
            if (player1.isActive()) {
                player1.setActive(false);
                player2.setActive(true);
                battleIsActive = turnBattleResult(player2, player1);
            } else {
                player2.setActive(false);
                player1.setActive(true);
                battleIsActive = turnBattleResult(player1, player2);
            }
        }
    }

    public static void shipsPlaysment(Player player) {
        System.out.println(player.getName() + ", place your ships on the game field");
        System.out.println();
        System.out.println();
        System.out.println();
        printBattleField(player.getBattleField());
        for (Map.Entry<String, Ship> ship : player.getShipsList().entrySet()) {
            System.out.println();
            System.out.println("Enter the coordinates of the " + ship.getValue().getName() + " (" + ship.getValue().getSize() + " cells):");
            boolean menuIsActive = true;
            while (menuIsActive) {
                System.out.println();
                System.out.print("> ");
                int[] coordinate1 = parseCoordinates(in.next());
                int[] coordinate2 = parseCoordinates(in.next());
                int[] coordinates = orderCoordinates(coordinate1, coordinate2);
                if (player.validateCoordinates(coordinates, ship.getValue())) {
                    player.placeShip(coordinates, ship.getValue());
                    menuIsActive = false;
                }
            }
            System.out.println();
            printBattleField(player.getBattleField());
        }
    }

    public static boolean turnBattleResult(Player attacker, Player defender) {
        System.out.println();
        printBattleField(attacker.getFogOfWar());
        System.out.println("---------------------");
        printBattleField(attacker.getBattleField());
        System.out.println();
        System.out.println(attacker.getName() + ", it's your turn:");
        boolean isPlayerTurnActive = true;
        while (isPlayerTurnActive) {
            System.out.println();
            System.out.print("> ");
            int[] coordinate = parseCoordinates(in.next());
            if (coordinate[1] <= 10 && coordinate[1] >= 1 && coordinate[0] > 0) {
                String shotResult = takeShot(coordinate[0], coordinate[1], defender.getBattleField(), attacker.getFogOfWar());
                System.out.println();
                switch (shotResult){
                    case "M":
                        System.out.println("You missed!");
                        break;
                    case "X":
                        System.out.println("You hit a ship!");
                        break;
                    default:
                        defender.getShipsList().get(shotResult).damage();
                        if (defender.getShipsList().get(shotResult).getHitPoints() > 0) {
                            System.out.println("You hit a ship!");
                        } else {
                            defender.getShipsList().remove(shotResult);
                            if (defender.getShipsList().size() > 0) {
                                System.out.println("You sank a ship!");
                            } else {
                                System.out.println("You sank the last ship. You won. Congratulations!");
                                return false;
                            }

                        }
                }
                isPlayerTurnActive = false;
            } else {
                System.out.println();
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        }
        return true;
    }

    public static String takeShot (int row, int col, String[][] defenderBattleField, String[][] attackerFogOfWar) {
        switch (defenderBattleField[row][col]){
            case "~":
            case "M":
                defenderBattleField[row][col] = "M";
                attackerFogOfWar[row][col] = "M";
                return "M";
            case "X":
                return "X";
            default:
                String shipId = defenderBattleField[row][col];
                defenderBattleField[row][col] = "X";
                attackerFogOfWar[row][col] = "X";
                return shipId;
        }
    }

    public static int[] parseCoordinates(String coordinate) {
        int[] coordinates = new int[2];
        coordinates[0] = defineRow(coordinate.substring(0,1));
        coordinates[1] = Integer.parseInt(coordinate.substring(1));
        return coordinates;
    }

    public static int[] orderCoordinates (int[] coordinate1, int[] coordinate2) {
        if (coordinate2[0] < coordinate1[0]) {
            int temp = coordinate1[0];
            coordinate1[0] = coordinate2[0];
            coordinate2[0] = temp;
        }
        if (coordinate2[1] < coordinate1[1]) {
            int temp = coordinate1[1];
            coordinate1[1] = coordinate2[1];
            coordinate2[1] = temp;
        }
        return new int[]{coordinate1[0], coordinate1[1], coordinate2[0], coordinate2[1]};
    }

    public static int defineRow (String letter) {
        switch (letter){
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            case "I":
                return 9;
            case "J":
                return 10;
            default:
                return 0;
        }
    }

    public static void initBattleField(Player player) {
        String[][] battleField = new String[11][11];
        battleField[0] = new String[]{" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        battleField[1] = new String[]{"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[2] = new String[]{"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[3] = new String[]{"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[4] = new String[]{"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[5] = new String[]{"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[6] = new String[]{"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[7] = new String[]{"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[8] = new String[]{"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[9] = new String[]{"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        battleField[10] = new String[]{"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"};
        String[][] fogOfWar =  new String[11][11];
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField[0].length; j++) {
                fogOfWar[i][j] = battleField[i][j];
            }
        }

        Map<String, Ship> shipsList = new TreeMap<>();
        shipsList.put("1", new Ship("Aircraft Carrier", 5, "1"));
        shipsList.put("2", new Ship("Battleship", 4, "2"));
        shipsList.put("3", new Ship("Submarine", 3, "3"));
        shipsList.put("4", new Ship("Cruiser", 3, "4"));
        shipsList.put("5", new Ship("Destroyer", 2, "5"));

        player.setBattleField(battleField);
        player.setFogOfWar(fogOfWar);
        player.setShipsList(shipsList);
    }

    public static void printBattleField(String[][] field) {
        for (int i = 0; i < field.length; i++) {
            StringBuilder row = new StringBuilder();
            row.append(field[i][0]);
            for (int j = 1; j < field[i].length; j++) {
                if (i == 0 || "~".equals(field[i][j]) || "X".equals(field[i][j]) || "M".equals(field[i][j])) {
                    row.append(" ").append(field[i][j]);
                } else {
                    row.append(" ").append("O");
                }

            }
            System.out.println(row);
        }
    }

    public static void nextTurn() {
        System.out.println();
        System.out.println("Press Enter and pass the move to another player");
        System.out.print("...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
