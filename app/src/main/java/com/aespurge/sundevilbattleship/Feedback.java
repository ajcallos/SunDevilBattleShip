package com.aespurge.sundevilbattleship;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aespurge.sundevilbattleship.Game.ships.Warship;

/**
 * Created by Anthony on 11/21/2015.
 */
public class Feedback {

    TextView feedbackText;

    Feedback(TextView feedbackText) {
        this.feedbackText = feedbackText;
    }
    public void hitShip(Warship ship, boolean isEnemy){
        if(isEnemy)
            feedbackText.setText("You hit the enemy's " + ship.getType() + "!");
        else
            feedbackText.setText("The enemy hit your " + ship.getType() + "!");
    }

    public void sankShip(Warship ship, boolean isEnemy){
        if(isEnemy)
            feedbackText.setText("You sank the enemy's " + ship.getType() + "!");
        else
            feedbackText.setText("The enemy sank your " + ship.getType() + "!");
    }

    public void hitWater(boolean isEnemy){
        if(isEnemy)
            feedbackText.setText("You missed! Oops!");
        else
            feedbackText.setText("The enemy missed! Hah!");
    }
    public void myTurn(){
        feedbackText.setText("It's your turn! Choose a target!");
    }
}
