package com.app.game;

import com.app.game.components.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    private double windowWidth = 900;
    private double windowHeight = 600;
    private Pane container = new Pane();
    private Line line = new Line(0, 250, windowWidth, 250);
    private Zone zone1 = new Zone(0,20,line.getEndX()-80,line.getEndY()-80);
    private Zone zone2 = new Zone(line.getStartX(),line.getStartY(),line.getEndX()-80,windowHeight-200);
    private Hero hero = new Hero(zone2);
    private Arme arme = new Arme(hero);
    private List<Monster> monsterList = new ArrayList<>();
    private List<Balle> balleList = new ArrayList<>();
    private List<Monster2> monster2List = new ArrayList<>();
    private int score;
    private int monsterNumber;
    private HBox box = new HBox();
    private Text scoreText = new Text("Score : "+ String.valueOf(score));
    private Text monsterText = new Text("Monster : "+ String.valueOf(monsterNumber));



    private void createContent(){
        line.setStroke(Color.TRANSPARENT);
        container.getChildren().add(line);
        container.getChildren().add(hero.getBody());
        container.getChildren().add(arme.getBody());
        box.getChildren().add(scoreText);
        box.getChildren().add(monsterText);
        box.setMargin(scoreText, new Insets(10,0,0,20));
        box.setMargin(monsterText, new Insets(10,0,0,600));
        scoreText.setFill(Color.WHITE);
        scoreText.setStyle("-fx-font: 24 arial;");
        monsterText.setFill(Color.WHITE);
        monsterText.setStyle("-fx-font: 24 arial;");
        container.getChildren().add(box);
    }
    private void refreshContent(Stage stage){
        for(Balle balle: balleList) {
            balle.update();
            for(Monster monster: monsterList) {
                if(balle.touch(monster)){
                    container.getChildren().removeAll( balle.getBody());
                    Image image = null;
                    try{
                        image = new Image(new FileInputStream("images/bom.png"));
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    ((ImageView)monster.body).setImage(image);
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> {
                        container.getChildren().remove(monster.getBody());
                    });
                    pause.play();
                    balle.setaLive(false);
                    monster.setaLive(false);
                    score++;
                    scoreText.setText("Score : "+ String.valueOf(score));
                    monsterNumber--;
                    monsterText.setText("Monster : "+ String.valueOf(monsterNumber));
                }
            }
        }
        monsterList.removeIf(GraphicObject::isDead);
        balleList.removeIf(GraphicObject::isDead);
            if (Math.random() < .02) {
                    Monster monster = new Monster(zone1);
                    container.getChildren().add(monster.getBody());
                    monsterList.add(monster);
                    monsterNumber++;
                monsterText.setText("Monster : "+ String.valueOf(monsterNumber));
                if(monsterNumber == 30){
                    Pane container = new Pane();
                    Image img = null;
                    try{
                        img = new Image("file:images/gameover.jpg");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    BackgroundImage bImg = new BackgroundImage(img,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(1.0, 1.0, true, true, false, false));
                    Background bGround = new Background(bImg);
                    container.setBackground(bGround);
                    Scene gameover = new Scene(container);
                    stage.setScene(gameover);
                    lose();
                    mediaPlayer.stop();
                    gameover.setOnKeyPressed(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent event) {
                            arme.update(hero);
                            switch (event.getCode()) {
                                case Y :
                                    try {
                                        new Game().start(stage);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    break;
                                case N : stage.close();
                                    break;
                            }
                        }
                    });
                }

            }
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Game");
        stage.getIcons().add(new Image("file:images/icon.png"));
        Thread t1 = new Thread() {
            public void run() {
                mainMusic();
            }
        };
        t1.start();
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        Scene scene = new Scene(container);
        createContent();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                arme.update(hero);
                switch (event.getCode()) {
                    case X : arme.rotateLeft();
                    break;
                    case W : arme.rotateRight();
                    break;
                    case SPACE :
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Balle balle = new Balle(arme);
                                container.getChildren().add(balle.getBody());
                                balleList.add(balle);
                                shot();
                            }
                        });
                        break;
                    case UP :
                        hero.goUp(hero,arme);
                        break;
                    case DOWN:
                        hero.goDown(hero,arme);
                        break;
                    case LEFT:
                        hero.goLeft(hero,arme);
                        break;
                    case RIGHT:
                        hero.goRight(hero,arme);
                        break;
                }
            }
        });
        Image img = null;
        try{
            img = new Image("file:images/background.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        container.setBackground(bGround);

        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                refreshContent(stage);
            }
        };
        stage.setScene(scene);
        animation.start();
        stage.setResizable(false);
        stage.show();
    }


    MediaPlayer mediaPlayer;
    public void mainMusic(){
        String s = "sounds/play.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    MediaPlayer mediaPlayer2;
    public void shot(){
        String s = "sounds/shoot.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer2 = new MediaPlayer(h);
        mediaPlayer2.play();
    }

    MediaPlayer mediaPlayer3;
    public void lose(){
        String s = "sounds/lose.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer3 = new MediaPlayer(h);
        mediaPlayer3.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
