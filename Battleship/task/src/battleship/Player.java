package battleship;

import java.util.Map;

public class Player {
    private final String name;

    private String[][] battleField;
    private String[][] fogOfWar;
    private Map<String, Ship> shipsList;

    private boolean isActive;

    public Player(String name) {
        this.name = name;
    }

    public String[][] getBattleField() {
        return battleField;
    }

    public void setBattleField(String[][] battleField) {
        this.battleField = battleField;
    }

    public String[][] getFogOfWar() {
        return fogOfWar;
    }

    public void setFogOfWar(String[][] fogOfWar) {
        this.fogOfWar = fogOfWar;
    }

    public Map<String, Ship> getShipsList() {
        return shipsList;
    }

    public void setShipsList(Map<String, Ship> shipsList) {
        this.shipsList = shipsList;
    }

    public String getName() {
        return name;
    }

    public boolean validateCoordinates (int[] coordinates, Ship ship) {
        int coordinateForCompare1;
        int coordinateForCompare2;
        switch (getPosition(coordinates)) {
            case "H":
                coordinateForCompare1 = coordinates[1];
                coordinateForCompare2 = coordinates[3];
                break;
            case "V":
                coordinateForCompare1 = coordinates[0];
                coordinateForCompare2 = coordinates[2];
                break;
            default:
                System.out.println();
                System.out.println("Error! Wrong ship location! Try again:");
                return false;
        }
        if ((coordinateForCompare2 - coordinateForCompare1 + 1) != ship.getSize()) {
            System.out.println();
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
            return false;
        }
        int[] checkedArea = new int[4];
        checkedArea[0] = Math.max(coordinates[0] - 1, 1);
        checkedArea[1] = Math.max(coordinates[1] - 1, 1);
        checkedArea[2] = Math.min(coordinates[2] + 1, battleField.length - 1);
        checkedArea[3] = Math.min(coordinates[3] + 1, battleField[0].length - 1);
        for (int i = checkedArea[0]; i <= checkedArea[2]; i++) {
            for (int j = checkedArea[1]; j <= checkedArea[3]; j++) {
                if (!"~".equals(battleField[i][j])) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }
        return true;
    }

    public String getPosition (int[] coordinates) {
        if (coordinates[0] == coordinates[2]) {
            return "H";
        } else if (coordinates[1] == coordinates[3]) {
            return "V";
        } else {
            return "Error";
        }
    }

    public void placeShip(int[] coordinates, Ship ship) {
        if ("H".equals(getPosition(coordinates))) {
            for (int i = coordinates[1]; i <= coordinates[3]; i++) {
                battleField[coordinates[0]][i] = ship.getId();
            }
        } else {
            for (int i = coordinates[0]; i <= coordinates[2]; i++) {
                battleField[i][coordinates[1]] = ship.getId();
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
