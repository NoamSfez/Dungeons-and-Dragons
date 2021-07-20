package Tile;

import Enemy.Enemy;
import Player.Player;

public interface Visitor {
    int visit(Player player ,int attack);

    int visit(Enemy enemy,int attack);

    int visit(Wall wall,int attack);

    int visit(Empty empty,int attack);

}
