package com.example.doasimsaying.game;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doasimsaying.R;
import com.example.doasimsaying.databinding.TheGameActivityBinding;
import com.example.doasimsaying.scores.ScoresActivity;

import java.util.Arrays;

public class TheGameActivity extends AppCompatActivity {
    private GameExecutor gameExecutor;
    private TheGameActivityBinding theGameActivityBinding;
    static MediaPlayer clickSound;
    private long timeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theGameActivityBinding = TheGameActivityBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        setContentView(theGameActivityBinding.getRoot());
        clickSound = MediaPlayer.create(TheGameActivity.this, R.raw.click_sound);

        startGame();
    }
    @Override
    public void onBackPressed() {
        if(timeBack + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(this, "Click back twice to go home", Toast.LENGTH_SHORT).show();
        }
        timeBack = System.currentTimeMillis();
    }

    private void startGame() {
        this.gameExecutor = new GameExecutor();
        new GameTask().execute();
    }

    private class GameTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground (Void [] objects) {

            gameExecutor.onTick();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            char [] life = new char[gameExecutor.getLife()];
            Arrays.fill(life, '❤');
            super.onPostExecute(aVoid);
            theGameActivityBinding.scoreTxtV.setText(getString(R.string.game_score_format, gameExecutor.getScoreCount()));
            theGameActivityBinding.lifeTxtV.setText(getString(R.string.game_life_format, String.copyValueOf(life)));
            if(gameExecutor.isNewLevel()) {
                theGameActivityBinding.levelsTxtV.setText("LEVEL " + gameExecutor.getLevel());
                theGameActivityBinding.levelsTxtV.setVisibility(View.VISIBLE);
            } else{
                theGameActivityBinding.levelsTxtV.setVisibility(View.INVISIBLE);
            }
            GameObject gameObject = gameExecutor.getGameObjectLight();
            if(gameObject.isLight())
                switch (gameObject.getColor()) {
                    case Red:
                        theGameActivityBinding.redBtn.setImageDrawable(getResources().getDrawable(R.drawable.red_clicked));
                        theGameActivityBinding.blueBtn.setImageDrawable(getResources().getDrawable(R.drawable.blue));
                        theGameActivityBinding.greenBtn.setImageDrawable(getResources().getDrawable(R.drawable.green));
                        theGameActivityBinding.yellowBtn.setImageDrawable(getResources().getDrawable(R.drawable.yellow));

                        break;
                    case Blue:
                        theGameActivityBinding.redBtn.setImageDrawable(getResources().getDrawable(R.drawable.red));
                        theGameActivityBinding.blueBtn.setImageDrawable(getResources().getDrawable(R.drawable.blue_clicked));
                        theGameActivityBinding.greenBtn.setImageDrawable(getResources().getDrawable(R.drawable.green));
                        theGameActivityBinding.yellowBtn.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
                        break;
                    case Green:
                        theGameActivityBinding.redBtn.setImageDrawable(getResources().getDrawable(R.drawable.red));
                        theGameActivityBinding.blueBtn.setImageDrawable(getResources().getDrawable(R.drawable.blue));
                        theGameActivityBinding.greenBtn.setImageDrawable(getResources().getDrawable(R.drawable.green_clicked));
                        theGameActivityBinding.yellowBtn.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
                        break;
                    case Yellow:
                        theGameActivityBinding.redBtn.setImageDrawable(getResources().getDrawable(R.drawable.red));
                        theGameActivityBinding.blueBtn.setImageDrawable(getResources().getDrawable(R.drawable.blue));
                        theGameActivityBinding.greenBtn.setImageDrawable(getResources().getDrawable(R.drawable.green));
                        theGameActivityBinding.yellowBtn.setImageDrawable(getResources().getDrawable(R.drawable.yellow_clicked));
                        break;
                }
            else{
                theGameActivityBinding.redBtn.setImageDrawable(getResources().getDrawable(R.drawable.red));
                theGameActivityBinding.blueBtn.setImageDrawable(getResources().getDrawable(R.drawable.blue));
                theGameActivityBinding.greenBtn.setImageDrawable(getResources().getDrawable(R.drawable.green));
                theGameActivityBinding.yellowBtn.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
            }

            if (gameExecutor.isGameOver()) {
                GameOverDialog gameOverDialog = new GameOverDialog(TheGameActivity.this, new GameDialogListener());
                gameOverDialog.show();
            }
            else
                new Handler().postDelayed(()->new GameTask().execute(), 500);
        }
    }

    public static void click(){
        clickSound.start();
        if(!clickSound.isPlaying())
            clickSound.release();
    }

    public void onRedClick(View view) {
        gameExecutor.getGameObjectClick().setClicked(true);
        gameExecutor.getGameObjectClick().setColor(GameObject.ObjectColor.Red);
        ImageButton redBtn = findViewById(R.id.redBtn);
        redBtn.setImageDrawable(getResources().getDrawable(R.drawable.red_clicked));
        click();
    }
    public void onBlueClick(View view) {
        gameExecutor.getGameObjectClick().setClicked(true);
        gameExecutor.getGameObjectClick().setColor(GameObject.ObjectColor.Blue);
        ImageButton blueBtn = findViewById(R.id.blueBtn);
        blueBtn.setImageDrawable(getResources().getDrawable(R.drawable.blue_clicked));
        click();
    }
    public void onGreenClick(View view) {
        gameExecutor.getGameObjectClick().setClicked(true);
        gameExecutor.getGameObjectClick().setColor(GameObject.ObjectColor.Green);
        ImageButton greenBtn = findViewById(R.id.greenBtn);
        greenBtn.setImageDrawable(getResources().getDrawable(R.drawable.green_clicked));
        click();
    }
    public void onYellowClick(View view) {
        gameExecutor.getGameObjectClick().setClicked(true);
        gameExecutor.getGameObjectClick().setColor(GameObject.ObjectColor.Yellow);
        ImageButton yellowBtn = findViewById(R.id.yellowBtn);
        yellowBtn.setImageDrawable(getResources().getDrawable(R.drawable.yellow_clicked));
        click();
    }

    private class GameDialogListener implements GameOverDialog.Listener {
        @Override
        public void onHomeClick() {
            finish();
        }

        @Override
        public void onScoresClick(String name) {
            Intent scoresIntent = new Intent(TheGameActivity.this, ScoresActivity.class);
            scoresIntent.putExtra(ScoresActivity.EXTRA_NAME, name);
            scoresIntent.putExtra(ScoresActivity.EXTRA_SCORE, gameExecutor.getScoreCount());
            startActivity(scoresIntent);
            finish();
        }
    }
}
