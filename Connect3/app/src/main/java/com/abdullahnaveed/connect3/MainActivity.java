package com.abdullahnaveed.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //2 means unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    boolean gameIsActive = true;

    //0=yellow, 1=red
    int activePlayer=0;

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString()); //between 0 and 8 depending on what is tapped

        if(gameState[tappedCounter]==2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer; //sets that to either yellow or red, depending on which player tapped it

            counter.setTranslationY(-1000f); //setting it off the screen

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow); //adding an image to the imageview
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red); //adding an image to the imageview
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300); //bringing it back onto the screen

            //determines winner
            for(int[] winningPosition : winningPositions){

                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    //someone has won!!

                    gameIsActive = false;

                    String winner = "Red";

                   if(gameState[winningPosition[0]]==0) {
                       winner = "Yellow";
                   }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");

                    //shows the playAgainLayout
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    layout.bringToFront();

                } else{
                    boolean gameIsOver = true;

                    for(int counterState : gameState){
                        if(counterState == 2){
                            gameIsOver = false;
                        }
                    }

                    if(gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw");

                        //shows the playAgainLayout
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                        layout.bringToFront();

                    }
                }



            }

        }
    }

    public void playAgain(View view){

        gameIsActive = true;

        //making layout invisible
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        //setting gamestate back to 2
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++){ //tells how many views there are in grid layout

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0); //sets to empty image

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
