public class BattleField {

    final boolean COLORED_MODE = false;

    private String[][] field = {
            { "B", "B", " ", "B", " ", "B", " ", "B", "B" },
            { "B", " ", " ", " ", " ", " ", " ", " ", "B" },
            { "B", "B", " ", " ", "B", " ", "B", "B", "B" },
            { " ", "B", "B", " ", " ", " ", "B", "B", " " },
            { "B", " ", " ", "B", "B", " ", "B", "B", "B" },
            { "B", "B", "B", "B", "B", "B", "B", "B", " " },
            { " ", "B", " ", " ", " ", " ", " ", "B", "B" },
            { "B", " ", " ", "B", "B", "B", " ", " ", "B" },
            { " ", " ", "B", " ", " ", " ", "B", " ", " " } };

    private int width = 576;
    private int height = 576;
    private int sizeQuadrant = 64;
    private  int briksOnField;

    public int getBriksOnField() {
        return briksOnField;
    }

    public void setBriksOnField(int briksOnField) {
        this.briksOnField = briksOnField;
    }

    public int getSizeQuadrant() {
        return sizeQuadrant;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String[][] getField() {
        return field;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setSizeQuadrant(int sizeQuadrant) {
        this.sizeQuadrant = sizeQuadrant;
    }

    public int getMaxLimitX() {

        if (field != null && field.length > 0 && field[0].length > 0) {
            return (field[0].length - 1) * getSizeQuadrant();
        }
        return 0;
    }

    public int getMaxLimitY() {

        if (field != null && field.length > 0) {
            return (field.length - 1)*getSizeQuadrant();
        }

        return 0;
    }

    public int howManyBricksInField() {

        int result = 0;
        for (int i = 0; i < getField().length; i++) {
            for (int j = 0; j < getField()[i].length; j++) {
                if (!getField()[i][j].trim().isEmpty()) {
                    result++;
                }
            }
        }

        return result;
    }


}
