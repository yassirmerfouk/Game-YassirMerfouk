package com.app.game.components;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Balle extends GraphicObject {
    private Point2D direction = new Point2D(0, 0);
    public Balle(Arme arme) {
        Image bulletImage = null;
        try {
            bulletImage = new Image(new FileInputStream("images/ballon.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        body = new ImageView(bulletImage);
        body.setRotate(arme.getRotate());
        body.setTranslateX(arme.getBullet().getTranslateX()-23);
        body.setTranslateY(arme.getBullet().getTranslateY()-10);
        updateDirection(arme.getRotate());
    }
    private void updateDirection(double rotation) {
        Point2D p;
        double x = Math.cos(Math.toRadians(rotation));
        double y = Math.sin(Math.toRadians(rotation));
        p = new Point2D(x,y);
        direction = p.normalize().multiply(5);
    }
    public void update() {
        body.setTranslateX(body.getTranslateX()+direction.getX());
        body.setTranslateY(body.getTranslateY()+direction.getY());
    }
}
