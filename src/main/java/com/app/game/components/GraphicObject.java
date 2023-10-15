package com.app.game.components;

import javafx.scene.Node;

public class GraphicObject {
    public Node body;
    protected boolean aLive = true;

    public boolean isaLive() {
        return aLive;
    }

    public Node getBody(){
        return body;
    }

    public void setBody(Node body) {
        this.body = body;
    }

    public void setaLive(boolean aLive) {
        this.aLive = aLive;
    }

    public boolean isDead() {
        return !aLive;
    }

    public boolean touch(GraphicObject graphicObject) {
        return body.getBoundsInParent().intersects(graphicObject.getBody().getBoundsInParent());
    }
}
