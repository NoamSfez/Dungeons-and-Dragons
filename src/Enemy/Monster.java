package Enemy;

import Player.Player;
import Tile.Unit;

import java.util.Random;

public class Monster extends Enemy{
    private int visionRange;
    private int randomChoice=5;

    public Monster(char tile, String name, int healthCapacity, int attack, int defense,int visionRange,int experienceValue) {
        super(tile, name, healthCapacity, attack, defense,experienceValue);
        this.visionRange=visionRange;
    }

    public void gameTick(){
        if(this.rangeFromCharacter < visionRange){
            int dx=this.position.getX() - playerPosition.getX();/////DX
            int dy=this.position.getY() - playerPosition.getY();/////DY
            if(Math.abs(dy)>Math.abs(dx)){//////DY>DX
                if(dy>0){//*Move left*// ///DY
                    interact(board[position.getX()][position.getY()-1],attack());
                }
                else {//*Move right*//
                    interact(board[position.getX()][position.getY()+1],attack());
                }
            }
            else{
                if(dx>0){//*Move up*// ///DX
                    interact(board[position.getX()-1][position.getY()],attack());
                }
                else {//*Move down*//
                    interact(board[position.getX()+1][position.getY()],attack());
                }
            }
        }
        else{
            Random rnd = new Random();
            int choice = rnd.nextInt(randomChoice);
            switch (choice){
                case 0:{//*Move left*//
                    interact(board[position.getX()][position.getY()-1],attack());
                    break;
                }
                case 1:{//*Move right*//
                    interact(board[position.getX()][position.getY()+1],attack());
                    break;
                }
                case 2:{//*Move up*//
                    interact(board[position.getX()-1][position.getY()],attack());
                    break;
                }
                case 3:{//*Move down*//
                    interact(board[position.getX()+1][position.getY()],attack());
                    break;
                }
                case 4:{
                    break;
                }
            }
        }
    }


}
