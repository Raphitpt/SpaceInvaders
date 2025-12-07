package src.com.spaceinvaders.game;

public class Boss {
    public int positionX, positionY;
    public int health;
    public int maxHealth;
    public int direction; // -1 pour gauche, 1 pour droite
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
        this.shootingCooldown = 30; // Tire toutes les 30 ticks
    }

    public void updatePosition() {
        // Mouvement en zigzag
        positionX += direction * zigzagAmplitude;

        // Changement de direction aux bords
        if (positionX <= 50 || positionX >= 550) {
            direction *= -1;
        }

        // Descente progressive
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