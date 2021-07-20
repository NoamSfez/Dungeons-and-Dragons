package Tile;

public abstract class Tile implements Visitor{
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }

    public void initialize(Position position){
        this.position = position;
    }

    public char getTile() { return tile;}

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String toString() {
        return String.valueOf(tile);
    }

    public abstract String getName();
}
