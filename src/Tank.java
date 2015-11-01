
/**
 * Created by admin on 27.10.2015.
 */
public class Tank {

    private int tankX;
    private int tankY;
    private TankDirection direction;
    private int speedTank;

    public Tank() {

        setTankX(0);
        setTankY(512);
        setDirection(TankDirection.UP);
        setSpeedTank(10);
    }

    public int getSpeedTank() {
        return speedTank;
    }

    public void setSpeedTank(int speedTank) {
        this.speedTank = speedTank;
    }

    public TankDirection getDirection() {
        return direction;
    }

    public void setDirection(TankDirection direction) {
        this.direction = direction;
    }


    public int getTankX() {
        return tankX;
    }

    public void setTankX(int tankX) {
        this.tankX = tankX;
    }

    public int getTankY() {
        return tankY;
    }

    public void setTankY(int tankY) {
        this.tankY = tankY;
    }


}
