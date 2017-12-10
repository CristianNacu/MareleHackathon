package sample.model;

import javafx.scene.image.Image;

public class Tile {
    private Image image;
    private int id;
    private double layoutX;
    private double layoutY;
    private String type;
    public Tile(int id,Image image, double layoutX, double layoutY,String type) {
        this.image = image;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.type=type;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (Double.compare(tile.layoutX, layoutX) != 0) return false;
        return Double.compare(tile.layoutY, layoutY) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(layoutX);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(layoutY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(double layoutY) {
        this.layoutY = layoutY;
    }
}
