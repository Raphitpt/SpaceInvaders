package src.com.spaceinvaders.game;


public class Enemy {
    public int positionX, positionY;
    public boolean isBoss;
    public int health;

    public Enemy(int positionX, int positionY, boolean isBoss) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isBoss = isBoss;
        this.health = isBoss ? 2 : 1;
    }
}
