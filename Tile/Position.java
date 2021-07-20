package Tile;

public class Position {
    private int y;
    private int x;

    public Position(int y,int x){
        this.y=y;
        this.x=x;
    }
    public int range(Position OtherPos){
    int a= (int)Math.pow((Math.pow(getX()-OtherPos.getX(),2)+Math.pow(getY()-OtherPos.getY(),2)),0.5);
    return a;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}


