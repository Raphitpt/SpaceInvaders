package src.com.spaceinvaders.game;


public class Enemy {
    public int x, y;
    public boolean isBoss;
    public int health;

    public Enemy(int x, int y, boolean isBoss) {
        this.x = x;
        this.y = y;
        this.isBoss = isBoss;
        this.health = isBoss ? 2 : 1;
    }
}
