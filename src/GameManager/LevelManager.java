package LevelManager;

import Enemy.Enemy;
import Player.Player;
import Tile.*;

import java.util.LinkedList;
import java.util.List;

public class LevelManager {
    public Tile[][] board;
    private List<Enemy> enemies;
    private Player character;
    private Position characterPosition;
    private MessageHandler cmdPrinter;

    public LevelManager(Tile[][] board){
        this.board=board;
        cmdPrinter=new cmdPrinter();
    }
    public void addEnemy(Enemy enemy){ enemies.add(enemy);
    }

    public int sizeEnemies(){
        return enemies.size();
    }

    public void removeEnemy(Enemy enemy){
        Empty empty=new Empty();
        board[enemy.getPosition().getX()][enemy.getPosition().getY()] = empty;
        empty.initialize(enemy.getPosition());
        enemies.remove(enemy);
    }


    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setCharacterPosition(Position characterPosition) {
        this.characterPosition = characterPosition;
    }

    public void setPosition(Position position) {
        character.setPosition(position);
    }

    public Position getCharacterPosition() {
        return characterPosition;
    }

    public boolean playLevel() {
        while (this.character.getHealth().getAmount() > 0 ) {
            this.cmdPrinter.sendMessage(this.printBoard());
            this.cmdPrinter.sendMessage(this.character.describe());
            this.character.gameTick();

            if (this.character.getHealth().getAmount() <= 0) {
                this.cmdPrinter.sendMessage(this.printBoard());
                this.cmdPrinter.sendMessage(this.character.describe());
                return false;
            }
            characterPosition =character.getPosition();
            for (Enemy enemy : enemies) {
                if (enemy.getHealth().getAmount() > 0) {
                    //int range=(int)enemy.getPosition().range(character.getPosition());
                    enemy.setRangeFromCharacter(enemy.getPosition().range(character.getPosition()));
                    enemy.setPlayerPosition(characterPosition);
                }
            }
            enemyGameTick();
            if (!enemiesLeft()) {
                return true;
            }
        }
        return false;
    }

    public void enemyGameTick() {
        for (Enemy enemy : enemies) {
            if (enemy.getHealth().getAmount() > 0) {
                enemy.gameTick();
            }
        }
    }



    public List<Enemy> getEnemy() {
        return enemies;
    }

    public void setCharacter(Player character) {
        this.character = character;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Player getCharacter() {
        return character;
    }

    public String printBoard() {
        String print = "";
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                print =print + board[i][j];
            }
            print =print+ "\n";
        }
        return print;
    }

    private boolean enemiesLeft() {
        if(sizeEnemies()>0)
            return true;
        return false;
    }
}
