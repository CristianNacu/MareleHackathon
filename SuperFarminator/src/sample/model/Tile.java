package sample.model;

import javafx.scene.image.Image;

public class Tile {
    private Image image;
    private double layoutX;
    private double layoutY;

    public Tile(Image image, double layoutX, double layoutY) {
        this.image = image;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
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
