package Player;

import Enemy.Enemy;
import Tile.Tile;
import Tile.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Mage extends Player{
    private int manaPool;
    private int manaCost;
    private int currentMana;
    private int spellPower;
    private int hitCount;
    private int abilityRange;

    protected static final int MANA_POOL_BONUS = 25;
    protected static final int CURRENT_MANA_BONUS = 4;
    protected static final int EXTRA_CURRENT_MANA_BONUS = 1;
    protected static final int SPELL_POWER_BONUS = 10;

    public Mage(String name, int healthCapacity, int attack, int defense,int manaPool,int manaCost,int spellPower,int hitCount,int abilityRange) {
        super(name, healthCapacity, attack, defense);
        this.manaPool=manaPool;
        this.manaCost=manaCost;
        this.currentMana=manaPool/4;
        this.spellPower=spellPower;
        this.hitCount=hitCount;
        this.abilityRange=abilityRange;
    }

    public void levelUp(){
        super.levelUp();
        this.setManaPool(this.getManaPool() + gainManaPool());
        this.setCurrentMana(Math.min(this.getCurrentMana() + gainCurrentMana(), manaPool));
        this.setSpellPower(this.getSpellPower() + gainSpellPower());
    }

    @Override
    public void specialAbility() {
        if (currentMana < manaCost)
            messageCallback.send(String.format("%s tried to cast Blizzard, but there was not enough mana: %d/%d.", getName(), currentMana, manaCost));
        else {
            currentMana = currentMana - manaCost;
            messageCallback.send(String.format("%s cast Blizzard.", getName()));
            Random rnd = new Random();
            int hits = 0;
            List<Tile> enemies = getEnemiesWithinRange();
            if (enemies.size() == 0) {
                return;
            }
            while (hits < hitCount && enemies.size() > 0) {
                int enemy = rnd.nextInt(enemies.size());
                useAbility=true;
                int result=interact(enemies.get(enemy),getSpellPower());
                useAbility=false;
                if(result==0)
                    enemies.remove(enemy);
                hits = hits + 1;
            }
        }
    }

    private List<Tile> getEnemiesWithinRange(){
        ArrayList<Tile> enemies=new ArrayList<Tile>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if((this.position.range(board[i][j].getPosition()) < abilityRange)&& !(board[i][j].getTile()=='@') && !(board[i][j].getTile()=='.') && !(board[i][j].getTile()=='#'))
                    enemies.add(board[i][j]);
            }
        }
        return enemies;
    }



    protected int gainManaPool(){
        return level * MANA_POOL_BONUS;
    }
    protected int gainCurrentMana(){ return manaPool/CURRENT_MANA_BONUS; }
    protected int gainSpellPower(){ return level * SPELL_POWER_BONUS; }
    protected int gainExtraCurrentMana(){ return level * EXTRA_CURRENT_MANA_BONUS; }


    public String describe() {
        return String.format("%s\t\tMana: %d/%d\t\tSpell Power: %d", super.describe(),getCurrentMana(),getManaPool(),getSpellPower());
    }


    public int getManaPool() {
        return manaPool;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    @Override
    public void gameTick() {
        currentMana = Math.min(manaPool, currentMana + gainExtraCurrentMana());
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
