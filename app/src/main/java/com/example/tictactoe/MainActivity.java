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
    private TextView message;
    private TextView messageLS;
    private Button generateAgain;
    private ArrayList<Integer> buttonTags = new ArrayList<Integer>();
    private int playerTurn;
    private int round;
    private int grid = 3;
    private int buttonTag;
    private int totalRound = grid * grid;
    private int cell;
    private boolean gameEnds;
    private boolean AI = false;

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

    private String playerSymbol(int cell){

        String text = "";

        if(cell == 1){
            text = "O";
        }
        else if(cell == 2){
            text = "X";
        }

        return text;
    }

    //For restoring button text when orientation changes
    private void restoreButtonText(){

        Button b1 = findViewById(R.id.button1);
        int vb1 = cells.get(0,0);
        if(vb1!=0) {
            b1.setText(playerSymbol(vb1));
            b1.setEnabled(false);
        }

        Button b2 = findViewById(R.id.button2);
        int vb2 = cells.get(0,1);
        if(vb2!=0) {
            b2.setText(playerSymbol(vb2));
            b2.setEnabled(false);
        }

        Button b3 = findViewById(R.id.button3);
        int vb3=  cells.get(0,2);
        if(vb3!=0){
            b3.setText(playerSymbol(vb3));
            b3.setEnabled(false);
        }

        Button b4 = findViewById(R.id.button4);
        int vb4 = cells.get(1,0);
        if(vb4!=0){
            b4.setText(playerSymbol(vb4));
            b4.setEnabled(false);
        }

        Button b5 = findViewById(R.id.button5);
        int vb5 =cells.get(1,1);
        if(vb5!=0) {
            b5.setText(playerSymbol(vb5));
            b5.setEnabled(false);
        }

        Button b6 = findViewById(R.id.button6);
        int vb6 = cells.get(1,2);
        if(vb6!=0){
            b6.setText(playerSymbol(vb6));
            b6.setEnabled(false);
        }

        Button b7 = findViewById(R.id.button7);
        int vb7 =  cells.get(2,0);
        if(vb7!=0){
            b7.setText(playerSymbol(vb7));
            b7.setEnabled(false);
        }

        Button b8 = findViewById(R.id.button8);
        int vb8 = cells.get(2,1);
        if(vb8!=0){
            b8.setText(playerSymbol(vb8));
            b8.setEnabled(false);
        }

        Button b9 = findViewById(R.id.button9);
        int vb9 = cells.get(2,2);
        if(vb9!=0){
            b9.setText(playerSymbol(vb9));
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
            //Set the grid to 5x5
            grid = 5;
            //Total round of 25
            totalRound = grid * grid;
            //Set the content view to landscape
            setContentView(R.layout.activity_main);
            //Grab the text of portrait textview to the landscape textview
            messageLS = findViewById(R.id.message);
            messageLS.setText((String)message.getText());
            //Call the function and restore all the button details that is in portrait mode
            restoreButtonText();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "Portrait mode", Toast.LENGTH_SHORT).show();
            //Set the grid to 3x3
            grid = 3;
            //When orientation from landscape back to portrait, reset the app
            finish();
            startActivity(getIntent());
        }
    }

    //When button is clicked execute the game
    public void buttonClicked(View view) {
        if (!gameEnds) {
            executeGame(view);
        }
    }

    //Run the game
    private void executeGame(View view){
        //Round + 1 when user clicks on button
        round++;
        Button button = (Button) view;

        //Textview for landscape
        TextView message = findViewById(R.id.message);
        message.getText();

        //Get the tag of the button that is clicked
        int id = Integer.parseInt(""+view.getTag());

        //Handle button1 to button14
        if(id < 15) {
            int rowIndex = (id - 1) / 3;
            int colIndex = (id - 1) % 3;
            cell = cells.get(rowIndex, colIndex);

            if(cell == 1 || cell == 2){
                Log.i("testTAG", "occupied");
                playerTurn = cell;
            }
            else{
                buttonTags.add(id);                        //Add the id into ArrayList for checking
                cells.set(rowIndex, colIndex, playerTurn); //Set the cell with player's number
                cell = cells.get(rowIndex, colIndex);      //Get the cell
                button.setText(playerSymbol(cell));        //Set the text of button
                button.setEnabled(false);                  //Disable the button for clicking
            }

        }
        //Handle button15 to button25
        else if(id >= 15){
            int rowIndex = (id - 1) % 5;
            int colIndex = (id - 1) / 5;
            cell = cells.get(rowIndex, colIndex);

            if(cell == 1 || cell == 2){
                Log.i("testTAG", "occupied");
                playerTurn = cell;
            }
            else{
                buttonTags.add(id);                        //Add the id into ArrayList for checking
                cells.set(rowIndex, colIndex, playerTurn); //Set the cell with player's number
                cell = cells.get(rowIndex, colIndex);      //Get the cell
                button.setText(playerSymbol(cell));        //Set the text of button
                button.setEnabled(false);                  //Disable the button for clicking
            }
        }

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

        if(!gameEnds){
            if(playerTurn==1){
                playerTurn=2;
                message.setText("Player 2 turns");
                AIClick(grid); //Run this function for AI to auto click
            }else{
                playerTurn=1;
                message.setText("Player 1 turns");
            }
        }
    }

    private void AIClick(int grid){

        //Run the function generateButton() to get a random generated button
        Button randomBtn = generateButton();
        //Check for the current orientation
        int orientation = getResources().getConfiguration().orientation;

        //If the current orientation is landscape set the portrait message to landscape message
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            message = messageLS;
        }

        //If the conditions is true run this block of code
        if (playerTurn == 2 && AI == true && randomBtn.isEnabled()) {

            //Get the tag of the random button generated
            int id = Integer.parseInt("" + randomBtn.getTag());

            //Handle button1 to button14
            if(id < 15) {
                //Calculate the row and column with the button id
                int rowIndex = (id - 1) / 3;
                int colIndex = (id - 1) % 3;

                //Run a auto click to represent AI with delay of 1s
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cells.set(rowIndex, colIndex, playerTurn); //Set the cell for AI
                        cell = cells.get(rowIndex, colIndex);      //Get the cell
                        randomBtn.setText(playerSymbol(cell));     //Set the text of button
                        randomBtn.setEnabled(false);               //Disable the button for clicking
                        playerTurn = 1;                            //Set the turn to 1
                        buttonTags.add(id);                        //Add the id into ArrayList for checking
                        round++;                                   //Round + 1 after AI clicks on button
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
                //Calculate the row and column with the button id
                int rowIndex = (id - 1) % 5;
                int colIndex = (id - 1) / 5;

                //Run a auto click to represent AI with delay of 1s
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cells.set(rowIndex, colIndex, playerTurn); //Set the cell for AI
                        cell = cells.get(rowIndex, colIndex);      //Get the cell
                        randomBtn.setText(playerSymbol(cell));     //Set the text of button
                        randomBtn.setEnabled(false);               //Disable the button for clicking
                        playerTurn = 1;                            //Set the turn to 1
                        buttonTags.add(id);                        //Add the id into ArrayList for checking
                        round++;                                   //Round + 1 after AI clicks on button
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
            //Generate random button again
            generateAgain = generateButton();
            //Get the tag number of the generated random button
            buttonTag = Integer.parseInt(""+generateAgain.getTag());

            //Check with the arraylist if the random generated button's tag is in it, if it is, keep generating until the number is not in the list
            while(buttonTags.contains(buttonTag)) {
                //Re-setting the variable and buttonTag until the button tag does not contain in the buttonTags arrayList
                generateAgain = generateButton();
                buttonTag = Integer.parseInt(""+generateAgain.getTag());
            }

            //When the button tag don't contain in arraylist, playerturn is 2, AI is true and button is available to click, run this function
            if(!buttonTags.contains(buttonTag) && playerTurn == 2 && AI == true && generateAgain.isEnabled()){
                //Get the tag of the random button generated
                int id = Integer.parseInt("" + generateAgain.getTag());

                //Handle button1 to button14
                if(id < 15) {
                    //Calculate the row and column with the button id
                    int rowIndex = (id - 1) / 3;
                    int colIndex = (id - 1) % 3;

                    //Run a auto click to represent AI with delay of 1s
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cells.set(rowIndex, colIndex, playerTurn); //Set the cell for AI
                            cell = cells.get(rowIndex, colIndex);      //Get the cell
                            generateAgain.setText(playerSymbol(cell)); //Set the text of button
                            generateAgain.setEnabled(false);           //Disable the button for clicking
                            playerTurn = 1;                            //Set the turn to 1
                            buttonTags.add(id);                        //Add the id into ArrayList for checking
                            round++;                                   //Round + 1 after AI clicks on button
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
                    //Calculate the row and column with the button id
                    int rowIndex = (id - 1) % 5;
                    int colIndex = (id - 1) / 5;

                    //Run a auto click to represent AI with delay of 1s
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cells.set(rowIndex, colIndex, playerTurn); //Set the cell for AI
                            cell = cells.get(rowIndex, colIndex);      //Get the cell
                            generateAgain.setText(playerSymbol(cell)); //Set the text of button
                            generateAgain.setEnabled(false);           //Disable the button for clicking
                            playerTurn = 1;                            //Set the turn to 1
                            buttonTags.add(id);                        //Add the id into ArrayList for checking
                            round++;                                   //Round + 1 after AI clicks on button
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
    }

    private Button generateButton(){
        //Create a buttonArray of 25 buttons in it
        Button[] buttonArray = new Button[25];

        //Generate and set the position with 25 different buttons
        for(int i = 0; i < buttonArray.length; i++) {
            int id = getResources().getIdentifier("button" + (i+1), "id", getPackageName());
            buttonArray[i] = (Button) findViewById(id);
        }

        int max;
        //Random module
        Random rand = new Random();
        //Random button
        Button randomBtn = null;

        //When the grid is 3 (Portrait mode)
        if(grid == 3){
            max = 8; //Set the maximum range of number for random module to 8
            int randomNo = rand.nextInt(max);  //Randomly generate position 0 to 8 button
            randomBtn = buttonArray[randomNo]; //Set the random generated position to the button
        }
        //When grid is 5 (Landscape mode)
        else if(grid == 5){
            max = 24; //Set the maximum range of number for random module to 24
            int randomNo = rand.nextInt(max);  //Randomly generate position 0 to 24 button
            randomBtn = buttonArray[randomNo]; //Set the random generated position to the button
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