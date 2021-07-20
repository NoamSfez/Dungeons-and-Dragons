package Enemy;
import Player.Player;
import Tile.*;

public abstract class Enemy extends Unit {

    public EnemyDeathCallback deathCallback;
    private int experienceValue;
    protected int rangeFromCharacter;
    protected Position playerPosition;

    protected Enemy(char tile, String name, int healthCapacity, int attack, int defense,int experienceValue) {
        super(tile, name, healthCapacity, attack, defense);
        this.experienceValue=experienceValue;
    }

    public int accept(Unit u ,int attack){
        return u.visit(this,getAttack());
    }

    public int interact(Tile tile, int attack){
        return tile.visit(this,attack );
    }

    public int visit(Enemy enemy,int attack){return 1;};

    public int visit(Wall wall, int attack){return 1;};

    public int visit(Empty empty, int attack){
        swapPosition(empty);
        return 1;
    };

    public int visit(Player player,int attack){
        battle(player,attack);
        if(!alive()){
            if(!player.useAbility)
                swapPosition(player);
            player.onKill(this);
            return 0;
        }
        return 1;
    };

    public void setRangeFromCharacter(int range) {
        this.rangeFromCharacter = range;
    }

    public void setPlayerPosition(Position position) {
        this.playerPosition = position;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    public void onDeath(){
        deathCallback.call();
    }


    public void battle(Player player,int attack){
        int def=defense();
        int damegeDone=Math.max(attack-def,0);
        if(!player.useAbility) {
            messageCallback.send(String.format("%s engaged in combat with %s.\n%s\n%s", player.getName(), getName(), player.describe(), describe()));
            messageCallback.send(String.format("%s rolled %d attack points.",player.getName(),attack));
        }
        messageCallback.send(String.format("%s rolled %d defense points.",getName(),def));
        this.health.reduceAmount(damegeDone);
        if(!player.useAbility)
            messageCallback.send(String.format("%s dealt %d damage to %s.",player.getName(),damegeDone,getName()));
        else
            messageCallback.send(String.format("%s hit %s for %d ability damage.",player.getName(),getName(),damegeDone));
    }
}
