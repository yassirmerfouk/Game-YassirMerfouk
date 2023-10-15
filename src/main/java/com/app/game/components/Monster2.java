package com.app.game.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Monster2 extends GraphicObject{

    public Monster2(Zone zone){
        Image image = null;
        try{
            image = new Image(new FileInputStream("images/monstre2.png"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        body = new ImageView(image);
        ((ImageView)body).setX(0);
        ((ImageView)body).setY(0);
        double x = zone.getX1() + (zone.getX2() - zone.getX1()) * Math.random();
        double y = zone.getY1() + (zone.getY2() - zone.getY1()) * Math.random();
        body.setTranslateX(x);
        body.setTranslateY(y);
    }
}
