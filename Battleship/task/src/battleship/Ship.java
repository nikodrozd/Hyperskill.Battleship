package battleship;

public class Ship {

    private String name;
    private int size;
    private int hitPoints;

    private final String id;

    private String position;
    private int[] coordinates;

    public Ship(String name, int size, String id) {
        this.name = name;
        this.size = size;
        this.hitPoints = size;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void damage() {
        this.hitPoints--;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }
}
