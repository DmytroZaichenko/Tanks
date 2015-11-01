/**
 * Created by admin on 27.10.2015.
 */
public class Bullet {

    private int bulletX = -100;
    private int bulletY = -100;

    private int speed = 10;
    private int bulletSpeed = 50;

    public int getBulletX() {
        return bulletX;
    }

    public void setBulletX(int bulletX) {
        this.bulletX = bulletX;
    }

    public int getBulletY() {
        return bulletY;
    }

    public void setBulletY(int bulletY) {
        this.bulletY = bulletY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public Bullet() {
    }

    public Bullet(Tank tank){
        setBulletX(tank.getTankX() + 25);
        setBulletY(tank.getTankY() + 25);
    }



}
