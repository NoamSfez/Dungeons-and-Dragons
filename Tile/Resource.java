package Tile;

public class Resource {
    private int healthCapacity;
    private int amount;

    public Resource(int healthCapacity,int amount){
        this.healthCapacity=healthCapacity;
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getHealthCapacity() {
        return healthCapacity;
    }

    public void setAmount(int amount) {
        if(amount>healthCapacity)
            this.amount=healthCapacity;
        else
            this.amount = amount;
    }

    public void reduceAmount(int damageDone){
        if(damageDone>=amount)
            amount=0;
        else
            amount=amount-damageDone;
    }

    public void setHealthCapacity(int healthCapacity) {
        this.healthCapacity = healthCapacity;
    }
    public void addCapacity(int healthGained){
        healthCapacity=healthCapacity+healthGained;
    }
    public void resture(){
        amount=healthCapacity;
    }
}
