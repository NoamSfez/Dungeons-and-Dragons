package Player;
import Enemy.Enemy;
import Tile.*;
import Tile.Unit;

public abstract class Player extends Unit {
    public static Boolean isDead=false;
    public boolean useAbility=false;
    public static final char playerTile = '@';
    protected static final int REQ_EXP = 50;
    protected static final int HEALTH_BONUS = 10;
    protected static final int ATTACK_BONUS = 4;
    protected static final int DEFENSE_BONUS = 1;
    public PlayerDeathCallback deathCallback;
    protected int level;
    protected int experience;

    protected Player( String name, int healthCapacity, int attack, int defense) {
        super(playerTile, name, healthCapacity, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    protected int levelUpRequirement(){
        return REQ_EXP * level;
    }
    protected int gainHealth(){
        return level * HEALTH_BONUS;
    }
    protected int gainAttack(){
        return level * ATTACK_BONUS;
    }

    protected int gainDefense(){
        return level * DEFENSE_BONUS;
    }

    public int visit(Wall wall, int attack){return 1;};

    public int visit(Player player,int attack){return 1;};

        public void isDead(){
        isDead=true;
        this.tile='X';
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), getLevel(), getExperience(), levelUpRequirement());
    }
    public int visit(Enemy enemy,int attack){
        if(alive()) {
            battle(enemy, attack);
            if (!alive()) {
                onDeath();
                return 0;
            }
        }
        return 1;
    }


    public void battle(Enemy enemy,int attack){
        messageCallback.send(String.format("%s engaged in combat with %s.\n%s\n%s",enemy.getName(),getName(),enemy.describe(),describe()));
        int def=defense();
        messageCallback.send(String.format("%s rolled %d attack points.",enemy.getName(),attack));
        messageCallback.send(String.format("%s rolled %d defense points.",getName(),def));
        int damegeDone=Math.max(attack-def,0);
        health.reduceAmount(damegeDone);
        messageCallback.send(String.format("%s dealt %d damage to %s.",enemy.getName(),damegeDone,getName()));
        if(!alive())
            messageCallback.send(String.format("%s was killed by %s.",getName(),enemy.getName()));
    }


    public void onKill(Enemy enemy){
        int experienceGained=enemy.getExperienceValue();
        messageCallback.send(String.format("%s died. %s gained %d experience",enemy.getName(),getName(),experienceGained));
        addExperience(experienceGained);
        enemy.onDeath();
    }

    public void addExperience(int experienceGained){
        this.experience+=experienceGained;
        int nextLevelReq=levelUpRequirement();
        while (experience>=nextLevelReq){
            levelUp();
            experience-=nextLevelReq;
            nextLevelReq=levelUpRequirement();
        }
    }
    public void levelUp(){
        level++;
        int healthGained=gainHealth();
        int attackGained=gainAttack();
        int defenseGained=gainDefense();
        health.addCapacity(healthGained);
        health.resture();
        attackDamage +=attackGained;
        defensePoints +=defenseGained;
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence.",getName(),getLevel(),healthGained,attackGained,defenseGained));
    }
    public void onDeath(){
        messageCallback.send("You lost.");
        deathCallback.call();
    }
    public int interact(Tile tile,int attack){
        return tile.visit(this,attack );
    }

    protected abstract void specialAbility();

    protected void goUp(){
        interact(board[position.getX()-1][position.getY()],attack());
    }
    protected void goDown(){
        interact(board[position.getX()+1][position.getY()],attack());
    }
    protected void goRight(){
        interact(board[position.getX()][position.getY()+1],attack());
    }
    protected void goLeft(){
        interact(board[position.getX()][position.getY()-1],attack());
    }



}

