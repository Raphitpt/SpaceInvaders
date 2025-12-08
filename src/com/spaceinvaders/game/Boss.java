package src.com.spaceinvaders.game;

public class Boss {
    public int positionX, positionY;
    public int health;
    public int maxHealth;
    public int direction;
    public int zigzagAmplitude;
    public int ticksSinceLastShot;
    public int shootingCooldown;

    public Boss(int positionX, int positionY, int health) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.health = health;
        this.maxHealth = health;
        this.direction = 1;
        this.zigzagAmplitude = 5;
        this.ticksSinceLastShot = 0;
        this.shootingCooldown = 30;
    }

    public void updatePosition() {
        positionX += direction * zigzagAmplitude;

        if (positionX <= 50 || positionX >= 550) {
            direction *= -1;
        }

        if (Math.random() < 0.1) {
            positionY += 5;
        }
    }

    public boolean canShoot() {
        ticksSinceLastShot++;
        if (ticksSinceLastShot >= shootingCooldown) {
            ticksSinceLastShot = 0;
            return true;
        }
        return false;
    }

    public void takeDamage() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}