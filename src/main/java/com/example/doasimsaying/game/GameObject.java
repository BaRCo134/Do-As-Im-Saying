package com.example.doasimsaying.game;

public class GameObject{

    private ObjectColor color;
    private boolean clicked = false;
    private boolean light = false;

    public GameObject() {
        color = ObjectColor.Red;
    }

    public void clear(){
        clicked = false;
        light = false;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isLight() {
        return light;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    enum ObjectColor {Red, Blue, Yellow, Green};

    public void setColor(ObjectColor color) {   this.color = color; }

    public ObjectColor getColor() {     return color;  }

}
