import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ActionField extends JPanel{

    BattleField battleField;
    Tank tankClean;
    Bullet bullet;

    public void runTheGame() throws Exception{

        clean();

    }

    public Tank getTankClean() {
        return tankClean;
    }

    public void setTankClean(Tank tankClean) {

        this.tankClean = tankClean;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    private void clearUnderTank(Tank tank, BattleField battleField) {

        if (battleField != null && tank != null){

            String coordinates = getQuadrant(tank.getTankX(), tank.getTankY());
            int y = Integer.parseInt(coordinates.split("_")[0]);
            int x = Integer.parseInt(coordinates.split("_")[1]);

            if (x >= 0 && x <= 8 && y >= 0 && y <= 8) {
                if (!battleField.getField()[y][x].trim().isEmpty()) {
                    battleField.getField()[y][x] = " ";
                }
            }
        }
    }

    void clean() throws Exception {

        int rBrick = 3;
        Random rand = new Random();
        int countBricks = getBattleField().howManyBricksInField();
        getBattleField().setBriksOnField(countBricks);

        while (getBattleField().getBriksOnField() > 0) {

            for (TankDirection direction : TankDirection.values()) {

                turn(direction);
                shootingBrick(direction);
            }

            int r;

            if (getBattleField().getBriksOnField() > rBrick){
                r = rand.nextInt(5);
                r = r == 4 ? 1 : r;
               //System.out.println("Random direction: "+r);
                turn(TankDirection.values()[r]);
                move();
            } else {
                cleanRemainderBricks();
            }

        }
    }

    public void cleanRemainderBricks() throws Exception {

        int len = getBattleField().getField().length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < getBattleField().getField()[i].length; j++) {
                if (!getBattleField().getField()[i][j].trim().isEmpty()) {
                    moveToQuadrant(i + 1, j + 1);
                    if (howManyBricksInDirection(getTankClean().getDirection()) > 0) {
                        fire();
                    }
                }
            }
        }

    }

    public void moveToQuadrant(int v, int h) throws Exception {

        String coordinates = getQuadrantXY(v, h);

        int y = Integer.parseInt(coordinates.split("_")[0]);
        int x = Integer.parseInt(coordinates.split("_")[1]);

        boolean key = true;

        while (key) {

            if (x != getTankClean().getTankX() && x >= 0 && x <= getBattleField().getMaxLimitX()) {
                if (x > getTankClean().getTankX()) {
                    turn(TankDirection.RIGHT);
                    key = move();
                } else {
                    turn(TankDirection.LEFT);
                    key = move();
                }
            } else {
                break;
            }
        }

        key = true;

        while (key) {

            if (y != getTankClean().getTankY() && y >= 0 && y <= getBattleField().getMaxLimitY()) {
                if (y > getTankClean().getTankY()) {
                    turn(TankDirection.BOTTOM);
                    key = move();
                } else {
                    turn(TankDirection.UP);
                    key = move();
                }
            } else {
                break;
            }
        }

    }


    public void shootingBrick(TankDirection direction) throws Exception {

        int howShots = howManyBricksInDirection(direction);
        getBattleField().setBriksOnField(getBattleField().getBriksOnField() - howShots);
        while (howShots > 0) {
            fire();
            howShots--;
            if (howShots > 0) {
                move();
            }
        }

    }

    public int howManyBricksInDirection(TankDirection direction) {

        int result = 0;
        Tank tank  = getTankClean();
        String coordinates = getQuadrant(tank.getTankX(), tank.getTankY());
        int y = Integer.parseInt(coordinates.split("_")[0]);
        int x = Integer.parseInt(coordinates.split("_")[1]);

        BattleField bt = getBattleField();

        if (direction == TankDirection.UP) {
            for (int j = 0; j <= y; j++) {
                if (!bt.getField()[j][x].trim().isEmpty()) {
                    result++;
                }
            }

        } else if (direction == TankDirection.BOTTOM) {
            for (int j = y; j <= 8; j++) {
                if (!bt.getField()[j][x].trim().isEmpty()) {
                    result++;
                }
            }
        } else if (direction == TankDirection.LEFT) {
            for (int j = x; j >= 0; j--) {
                if (!bt.getField()[y][j].trim().isEmpty()) {
                    result++;
                }
            }

        } else if (direction == TankDirection.RIGHT) {
            for (int j = x; j <= 8; j++) {
                if (!bt.getField()[y][j].trim().isEmpty()) {
                    result++;
                }
            }
        }

        return result;
    }

    public ActionField() throws Exception {

        setBattleField(new BattleField());

        JFrame frame = new JFrame("BATTLE FIELD, DAY 4");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(battleField.getWidth() + 17, battleField.getHeight() + 35));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);

        setTankClean(new Tank());
        setBullet(new Bullet(getTankClean()) );
        clearUnderTank(getTankClean(),getBattleField());

    }

    public void fire() throws Exception {

        Tank tank = getTankClean();

        Bullet currentBullet = getBullet();
        currentBullet.setBulletX(tank.getTankX() + 25);
        currentBullet.setBulletY(tank.getTankY() + 25);

        int step = 5;
        TankDirection direction = tank.getDirection();

        while ((currentBullet.getBulletX() > -14 && currentBullet.getBulletX() < 590)
                && (currentBullet.getBulletY() > -14 && currentBullet.getBulletY() < 590)) {
            if (direction == TankDirection.UP) {
                bullet.setBulletY(bullet.getBulletY() - step);
            } else if (direction == TankDirection.BOTTOM) {
                bullet.setBulletY(bullet.getBulletY() + step);
            } else if (direction == TankDirection.LEFT) {
                bullet.setBulletX(bullet.getBulletX() - step);
            } else if (direction == TankDirection.RIGHT) {
                bullet.setBulletX(bullet.getBulletX() + step);
            }
            repaint();
            Thread.sleep(50);

            if (processInterception()) {
                currentBullet.setBulletX(-100);
                currentBullet.setBulletY(-100);
                repaint();
            }
        }

    }

    public boolean processInterception() {

        String coordinates = getQuadrant(bullet.getBulletX(), bullet.getBulletY());
        int y = Integer.parseInt(coordinates.split("_")[0]);
        int x = Integer.parseInt(coordinates.split("_")[1]);


        if (y >= 0 && y < 9 && x >= 0 && x < 9) {
            if (!getBattleField().getField()[y][x].trim().isEmpty()) {
                getBattleField().getField()[y][x] = " ";
                return true;
            }
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        int sizeQuadrant =  getBattleField().getSizeQuadrant();

        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (getBattleField().COLORED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * sizeQuadrant, v * sizeQuadrant, sizeQuadrant, sizeQuadrant);
            }
        }

        String [][] bf = getBattleField().getField();

        for (int j = 0; j < bf.length; j++) {
            for (int k = 0; k < bf.length; k++) {
                if (bf[j][k].equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, sizeQuadrant, sizeQuadrant);
                }
            }
        }

        int tankX = tankClean.getTankX();
        int tankY = tankClean.getTankY();
        TankDirection direction = tankClean.getDirection();

        g.setColor(new Color(255, 0, 0));
        g.fillRect(tankX, tankY, sizeQuadrant, sizeQuadrant);

        g.setColor(new Color(0, 255, 0));

        if (direction == TankDirection.UP) {
            g.fillRect(tankX + 20, tankY, 24, 34);
        } else if (direction == TankDirection.BOTTOM) {
            g.fillRect(tankX + 20, tankY + 30, 24, 34);
        } else if (direction == TankDirection.LEFT) {
            g.fillRect(tankX, tankY + 20, 34, 24);
        } else {
            g.fillRect(tankX + 30, tankY + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getBulletX(), bullet.getBulletY(), 14, 14);
    }

    private String getQuadrantXY(int v, int h) {
        return (v - 1) * getBattleField().getSizeQuadrant() + "_" + (h - 1) * getBattleField().getSizeQuadrant();
    }

    public boolean move() throws Exception {

        Tank tank = getTankClean();
        TankDirection direction =  tank.getDirection();

        if (checkLimits(direction)) {
            return false;
        }

        int step = 1;
        int covered = 0;
        turn(direction);

        while (covered < battleField.getSizeQuadrant()) {

            if (direction == TankDirection.UP) {
                tank.setTankY(tank.getTankY() - step);
            } else if (direction == TankDirection.BOTTOM) {
                tank.setTankY(tank.getTankY() + step);
            } else if (direction == TankDirection.LEFT) {
                tank.setTankX(tank.getTankX() - step);
            } else {
                tank.setTankX(tank.getTankX() + step);
            }

            covered += step;

            repaint();
            Thread.sleep(tank.getSpeedTank());
        }

        return true;

    }

    private void turn(TankDirection direction) {
        if (getTankClean().getDirection() != direction) {
            getTankClean().setDirection(direction);
        }
    }

    public boolean checkLimits(TankDirection direction) {

        Tank tankClean = getTankClean();

        if ((direction == TankDirection.UP && tankClean.getTankY() == 0)
                || (direction == TankDirection.BOTTOM && tankClean.getTankY() >= battleField.getMaxLimitY())
                || (direction == TankDirection.LEFT && tankClean.getTankX() == 0)
                || (direction == TankDirection.RIGHT && tankClean.getTankX() >= battleField.getMaxLimitX())
                || (direction.getValue() < 1 || direction.getValue() > 4)
                || nextQuadrantBrik(tankClean)) {
            return true;
        }
        return false;
    }

    private String getQuadrant(int x, int y) {

        int SIZE_QUADRANT = battleField.getSizeQuadrant();
        return y / SIZE_QUADRANT + "_" + x / SIZE_QUADRANT;
    }

    public boolean nextQuadrantBrik(Tank tank) {

        int tmpTankX = tank.getTankX();
        int tmpTankY = tank.getTankY();

        TankDirection direction = tankClean.getDirection();
        int SIZE_QUADRANT = battleField.getSizeQuadrant();

        if (direction == TankDirection.UP) {
            tmpTankY -= SIZE_QUADRANT;
        } else if (direction == TankDirection.BOTTOM) {
            tmpTankY += SIZE_QUADRANT;
        } else if (direction == TankDirection.LEFT) {
            tmpTankX -= SIZE_QUADRANT;
        } else {
            tmpTankX += SIZE_QUADRANT;
        }

        String coordinates = getQuadrant(tmpTankX, tmpTankY);
        int y = Integer.parseInt(coordinates.split("_")[0]);
        int x = Integer.parseInt(coordinates.split("_")[1]);

        if (y >= 0 && y < 9 && x >= 0 && x < 9) {
            if (!getBattleField().getField()[y][x].trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
