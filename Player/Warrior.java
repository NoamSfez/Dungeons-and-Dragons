package Player;

import Enemy.Enemy;
import Tile.Tile;
import Tile.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Warrior extends Player{
    private int remainingCooldown;
    private int abilityCooldown;
    protected static final int SPECIAL_ABILITY_BONUS = 10;
    protected static final int EXTRA_HEALTH_BONUS = 5;
    protected static final int EXTRA_ATTACK_BONUS = 2;
    protected static final int EXTRA_DEFENSE_BONUS = 1;
    protected static final int RANGE = 3;
    protected static final Double AMOUNT_PERCENT_OF_LIFE = 0.1;

    public Warrior(String name, int healthCapacity, int attack, int defense,int abilityCooldown) {
        super(name, healthCapacity, attack, defense);
        this.remainingCooldown = 0;
        this.abilityCooldown = abilityCooldown;
    }

    public void levelUp(){
        super.levelUp();
        remainingCooldown = 0;
        setHealth(getHealth().getHealthCapacity() + gainExtraHealth());
        setAttack(getAttack() + gainExtraAttack());
        setDefense(getDefense() + gainExtraDefense());
        health.resture();
    }

    @Override
    public void specialAbility() {
        if(remainingCooldown>0)
            messageCallback.send(String.format("%s tried to cast Avenger's Shield, but there is a cooldown: %d.",getName(),remainingCooldown));
        else{
            remainingCooldown=abilityCooldown;
            health.setAmount(Math.min(health.getAmount()+gainSpecialAbility(),health.getHealthCapacity()));
            messageCallback.send(String.format("%s used Avenger's Shield, healing for %d.",getName(),gainSpecialAbility()));
            List<Tile> enemies=getEnemiesWithinRange();
            if(enemies.size()==0){
                return;
            }
            Random rnd = new Random();
            useAbility=true;
            interact(enemies.get(rnd.nextInt(enemies.size())),gainSpecialAbilityAttack());
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


    protected int gainSpecialAbility(){
        return defensePoints * SPECIAL_ABILITY_BONUS;
    }

    protected int gainSpecialAbilityAttack(){
        return (int)(health.getHealthCapacity() * AMOUNT_PERCENT_OF_LIFE);
    }

    protected int gainExtraHealth(){
        return level * EXTRA_HEALTH_BONUS;
    }

    protected int gainExtraAttack(){
        return level * EXTRA_ATTACK_BONUS;
    }

    protected int gainExtraDefense(){
        return level * EXTRA_DEFENSE_BONUS;
    }

    public String describe() {
        return String.format("%s\t\tCooldown: %d/%d", super.describe(),remainingCooldown,abilityCooldown );
    }


    @Override
    public void gameTick() {
        if(remainingCooldown!=0){
            remainingCooldown=remainingCooldown-1;
        }
        Scanner s = new Scanner(System.in);
        char ch = s.next().charAt(0);
        switch (ch){
            case 'w':{
                goUp();
                break;
            }
            case 's' :{
                goDown();
                break;
            }
            case 'd' :{
                goRight();
                break;
            }
            case 'a' :{
                goLeft();
                break;
            }
            case 'e' :{
                specialAbility();
                break;
            }
            case 'q':{
                break;
            }
        }
    }
}
