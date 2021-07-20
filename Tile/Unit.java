package Tile;

import Enemy.Enemy;
import Player.Player;

public abstract class Unit extends Tile implements Observer,Visited{
    public MessageCallback messageCallback;

    protected Tile[][] board;
    protected String name;
    protected Resource health;
    protected int attackDamage;
    protected int defensePoints;


    protected Unit(char tile, String name, int healthCapacity, int attackDamage, int defensePoints) {
        super(tile);
        this.name = name;
        this.health = new Resource(healthCapacity, healthCapacity);
        this.attackDamage = attackDamage;
        this.defensePoints = defensePoints;
    }

    public String getName() {
        return name;
    }

    public Resource getHealth() {
        return health;
    }

    public int getAttack() {
        return attackDamage;
    }

    public int getDefense() {
        return defensePoints;
    }

    public void setAttack(int attack) {
        this.attackDamage = attack;
    }

    public void setDefense(int defense) {
        this.defensePoints = defense;
    }

    public void setHealth(int health) {
        this.health.setHealthCapacity(health);
    }

    public int visit(Empty empty,int attack) {
        swapPosition(empty);
        return 1;
    }
    public abstract int visit(Player player,int attack);
    public abstract int visit(Enemy enemy,int attack);

    public void swapPosition(Tile tile){
        Position p = tile.position;
        tile.setPosition(this.getPosition());
        board[position.getX()][position.getY()]=tile;
        this.setPosition(p);
        board[p.getX()][p.getY()]=this;
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s/%s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().getAmount(),getHealth().getHealthCapacity(), getAttack(), getDefense());
    }

    public boolean alive(){
        return health.getAmount()>0;
    }
    public int attack(){
        return (int)(Math.random()*(attackDamage+1));
    }
    public int defense(){
        return (int)(Math.random()*(defensePoints+1));
    }
    protected abstract void onDeath();

    public void setBoard(final Tile[][] board) {
        this.board = board;
    }

    public MessageCallback getMessageCallback() {
        return messageCallback;
    }
}
