package com.app.game.components;

import com.app.game.Game;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Hero extends GraphicObject{

    public Hero(Zone zone){
        Image image = null;
        try{
            image = new Image(new FileInputStream("images/hero.png"));
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

    public void goRight(Hero hero,Arme arme) {
        if(body.getTranslateX()<=new Game().getWindowWidth()-100)
            body.setTranslateX(body.getTranslateX()+5);
        arme.update(hero);
    }
    public void goLeft(Hero hero,Arme arme) {
        if(body.getTranslateX()>=0)
            body.setTranslateX(body.getTranslateX()-5);
        arme.update(hero);
    }
    public void goUp(Hero hero,Arme arme) {
        if(body.getTranslateY()>=240)
            body.setTranslateY(body.getTranslateY()-5);
        arme.update(hero);
    }
    public void goDown(Hero hero,Arme arme) {
        if(body.getTranslateY()<=new Game().getWindowHeight()-190)
            body.setTranslateY(body.getTranslateY()+5);
        arme.update(hero);
    }

}
