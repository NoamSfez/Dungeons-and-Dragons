package Tile;

import Enemy.Enemy;
import Player.Player;

public class Wall extends Tile{
    public Wall(){
        super('#');
    }
//
//    @Override
//    public int interact(Unit unit,int attack) {
//        return unit.visit(this,attack);
//    }
//
//    @Override
//    public int interact(Tile.Tile tile, int attack) {
//        return 0;
//    }

    @Override
    public int visit(Player player, int attack) {
        return 1;
    }

    @Override
    public int visit(Enemy enemy, int attack) {
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
        return "Wall";
    }
}
