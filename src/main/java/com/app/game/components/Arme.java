package com.app.game.components;

import com.app.game.Game;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Arme extends GraphicObject{
    private ImageView bullet;

    public Arme(Hero hero){
        Image imageArme = null;
        Image bulletImage = null;
        try {
            imageArme = new Image(new FileInputStream("images/Arme.png"));
            bulletImage = new Image(new FileInputStream("images/ballon.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        body = new ImageView(imageArme);
        bullet = new ImageView(bulletImage);
        update(hero);
        updateBullet();
    }

    public ImageView getBullet() {
        return bullet;
    }
    public double getRotate() {
        return body.getRotate()-90;
    }

    public void update(Hero hero) {
        body.setTranslateX(hero.getBody().getTranslateX()+20);
        body.setTranslateY(hero.getBody().getTranslateY());
        updateBullet();
    }

    private void updateBullet() {
        bullet.setTranslateX(body.getTranslateX()+20);
        bullet.setTranslateY(body.getTranslateY()+15);
    }

    public void rotateRight() {
        body.setRotate(body.getRotate()-5);
        updateBullet();
    }
    public void rotateLeft() {
        body.setRotate(body.getRotate()+5);
        updateBullet();
    }


}
