package LevelManager;

import Enemy.*;
import Player.*;
import Tile.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelInitializer{

    public LevelManager parseLevel(List<String> levelData, List<Player> characters, int Idx) {
        //HashMap<Character,Enemy> enemies= createEnemies();
        Tile[][] board=new Tile[levelData.size()][levelData.get(0).length()];
        LevelManager levelManager = new LevelManager(board);
        List<Enemy> enemiesList=new ArrayList<Enemy>();
        levelManager.setEnemies(enemiesList);
        int line = 0;
        int column = 0;
        Player character = characters.get(Idx - 1);
        for (String row : levelData) {
            for (int i = 0; i < row.length(); i++) {
                char tile = row.charAt(i);
                if (tile == '.') {
                    Empty empty = new Empty();
                    Position pos = new Position(column, line);
                    empty.initialize(pos);
                    levelManager.getBoard()[line][column] = empty;
                } else if (tile == '#') {
                    Wall wall = new Wall();
                    Position pos = new Position(column, line);
                    wall.initialize(pos);
                    levelManager.getBoard()[line][column] = wall;
                } else if (tile == '@') {
                    Position pos = new Position(column, line);
                    character.initialize(pos);
                    character.messageCallback=(msg)->System.out.println(msg);
                    character.deathCallback=()-> levelManager.getCharacter().isDead();
                    levelManager.getBoard()[line][column] = character;

                } else {
                   Enemy enemy = typeEnemy(tile);
                   enemy.messageCallback = (msg)->System.out.println(msg);
                   enemy.deathCallback=()->levelManager.removeEnemy(enemy);
                   Position pos = new Position(column, line);
                   enemy.initialize(pos);
                   levelManager.getBoard()[line][column] = enemy;
                   levelManager.addEnemy(enemy);
                }
                column++;
            }
            line++;
            column = 0;
        }

        for (Enemy enemy: levelManager.getEnemy()) {
            enemy.setPlayerPosition(character.getPosition());
            enemy.setBoard(board);
        }
        levelManager.setCharacter(character);
        levelManager.setCharacterPosition(character.getPosition());

        return levelManager;
    }


    public List<Player> createCharacters() {
        List<Player> characters = new ArrayList<Player>();
        Warrior JonSnow = new Warrior("Jon Snow", 300, 30, 4, 3);
        Warrior TheHound = new Warrior("The Hound", 400, 20, 6, 5);
        Mage Melisandre = new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
        Mage ThorosOfMyr = new Mage("Thoros Of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
        Rogue AryaStark = new Rogue("Arya Stark", 150, 40, 2, 20);
        Rogue Bronn = new Rogue("Bronn", 250, 35, 3, 50);
         final Hunter Ygritte = new Hunter( "Ygritte", 220, 30, 2, 6);
        characters.add(JonSnow);
        characters.add(TheHound);
        characters.add(Melisandre);
        characters.add(ThorosOfMyr);
        characters.add(AryaStark);
        characters.add(Bronn);
        characters.add(Ygritte);
        return characters;
    }

    public Enemy typeEnemy(char enemy){
        switch (enemy){
            case 's':{
                return new Monster('s',"Lannister Solider", 80, 8, 3, 3,25);
            }
            case 'k':{
                return new Monster('k',"Lannister Knight", 200, 14, 8, 4,50);
            }
            case 'q':{
                return new Monster('q',"Queen’s Guard", 400, 20, 15, 5,100);
            }
            case 'z':{
                return new Monster('z',"Wright", 600, 30, 15, 3,100);
            }
            case 'b':{
                return  new Monster('b',"Bear-Wright", 1000, 75, 30, 4,250);
            }
            case 'g':{
                new Monster('g',"Giant-Wright", 1500, 100, 40, 5,500);
            }
            case 'w':{
                return new Monster('w',"White Walker", 2000, 150, 50, 6,1000);
            }
            case 'M':{
                return new Monster('M',"The Mountain", 1000, 60, 25, 6,500);
            }
            case 'C':{
                return new Monster('C',"Queen Cersei", 100, 10, 10, 1,1000);
            }
            case 'K':{
                return  new Monster('K',"Night’s King", 5000, 300, 150, 8,5000);
            }
            case 'B':{
                return new Trap('B',"Bonus Trap", 1, 1, 1, 250,1,5);
            }
            case 'Q':{
                return new Trap('Q',"Queen’s Trap", 250, 50, 10, 100,3,7);
            }
            case 'D':{
                return new Trap('D',"Night’s King", 500, 100, 20, 250,1,10);
            }
        }

        return null;
    }
}
