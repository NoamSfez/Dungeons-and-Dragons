package Tile;

import Enemy.Enemy;
import Player.Player;

public class Empty extends Tile{

    public Empty(){
        super('.');
    }

    @Override
    public int visit(Player player, int attack) {
        player.swapPosition(this);
        return 1;
    }

    @Override
    public int visit(Enemy enemy, int attack) {
        enemy.swapPosition(this);
        return 1;
    }

    @Override
    public int visit(Empty empty, int attack) {
        return 1;
    }

    @Override
    public int visit(Wall wall, int attack) {
        return 1;
    }

    @Override
    public String getName() {
        return "Empty";
    }
}
