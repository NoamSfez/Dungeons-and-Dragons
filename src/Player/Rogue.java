package Player;

import Enemy.Enemy;
import Tile.Tile;
import Tile.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rogue extends Player{
    private int cost;
    private int currentEnergy;
    protected static final int MAXIMAL_VALUE = 100;
    protected static final int EXTRA_ATTACK_BONUS = 3;
    protected static final int RANGE = 2;

    public Rogue(String name, int healthCapacity, int attack, int defense,int cost) {
        super(name, healthCapacity, attack, defense);
        this.cost=cost;
        this.currentEnergy=MAXIMAL_VALUE;
    }

    protected int gainExtraAttack(){
        return level * EXTRA_ATTACK_BONUS;
    }

    public void levelUp(){
        super.levelUp();
        currentEnergy = MAXIMAL_VALUE;
        this.setAttack(getAttack() + gainExtraAttack());
        List<Tile> enemies=getEnemiesWithinRange();
        if(enemies.size()==0){
            return;
        }
        for (Tile enemy : enemies) {
            useAbility=true;
            interact(enemy,getAttack());
            useAbility=false;
        }
    }


    public String describe() {
        return String.format("%s\t\tEnergy: %d/%d", super.describe(),getCurrentEnergy(),getMaximalValue());
    }

    @Override
    public void specialAbility() {
        if(currentEnergy<cost)
            messageCallback.send(String.format("%s tried to cast Fan of Knives, but there was not enough energy: %d/%d.",getName(),currentEnergy,cost));
        else{
            currentEnergy=currentEnergy-cost;
            messageCallback.send(String.format("%s cast Fan of Knives.",getName()));
            List<Tile> enemies = getEnemiesWithinRange();
            useAbility=true;
            for (Tile enemy:enemies) {
                interact(enemy,attack());
            }
            useAbility=false;
        }
    }
    private List<Tile> getEnemiesWithinRange(){
        ArrayList<Tile> enemies=new ArrayList<Tile>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if((this.position.range(board[i][j].getPosition()) < RANGE)&& !(board[i][j].getTile()=='@') && !(board[i][j].getTile()=='.') && !(board[i][j].getTile()=='#'))
                    enemies.add(board[i][j]);
            }
        }
        return enemies;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public static int getMaximalValue() {
        return MAXIMAL_VALUE;
    }

    @Override
    public void gameTick() {
        currentEnergy = Math.min(currentEnergy + 10, MAXIMAL_VALUE);
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
            case 'e' :{
                specialAbility();
                break;
            }
            case 'q': {
                break;
            }
        }
    }


}
