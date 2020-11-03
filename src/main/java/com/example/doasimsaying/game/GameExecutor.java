package com.example.doasimsaying.game;

import java.util.ArrayList;
import java.util.Random;

public class GameExecutor extends TheGameActivity{

    private int level = 1;
    private int scoreCount = 0;
    private int life = 3;
    private boolean newLevel = false;
    private GameObject gameObjectLight;
    private GameObject gameObjectClick;
    private ArrayList<GameObject> gameObjects;
    private GameState gameState;
    private int clickCount = 0;
    private int startLightIndex = 0;

    enum GameState {wait, start}

    public GameExecutor(){
        startLightIndex = 0;
        gameObjectLight = new GameObject();
        gameObjectClick = new GameObject();
        gameObjects = new ArrayList();
    }

    public void onTick () {

        if(gameState != GameState.wait) {
            if(startLightIndex == 0) {
                int randColor = new Random().nextInt(4);
                gameObjectLight = new GameObject();
                switch (randColor) {
                    case 0:
                        gameObjectLight.setColor(GameObject.ObjectColor.Red);
                        gameObjectLight.setLight(true);
                        break;
                    case 1:
                        gameObjectLight.setColor(GameObject.ObjectColor.Blue);
                        gameObjectLight.setLight(true);
                        break;
                    case 2:
                        gameObjectLight.setColor(GameObject.ObjectColor.Green);
                        gameObjectLight.setLight(true);
                        break;
                    case 3:
                        gameObjectLight.setColor(GameObject.ObjectColor.Yellow);
                        gameObjectLight.setLight(true);
                        break;
                }
                gameObjects.add(gameObjectLight);
            }
            gameObjectLight =  gameObjects.get(startLightIndex);
            gameObjectLight.setLight(true);
            startLightIndex++;
            if (startLightIndex == gameObjects.size())
                gameState = GameState.wait;
        }
        else
            gameObjectLight.setLight(false);

        int size = gameObjects.size();
        if (clickCount < size) {
            if (gameObjectClick.isClicked()) {
                if (gameObjectClick.getColor() != gameObjects.get(clickCount).getColor()) {
                    life--;
                } else {
                    clickCount++;
                    scoreCount++;
                    if (clickCount == size) {
                        level++;
                        newLevel = true;
                        gameState = GameState.start;
                        clickCount = 0;
                        startLightIndex = 0;
                    }
                }
                gameObjectClick.clear();
            }
        }
    }
    public GameObject getGameObjectClick()
    {
        return gameObjectClick;
    }
    public GameObject getGameObjectLight(){
        return gameObjectLight;
    }
    public int getScoreCount() {
        return scoreCount;
    }
    public boolean isGameOver(){
        return life <= 0;
    }

    public int getLife() {
        return life;
    }

    public boolean isNewLevel() {
        return newLevel;
    }

    public int getLevel() {
        return level;
    }
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
