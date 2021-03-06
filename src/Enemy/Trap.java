package Enemy;

import Player.Player;
import Tile.Tile;
import Tile.Unit;

public class Trap extends Enemy{

    private int range=2;
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int healthCapacity, int attack, int defense,int experienceValue,int visibilityTime,int invisibilityTime) {
        super(tile, name, healthCapacity, attack, defense,experienceValue);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        ticksCount=0;
        visible=true;
    }

    @Override
    public void gameTick() {
        visible=ticksCount<visibilityTime;
        if(ticksCount==(visibilityTime+invisibilityTime)){
            ticksCount=0;
        }
        else {
            ticksCount=ticksCount+1;
        }
        if(this.position.range(playerPosition)<range){
            Tile player=findPlayer();
            if(player!=null)
                interact(player,getAttack());
        }
    }


    private Tile findPlayer(){
        for (int i = 0; i < this.board.length; ++i) {
            for (int j = 0; j < this.board[i].length; ++j) {
                if (this.board[i][j].toString().equals("@")) {
                    return this.board[i][j];
                }
            }
        }
        return null;
    }

    public String toString() {
        if (visible) {
            return super.toString();
        }
        return ".";
    }
}
