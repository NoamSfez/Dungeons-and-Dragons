package LevelManager;

import Enemy.Enemy;
import Player.Player;
import Tile.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GameManager {


    public static void main(String[] args){
        LevelInitializer init = new LevelInitializer();
        MessageHandler cmd = new cmdPrinter();
        List<Player> characters = init.createCharacters();
        List<LevelManager> levels = new ArrayList<LevelManager>();
        List<File> files=new ArrayList<>();
        String[] pathNames;
        if(args.length==0 || args[0]==null){
            cmd.sendMessage("You need to register the folder of levels.");
            return;
        }
        File f=new File("./"+args[0]);
        pathNames=f.list();
        try {
            boolean playerSelection=false;
            char charIdx = ' ';
            do {
                String chooseFighter = "Select player:\n";
                int index = 1;
                for (Player player : characters) {
                    chooseFighter = chooseFighter + index + ". " + player.describe() + "\n";
                    index++;
                }
                cmd.sendMessage(chooseFighter);

                Scanner s = new Scanner(System.in);
                charIdx = s.nextLine().charAt(0);

                if(charIdx< '0' || charIdx>'9')
                    cmd.sendMessage("Not a number");
                if(charIdx >= '1'&& charIdx<=("" + characters.size()).charAt(0)){
                    playerSelection=true;
                }
            }while (!playerSelection);
            int Idx = charIdx - '0';
            cmd.sendMessage("You have selected: \n" + characters.get(Idx - 1).getName());
            for (String levelPath : pathNames) {
                List<String> levelData = Files.readAllLines(Paths.get("./" + args[0] + "/" + levelPath , new String[0]));
                LevelManager level = init.parseLevel(levelData, characters, Idx);
                levels.add(level);
                level.setPosition(level.getCharacterPosition());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Player character=levels.get(0).getCharacter();
        for (LevelManager currentLevel : levels) {
            currentLevel.setCharacter(character);
            character.setBoard(currentLevel.getBoard());
            character.setPosition(currentLevel.getCharacterPosition());
            if(!currentLevel.playLevel()){
                cmd.sendMessage(currentLevel.printBoard());
                cmd.sendMessage(character.describe());
                cmd.sendMessage("Game Over.");
                break;
            }
            if (currentLevel == levels.get(levels.size() - 1)) {
                cmd.sendMessage("You won!");
            }

        }

    }
}



