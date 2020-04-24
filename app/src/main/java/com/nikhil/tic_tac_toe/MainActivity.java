package com.nikhil.tic_tac_toe;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    public String player1 ="Player1";
    public String player2 ="Player2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        getplayername();

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> resetGame());
    }

    private void getplayername() {
        //briefinfo
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("X & O");
        builder.setCancelable(false);
        builder.setMessage("Welcome to X & O.\nPlease enter player's name!!");
        builder.setIcon(R.drawable.tictactoe);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.cancel();
            showCheckDialog1();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//player 2 name
    private void showCheckDialog2() {
        final Dialog dialog = new Dialog(MainActivity.this,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(R.layout.playername);
        Button bt1 = dialog.findViewById(R.id.btn1);
        EditText playernameedt = dialog.findViewById(R.id.playername);
        playernameedt.setHint("Player2 Name");
        bt1.setOnClickListener(v -> {
            if( playernameedt.getText().toString().trim().length() == 0)
                playernameedt.setError("Please enter player's name!");
            else
                player2 = playernameedt.getText().toString().trim();
                textViewPlayer2.setText(player2+":");
                dialog.cancel();
        });
    }

//player 1 name
    private void showCheckDialog1() {

        final Dialog dialog = new Dialog(MainActivity.this,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(R.layout.playername);
        Button bt1 = dialog.findViewById(R.id.btn1);
        EditText playernameedt = dialog.findViewById(R.id.playername);
        playernameedt.setHint("Player1 Name");
        bt1.setOnClickListener(v -> {
            if( playernameedt.getText().toString().trim().length() == 0)
                playernameedt.setError("Please enter player's name!");
            else
                player1 = playernameedt.getText().toString().trim();
                textViewPlayer1.setText(player1+":");
                dialog.cancel();
                showCheckDialog2();
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, player1 + " won!", Toast.LENGTH_LONG).show();
        updatePointsText();
        showrematchdialog();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, player2 +" won!", Toast.LENGTH_LONG).show();
        updatePointsText();
        showrematchdialog();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        showrematchdialog();
    }

    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        textViewPlayer1.setText(player1 + " : " + player1Points);
        textViewPlayer2.setText(player2 +" : " + player2Points);
    }

    private void clearboard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void showrematchdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Play Again?");
        builder.setCancelable(false);
        builder.setMessage("Do you want to play again with your friend?");
        builder.setPositiveButton("Yes",(dialog, which) ->{
            dialog.cancel();
            clearboard();
        });
        builder.setNegativeButton("No",(dialog, which) -> exit());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void exit() {
        finish();
        System.exit(0);
    }


    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        clearboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}