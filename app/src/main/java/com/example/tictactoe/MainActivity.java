package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Matrix cells;
    private int playerTurn;
    private TextView message;
    private boolean gameEnds;
    private int round;
    private int grid = 3;
    private boolean AI = false;
    private ArrayList<Integer> buttonTags = new ArrayList<Integer>();
    private Button generateAgain;
    private int buttonTag;
    private int totalRound = grid * grid;
    private TextView messageLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("CAPTURE_TEXT");
        Log.i("testTAG", mode+"");

        if (mode.equals("Play with Computer")){
            AI = true;
        }

        cells = new Matrix(5,5);
        message = findViewById(R.id.message);
        round = 0;
        playerTurn = 1;
        gameEnds = false;
        message.setText("Player 1 turn");

        if(savedInstanceState != null){
            cells = (Matrix) savedInstanceState.getSerializable("CELLS");
            playerTurn = savedInstanceState.getInt("PLAYER_TURN");
            round = savedInstanceState.getInt("ROUND");
            gameEnds = savedInstanceState.getBoolean("GAME_ENDS");
            message.setText(savedInstanceState.getString("MESSAGE"));
            restoreButtonText();
        }
    }

    //For restoring button text when orientation changes
    private void restoreButtonText(){
        Button b1 = findViewById(R.id.button1);
        int vb1 = cells.get(0,0);
        if(vb1!=0) {
            b1.setText(vb1+"");
            b1.setEnabled(false);
        }

        Button b2 = findViewById(R.id.button2);
        int vb2 = cells.get(0,1);
        if(vb2!=0) {
            b2.setText(vb2 + "");
            b2.setEnabled(false);
        }

        Button b3 = findViewById(R.id.button3);
        int vb3=  cells.get(0,2);
        if(vb3!=0){
            b3.setText(vb3+"");
            b3.setEnabled(false);
        }

        Button b4 = findViewById(R.id.button4);
        int vb4 = cells.get(1,0);
        if(vb4!=0){
            b4.setText(vb4+"");
            b4.setEnabled(false);
        }

        Button b5 = findViewById(R.id.button5);
        int vb5 =cells.get(1,1);
        if(vb5!=0) {
            b5.setText(vb5+"");
            b5.setEnabled(false);
        }

        Button b6 = findViewById(R.id.button6);
        int vb6 = cells.get(1,2);
        if(vb6!=0){
            b6.setText(vb6+"");
            b6.setEnabled(false);
        }

        Button b7 = findViewById(R.id.button7);
        int vb7 =  cells.get(2,0);
        if(vb7!=0){
            b7.setText(vb7+"");
            b7.setEnabled(false);
        }

        Button b8 = findViewById(R.id.button8);
        int vb8 = cells.get(2,1);
        if(vb8!=0){
            b8.setText(vb8+"");
            b8.setEnabled(false);
        }

        Button b9 = findViewById(R.id.button9);
        int vb9 = cells.get(2,2);
        if(vb9!=0){
            b9.setText(vb9+"");
            b9.setEnabled(false);
        }

    }

    //When orientation change, this function runs
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape mode", Toast.LENGTH_SHORT).show();
            grid = 5;
            totalRound = grid * grid;
            //Set the content view to landscape
            setContentView(R.layout.activity_main);
            //Get the text of portrait textview to the landscape textview
            messageLS = findViewById(R.id.message);
            messageLS.setText((String)message.getText());
            //Call the function and restore all the button details
            restoreButtonText();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "Portrait mode", Toast.LENGTH_SHORT).show();
            grid = 3;
            //When orientation from landscape back to portrait, reset the app
            finish();
            startActivity(getIntent());
        }
    }


//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putSerializable("CELLS",cells);
//        savedInstanceState.putInt("PLAYER_TURN",playerTurn);
//        savedInstanceState.putInt("ROUND",round);
//        savedInstanceState.putBoolean("GAME_ENDS",gameEnds);
//        savedInstanceState.putString("MESSAGE",(String)message.getText());
//        restoreButtonText();
//    }

    //When button is clicked execute the game
    public void buttonClicked(View view) {
        Log.i("testTAG", "totalRound in human click: "+totalRound);
        if (!gameEnds) {
            executeGame(view);
        }
        else if(gameEnds){
            Log.i("testTAG", "Game ends");
        }
    }

    //Run the game
    private void executeGame(View view){
        round++;

        Button button = (Button) view;
        button.setText("" + playerTurn);
        button.setEnabled(false);

        //Textview for landscape
        TextView message = findViewById(R.id.message);
        message.getText();

        Log.i("TestTAG", "" + view.getTag());

        int id = Integer.parseInt(""+view.getTag());
        int checkCell;

        if(id < 15) {
            int rowIndex = (id - 1) / 3;
            int colIndex = (id - 1) % 3;
            checkCell = cells.get(rowIndex, colIndex);

            if(checkCell == 1 || checkCell == 2){
                Log.i("testTAG", "occupied");
                playerTurn = checkCell;
            }
            else{
                buttonTags.add(id); //Add the id to arraylist for checking if the button is available
                cells.set(rowIndex, colIndex, playerTurn);
            }

        }
        else if(id >= 15){
            int rowIndex = (id - 1) % 5;
            int colIndex = (id - 1) / 5;
            checkCell = cells.get(rowIndex, colIndex);

            if(checkCell == 1 || checkCell == 2){
                Log.i("testTAG", "occupied");
                playerTurn = checkCell;
            }
            else{
                buttonTags.add(id); //Add the id to arraylist for checking if the button is available
                cells.set(rowIndex, colIndex, playerTurn);
            }
        }

        Log.i("testTAG", "" + buttonTags);
        //1, 2, 3, 16,21
        //4, 5, 6, 17,22
        //7, 8, 9, 18,23
        //10,11,12,19,24
        //13,14,15,20,25

        //00,01,02,03,04
        //10,11,12,13,14
        //20,21,22,23,24
        //30,31,32,33,34
        //40,41,42,43,44


        int winner = checkWinner();

        if(winner==1){
            message.setText("Player 1 wins");
            gameEnds = true;
        } else if(winner==2){
            message.setText("Player 2 wins");
            gameEnds = true;
        }

        Log.i("testTAG", ""+totalRound);

        if(round==totalRound && winner==0){
            message.setText("Draw!");
            gameEnds = true;
        }

        if(!gameEnds){
            if(playerTurn==1){
                playerTurn=2;
                message.setText("Player 2 turns");
                AIClick(grid);
            }else{
                playerTurn=1;
                message.setText("Player 1 turns");
            }
        }
    }

//
    private void AIClick(int grid){

            Log.i("testTAG", "totalRound in AI click: "+totalRound);
            Button randomBtn = generateButton();
            int orientation = getResources().getConfiguration().orientation;

            if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                message = messageLS;
            }

            //If all playerturn is 2, AI is true and generated button is clickable run this
            if (playerTurn == 2 && AI == true && randomBtn.isEnabled()) {

                int id = Integer.parseInt("" + randomBtn.getTag());

                //Handle button1 to button14
                if(id < 15) {
                    int rowIndex = (id - 1) / 3;
                    int colIndex = (id - 1) % 3;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            randomBtn.setText("" + playerTurn);
                            randomBtn.setEnabled(false);
                            cells.set(rowIndex, colIndex, playerTurn);
                            playerTurn = 1;
                            buttonTags.add(id);
                            Log.i("testTAG", "" + buttonTags);
                            round++;
                            message.setText("Player 1 turns");

                            int winner = checkWinner();
                            if(winner==1){
                                message.setText("Player 1 wins");
                                gameEnds = true;
                            } else if(winner==2){
                                message.setText("Player 2 wins");
                                gameEnds = true;
                            }

                            if(round==totalRound && winner==0){
                                message.setText("Draw!");
                                gameEnds = true;
                            }
                        }
                    }, 1000);
                }
                //Handle button15 to button25
                else if(id >= 15){
                    int rowIndex = (id - 1) % 5;
                    int colIndex = (id - 1) / 5;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            randomBtn.setText("" + playerTurn);
                            randomBtn.setEnabled(false);
                            cells.set(rowIndex, colIndex, playerTurn);
                            playerTurn = 1;
                            buttonTags.add(id);
                            Log.i("testTAG", "" + buttonTags);
                            round++;
                            message.setText("Player 1 turns");

                            int winner = checkWinner();
                            if(winner==1){
                                message.setText("Player 1 wins");
                                gameEnds = true;
                            } else if(winner==2){
                                message.setText("Player 2 wins");
                                gameEnds = true;
                            }

                            if(round==totalRound && winner==0){
                                message.setText("Draw!");
                                gameEnds = true;
                            }
                        }
                    }, 1000);
                }
            }
            //If 1 of the condition (playerTurn, AI and button not clickable) is false generate another button
            else {
                //Generate random button once
                generateAgain = generateButton();
                buttonTag = Integer.parseInt(""+generateAgain.getTag());

                Log.i("testTAG", "TagNo: " + buttonTag);

                //Check with the arraylist if the generated button's tag is in it, if it is, keep generating until is does not contain any
                while(buttonTags.contains(buttonTag)) {
                    Log.i("testTAG", "generateAgain while loop triggered");
                    generateAgain = generateButton();
                    buttonTag = Integer.parseInt(""+generateAgain.getTag());
                }

                //When the button tag don't contain in arraylist, playerturn is 2, AI is true and button is available to click, run this function
                if(!buttonTags.contains(buttonTag) && playerTurn == 2 && AI == true && generateAgain.isEnabled()){
                    Log.i("testTAG", "generateAgain isEnabled triggered");

                    int id = Integer.parseInt("" + generateAgain.getTag());

                    //Handle button1 to button14
                    if(id < 15) {
                        int rowIndex = (id - 1) / 3;
                        int colIndex = (id - 1) % 3;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                generateAgain.setText("" + playerTurn);
                                generateAgain.setEnabled(false);
                                cells.set(rowIndex, colIndex, playerTurn);
                                playerTurn = 1;
                                buttonTags.add(id);
                                Log.i("testTAG", "" + buttonTags);
                                round++;
                                message.setText("Player 1 turns");


                                int winner = checkWinner();
                                if(winner==1){
                                    message.setText("Player 1 wins");
                                    gameEnds = true;
                                } else if(winner==2){
                                    message.setText("Player 2 wins");
                                    gameEnds = true;
                                }

                                if(round==9 && winner==0){
                                    message.setText("Draw!");
                                    gameEnds = true;
                                }
                            }
                        }, 1000);
                    }
                    //Handle button15 to button25
                    else if(id >= 15){
                        int rowIndex = (id - 1) % 5;
                        int colIndex = (id - 1) / 5;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                generateAgain.setText("" + playerTurn);
                                generateAgain.setEnabled(false);
                                cells.set(rowIndex, colIndex, playerTurn);
                                playerTurn = 1;
                                buttonTags.add(id);
                                Log.i("testTAG", "" + buttonTags);
                                round++;
                                message.setText("Player 1 turns");

                                int winner = checkWinner();
                                if(winner==1){
                                    message.setText("Player 1 wins");
                                    gameEnds = true;
                                } else if(winner==2){
                                    message.setText("Player 2 wins");
                                    gameEnds = true;
                                }

                                if(round==9 && winner==0){
                                    message.setText("Draw!");
                                    gameEnds = true;
                                }
                            }
                        }, 1000);
                    }
                }
            }
//        }
    }

    private Button generateButton(){
        Button[] buttonArray = new Button[25];
        buttonArray[0] = findViewById(R.id.button1);
        buttonArray[1] = findViewById(R.id.button2);
        buttonArray[2] = findViewById(R.id.button3);
        buttonArray[3] = findViewById(R.id.button4);
        buttonArray[4] = findViewById(R.id.button5);
        buttonArray[5] = findViewById(R.id.button6);
        buttonArray[6] = findViewById(R.id.button7);
        buttonArray[7] = findViewById(R.id.button8);
        buttonArray[8] = findViewById(R.id.button9);
        buttonArray[9] = findViewById(R.id.button10);
        buttonArray[10] = findViewById(R.id.button11);
        buttonArray[11] = findViewById(R.id.button12);
        buttonArray[12] = findViewById(R.id.button13);
        buttonArray[13] = findViewById(R.id.button14);
        buttonArray[14] = findViewById(R.id.button15);
        buttonArray[15] = findViewById(R.id.button16);
        buttonArray[16] = findViewById(R.id.button17);
        buttonArray[17] = findViewById(R.id.button18);
        buttonArray[18] = findViewById(R.id.button19);
        buttonArray[19] = findViewById(R.id.button20);
        buttonArray[20] = findViewById(R.id.button21);
        buttonArray[21] = findViewById(R.id.button22);
        buttonArray[22] = findViewById(R.id.button23);
        buttonArray[23] = findViewById(R.id.button24);
        buttonArray[24] = findViewById(R.id.button25);

        int max;
        Random rand = new Random();
        Button randomBtn = null;

        if(grid == 3){
            max = 8;
            int randomNo = rand.nextInt(max);
            randomBtn = buttonArray[randomNo];
            Log.i("testTAG", randomBtn + " generated");
        }
        else if(grid == 5){
            max = 24;
            int randomNo = rand.nextInt(max);
            randomBtn = buttonArray[randomNo];
            Log.i("testTAG", randomBtn + " generated");
        }

        return randomBtn;
    }

    private int checkWinner(){

        int winner = 0;
        if(checkIfWinner(1, grid)){
            winner = 1;
        }else if(checkIfWinner(2, grid)){
            winner = 2;
        }

        return winner;
    }

    private boolean checkIfWinner(int playerNumber, int grid){

        boolean win = false;
        boolean row = false;
        boolean col = false;
        boolean diag1 = false;
        boolean diag2 = false;

        //Generate row and column according to how many grid
        //and check for all rows, columns and diagonals
        for(int i = 0; i < grid; i++) {
            row = checkCond(grid, "row", playerNumber, i);
            col = checkCond(grid, "col", playerNumber, i);
            diag1 = checkCond(grid, "diag1", playerNumber, i);
            diag2 = checkCond(grid, "diag2", playerNumber, i);

            if(row || col || diag1 || diag2) {
                win = true;
                break;
            }
        }
        return win;
    }

    public boolean checkCond(int grid, String cond, int playerNumber, int i) {

        boolean condition = false;

        boolean row = (cells.get(0,i) == playerNumber && cells.get(1,i) == playerNumber && cells.get(2,i) == playerNumber);
        boolean col = (cells.get(i,0) == playerNumber && cells.get(i,1) == playerNumber && cells.get(i,2) == playerNumber);
        boolean diag1 = (cells.get(0,0) == playerNumber && cells.get(1,1) == playerNumber && cells.get(2,2) == playerNumber);
        boolean diag2 = (cells.get(grid - 1,0) == playerNumber && cells.get(grid - 2,1) == playerNumber && cells.get(grid - 3,2) == playerNumber);

        switch(cond) {
            case "row":
                if (grid == 3) { //3 condition by default
                    row = (cells.get(0,i) == playerNumber && cells.get(1,i) == playerNumber && cells.get(2,i) == playerNumber);
                }
                else { //Add condition that is more than 3 dynamically, eg. if grid is 4 there will be 4 condition

                    for(int j = 3; j < grid; j++) {
                        row &= cells.get(j, i) == playerNumber;
                    }
                }
                //Check condition again after adding new condition according to grid
                if(row == true) {
                    condition = true;
                }else {
                    condition = false;
                }

                break;

            case "col":
                if (grid == 3) { //3 condition by default
                    col = (cells.get(i,0) == playerNumber && cells.get(i,1) == playerNumber && cells.get(i,2) == playerNumber);
                }
                else {//Add condition that is more than 3 dynamically

                    for(int j = 3; j < grid; j++) {
                        col &= cells.get(i, j) == playerNumber;
                    }
                }
                //Check condition again after adding new condition according to grid
                if(col == true) {
                    condition = true;
                }else {
                    condition = false;
                }

                break;

            case "diag1":
                if (grid == 3) { //3 condition by default
                    diag1 = (cells.get(0,0) == playerNumber && cells.get(1,1) == playerNumber && cells.get(2,2) == playerNumber);
                }
                else {//Add condition that is more than 3 dynamically

                    for(int j = 3; j < grid; j++) {
                        diag1 &= cells.get(j, j) == playerNumber;
                    }
                }
                //Check condition again after adding new condition according to grid
                if(diag1 == true) {
                    condition = true;
                }else {
                    condition = false;
                }

                break;

            case "diag2":
                if (grid == 3) { //3 condition by default
                    diag2 = (cells.get(0,2) == playerNumber && cells.get(1,1) == playerNumber && cells.get(2,0) == playerNumber);
                }
                else {//Add condition that is more than 3 dynamically

                    for(int j = 4; j < grid + 1; j++) {
                        diag2 &= cells.get(grid-j, j-1) == playerNumber;
                    }
                }
                //Check condition again after adding new condition according to grid
                if(diag2 == true) {
                    condition = true;
                }else {
                    condition = false;
                }

                break;
        }

        //If the condition variable turned into true, return true for this function
        if(condition == true) {
            return true;
        }else {
            return false;
        }
    }

}