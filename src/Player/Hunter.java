package Player;

import Enemy.Enemy;
import Tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hunter extends Player{

    protected static final int EXTRA_ARROWS_COUNT_BONUS=10;
    protected static final int EXTRA_ATTACK_BONUS = 2;
    protected static final int EXTRA_DEFENSE_BONUS = 1;
    private int range;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(String name, int healthCapacity, int attack, int defense,int range) {
        super(name, healthCapacity, attack, defense);
        this.range=range;
        this.arrowsCount=10*level;
        this.ticksCount=0;

    }

    public void levelUp(){
        super.levelUp();
        arrowsCount=gainArrowsCount();
        setAttack(getAttack()+gainExtraAttack());
        setDefense(getDefense()+gainExtraDefense());
    }

    protected int gainArrowsCount(){
        return level * EXTRA_ARROWS_COUNT_BONUS;
    }

    protected int gainExtraAttack(){
        return level * EXTRA_ATTACK_BONUS;
    }

    protected int gainExtraDefense(){
        return level * EXTRA_DEFENSE_BONUS;
    }

    public String describe() {
        return String.format("%s\t\tRange: %d", super.describe(),getRange());
    }

    public int getRange() {
        return range;
    }

    @Override
    protected void specialAbility() {
        if (arrowsCount==0) {
            messageCallback.send(String.format("%s tried to shoot an arrow but the arrows ran out.", getName()));
            return;
        }
        Tile enemy=getEnemyWithinRange();
        if (enemy==null){
            messageCallback.send(String.format("%s tried to shoot an arrow but there were no enemies in range.",getName()));
            return;
        }
        messageCallback.send(String.format("%s fired an arrow at %s.",getName(),enemy.getName()));
        useAbility=true;
        arrowsCount=arrowsCount-1;
        interact(enemy,getAttack());
        useAbility=false;
    }

    private Tile getEnemyWithinRange(){
        Tile enemy=null;
        int minRange=getRange()+1;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if((this.position.range(board[i][j].getPosition()) <= getRange())&& !(board[i][j].getTile()=='@') && !(board[i][j].getTile()=='.') && !(board[i][j].getTile()=='#')) {
                    if (this.position.range(board[i][j].getPosition()) < minRange) {
                        minRange = this.position.range(board[i][j].getPosition());
                        enemy = board[i][j];
                    }
                }
            }
        }
        return enemy;
    }



    @Override
    public void gameTick() {
        if(ticksCount == 10){
            arrowsCount = arrowsCount+level;
            ticksCount = 0;
        }
        else
            ticksCount=ticksCount+1;

        Scanner s = new Scanner(System.in);
        char ch = s.next().charAt(0);
        switch (ch) {
            case 'w': {
                goUp();
                break;
            }
            case 's': {
                goDown();
                break;
            }
            case 'd': {
                goRight();
                break;
            }
            case 'a': {
                goLeft();
                break;
            }
            case 'e': {
                specialAbility();
                break;
            }
            case 'q': {
                break;
            }
        }
    }
}
