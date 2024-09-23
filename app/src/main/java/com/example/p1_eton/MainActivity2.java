package com.example.p1_eton;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;
import java.util.List;
import java.util.Collections;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {
    boolean IS_EASY;
    int numColumns = 0;

    public enum COLOR { RED, GREEN, BLUE, YELLOW}

    boolean BALL_WAITING = false;
    COLOR WAITING_COLOR;
    Button indicatorButton;

    Stack<COLOR> col0 = new Stack<>();
    Stack<COLOR> col1 = new Stack<>();
    Stack<COLOR> col2 = new Stack<>();
    Stack<COLOR> col3 = new Stack<>();
    Stack<COLOR> col4 = new Stack<>();

    Stack<COLOR>[] stacks = new Stack[]{col0, col1, col2, col3, col4};

    Button four_0;
    Button four_1;
    Button four_2;
    Button four_3;

    Button zero_0;
    Button zero_1;
    Button zero_2;
    Button zero_3;

    Button one_0;
    Button one_1;
    Button one_2;
    Button one_3;

    Button two_0;
    Button two_1;
    Button two_2;
    Button two_3;

    Button three_0;
    Button three_1;
    Button three_2;
    Button three_3;

    Button[][] buttons = new Button[5][4];

    Button topOf0;
    Button topOf1;
    Button topOf2;
    Button topOf3;
    Button topOf4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        IS_EASY = intent.getBooleanExtra("easy", true);
        numColumns = IS_EASY ? 5 : 4;

        four_0 = findViewById(R.id.four_0);
        four_1 = findViewById(R.id.four_1);
        four_2 = findViewById(R.id.four_2);
        four_3 = findViewById(R.id.four_3);

        if(!IS_EASY) {
            four_0.setVisibility(View.INVISIBLE);
            four_0.setEnabled(false);
            four_1.setVisibility(View.INVISIBLE);
            four_1.setEnabled(false);
            four_2.setVisibility(View.INVISIBLE);
            four_2.setEnabled(false);
            four_3.setVisibility(View.INVISIBLE);
            four_3.setEnabled(false);
        }

        initializeStacks(IS_EASY);
    }

    public void initializeStacks(boolean isEasy) {
        Random rand = new Random();
        Random rand2 = new Random();

        int numColumns;

        if (isEasy) {
            numColumns = 5;
        } else {
            numColumns = 4;
        }

        int currentStack = 0;
        List<COLOR> balls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            balls.add(COLOR.RED);
            balls.add(COLOR.GREEN);
            balls.add(COLOR.BLUE);
        }
        Collections.shuffle(balls, rand);

        for (int i = 0; i < 12; i++) {
            if (stacks[currentStack].size() < 4) {
                stacks[currentStack].push(balls.remove(balls.size() - 1));
            }
            else {
                currentStack++;
                stacks[currentStack].push(balls.remove(balls.size() - 1));
            }
        }

//        Log.d("initialization:", stacks[0].toString());
//        Log.d("initialization:", stacks[1].toString());
//        Log.d("initialization:", stacks[2].toString());
//        Log.d("initialization:", stacks[3].toString());
//        Log.d("initialization:", stacks[4].toString());

        zero_0 = findViewById(R.id.zero_0);
        zero_1 = findViewById(R.id.zero_1);
        zero_2 = findViewById(R.id.zero_2);
        zero_3 = findViewById(R.id.zero_3);

        one_0 = findViewById(R.id.one_0);
        one_1 = findViewById(R.id.one_1);
        one_2 = findViewById(R.id.one_2);
        one_3 = findViewById(R.id.one_3);

        two_0 = findViewById(R.id.two_0);
        two_1 = findViewById(R.id.two_1);
        two_2 = findViewById(R.id.two_2);
        two_3 = findViewById(R.id.two_3);

        three_0 = findViewById(R.id.three_0);
        three_1 = findViewById(R.id.three_1);
        three_2 = findViewById(R.id.three_2);
        three_3 = findViewById(R.id.three_3);

        buttons[0] = new Button[]{zero_0, zero_1, zero_2, zero_3};
        buttons[1] = new Button[]{one_0, one_1, one_2, one_3};
        buttons[2] = new Button[]{two_0, two_1, two_2, two_3};
        buttons[3] = new Button[]{three_0, three_1, three_2, three_3};
        buttons[4] = new Button[]{four_0, four_1, four_2, four_3};

        // iterate thru the stacks array - basically going down each stack or column
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                COLOR currentColor = stacks[i].get(j);

                if (currentColor != null) {
//                    Log.d("initialization:", currentColor.toString());
                    switch (currentColor) {
                        case RED:
                            buttons[i][j].setBackgroundColor(Color.parseColor("#FF0000"));
                            break;
                        case GREEN:
                            buttons[i][j].setBackgroundColor(Color.parseColor("#00FF00"));
                            break;
                        case BLUE:
                            buttons[i][j].setBackgroundColor(Color.parseColor("#0000FF"));
                            break;
                    }
                }
            }
        }

        topOf0 = buttons[0][3];
        topOf1 = buttons[1][3];
        topOf2 = buttons[2][3];
        topOf3 = buttons[3][0];
        topOf4 = buttons[4][0];
    }

    public void moveBall(View v) {
        Log.d("initialization:", "==========================================================");
        int id = v.getId();
        int clickedColumn = -1;
        int clickedRow = -1;
        COLOR clickedColor;
        Button invisibleButton = findViewById(R.id.top_0);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (buttons[i][j].getId() == v.getId()) {
                    clickedColumn = i;
                    clickedRow = j;
                    // Toast.makeText(getApplicationContext(), "clicked " + clickedColumn + clickedRow, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        switch (clickedColumn) {
            case 0:
                invisibleButton = findViewById(R.id.top_0);
                break;
            case 1:
                invisibleButton = findViewById(R.id.top_1);
                break;
            case 2:
                invisibleButton = findViewById(R.id.top_2);
                break;
            case 3:
                invisibleButton = findViewById(R.id.top_3);
                break;
            case 4:
                invisibleButton = findViewById(R.id.top_4);
                break;
        }

        if (!BALL_WAITING) {
            if (stacks[clickedColumn].isEmpty()) {
                Toast.makeText(getApplicationContext(), "No ball selected - must select nonempty column!", Toast.LENGTH_SHORT).show();
                return;
            }

            BALL_WAITING = true;
            clickedColor = stacks[clickedColumn].peek();
            WAITING_COLOR = clickedColor;
            stacks[clickedColumn].pop();
            switch (clickedColumn) {
                case 0:
                    setButtonColor(topOf0, COLOR.YELLOW);

                    if (stacks[clickedColumn].size() >= 1) {
                        topOf0 = buttons[0][stacks[clickedColumn].size() - 1];
                    }

                    invisibleButton = findViewById(R.id.top_0);
                    invisibleButton.setVisibility(View.VISIBLE);
                    setButtonColor(invisibleButton, clickedColor);
                    break;
                case 1:
                    setButtonColor(topOf1, COLOR.YELLOW);

                    if (stacks[clickedColumn].size() >= 1) {
                        topOf1 = buttons[1][stacks[clickedColumn].size() - 1];
                    }

                    invisibleButton = findViewById(R.id.top_1);
                    invisibleButton.setVisibility(View.VISIBLE);
                    setButtonColor(invisibleButton, clickedColor);
                    break;
                case 2:
                    setButtonColor(topOf2, COLOR.YELLOW);

                    if (stacks[clickedColumn].size() >= 1) {
                        topOf2 = buttons[2][stacks[clickedColumn].size() - 1];
                    }

                    invisibleButton = findViewById(R.id.top_2);
                    invisibleButton.setVisibility(View.VISIBLE);
                    setButtonColor(invisibleButton, clickedColor);
                    break;
                case 3:
                    setButtonColor(topOf3, COLOR.YELLOW);

                    if (stacks[clickedColumn].size() >= 1) {
                        topOf3 = buttons[3][stacks[clickedColumn].size() - 1];
                    }

                    invisibleButton = findViewById(R.id.top_3);
                    invisibleButton.setVisibility(View.VISIBLE);
                    setButtonColor(invisibleButton, clickedColor);
                    break;
                case 4:
                    setButtonColor(topOf4, COLOR.YELLOW);

                    if (stacks[clickedColumn].size() >= 1) {
                        topOf4 = buttons[4][stacks[clickedColumn].size() - 1];
                    }

                    invisibleButton = findViewById(R.id.top_4);
                    invisibleButton.setVisibility(View.VISIBLE);
                    setButtonColor(invisibleButton, clickedColor);
                    break;
            }

            indicatorButton = invisibleButton;

            Log.d("initialization:", WAITING_COLOR.toString() + " " + clickedColumn);
            if (!validSpacesLeft(stacks, WAITING_COLOR, clickedColumn)) {
//                Toast.makeText(getApplicationContext(), "GAME LOST " + clickedColumn, Toast.LENGTH_SHORT).show();
                playAgain(false);
            }

//            Log.d("initialization:", "==== NEW COLUMNS ====");
//            Log.d("initialization:", stacks[0].toString());
//            Log.d("initialization:", stacks[1].toString());
//            Log.d("initialization:", stacks[2].toString());
//            Log.d("initialization:", stacks[3].toString());
//            Log.d("initialization:", stacks[4].toString());
//            Log.d("initialization", "top of column 0 = " + getResources().getResourceEntryName(topOf0.getId()));
//            Log.d("initialization", "top of column 1 = " + getResources().getResourceEntryName(topOf1.getId()));
//            Log.d("initialization", "top of column 2 = " + getResources().getResourceEntryName(topOf2.getId()));
//            Log.d("initialization", "top of column 3 = " + getResources().getResourceEntryName(topOf3.getId()));
//            Log.d("initialization", "top of column 4 = " + getResources().getResourceEntryName(topOf4.getId()));
        }
        else {
            if (stacks[clickedColumn].size() >= 4) {
                Toast.makeText(getApplicationContext(), "Column is full - cannot place ball here!", Toast.LENGTH_SHORT).show();
            }
            else if (!stacks[clickedColumn].isEmpty() && stacks[clickedColumn].peek() != WAITING_COLOR) {
                Toast.makeText(getApplicationContext(), "Cannot place in column with different colored ball at the top!", Toast.LENGTH_SHORT).show();
            }
            else {
                BALL_WAITING = false;
                stacks[clickedColumn].push(WAITING_COLOR);
                indicatorButton.setBackgroundColor(Color.TRANSPARENT);
                indicatorButton.setVisibility(View.INVISIBLE);

                switch (clickedColumn) {
                    case 0:
                        topOf0 = buttons[0][stacks[clickedColumn].size() - 1];
                        setButtonColor(topOf0, WAITING_COLOR);
                        break;
                    case 1:
                        topOf1 = buttons[1][stacks[clickedColumn].size() - 1];
                        setButtonColor(topOf1, WAITING_COLOR);
                        break;
                    case 2:
                        topOf2 = buttons[2][stacks[clickedColumn].size() - 1];
                        setButtonColor(topOf2, WAITING_COLOR);
                        break;
                    case 3:
                        topOf3 = buttons[3][stacks[clickedColumn].size() - 1];
                        setButtonColor(topOf3, WAITING_COLOR);
                        break;
                    case 4:
                        topOf4 = buttons[4][stacks[clickedColumn].size() - 1];
                        setButtonColor(topOf4, WAITING_COLOR);
                        break;
                }
//                Log.d("initialization:", "==== NEW COLUMNS ====");
//                Log.d("initialization:", stacks[0].toString());
//                Log.d("initialization:", stacks[1].toString());
//                Log.d("initialization:", stacks[2].toString());
//                Log.d("initialization:", stacks[3].toString());
//                Log.d("initialization:", stacks[4].toString());
//                Log.d("initialization", "top of column 0 = " + getResources().getResourceEntryName(topOf0.getId()));
//                Log.d("initialization", "top of column 1 = " + getResources().getResourceEntryName(topOf1.getId()));
//                Log.d("initialization", "top of column 2 = " + getResources().getResourceEntryName(topOf2.getId()));
//                Log.d("initialization", "top of column 3 = " + getResources().getResourceEntryName(topOf3.getId()));
//                Log.d("initialization", "top of column 4 = " + getResources().getResourceEntryName(topOf4.getId()));

                //if (validSpacesLeft(stacks, COLOR.RED, -1) || validSpacesLeft(stacks, COLOR.GREEN, -1) || validSpacesLeft(stacks, COLOR.BLUE, -1)) {
                    if (gameWon(stacks)) {
                        playAgain(true);
                    }
                //}
//                else {
//                    Toast.makeText(getApplicationContext(), validSpacesLeft(stacks, COLOR.RED, -1) + "" + validSpacesLeft(stacks, COLOR.GREEN, -1) + "" + validSpacesLeft(stacks, COLOR.BLUE, -1), Toast.LENGTH_SHORT).show();
////                    playAgain(false);
//                }
                return;
            }
        }
    }

    public boolean validSpacesLeft (Stack<COLOR>[] stacks, COLOR targetColor, int originColumn) {
        int numColumnsWithColor = 0;
        int numColumnsWithSpace = 0;

        for (int i = 0; i < numColumns; i++) {
            if (i == originColumn) {
                Log.d("initialization", "skipping over origin column " + i);
                continue;
            }

            if (stacks[i].isEmpty()) {
                Log.d("initialization", "column " + i + " is empty");
                return true;
            }

            if (columnMatched(stacks[i], targetColor)) {
                Log.d("initialization", "column " + i + " matched for " + targetColor.toString());
                return true;
            }

            if (stacks[i].peek() == targetColor && stacks[i].size() < 4) {
                Log.d("initialization", "column " + i + " has space for " + targetColor.toString());
                return true;
            }
        }

        return false;
    }

    public boolean columnMatched (Stack<COLOR> stack, COLOR colorTarget) {
        COLOR currentColor = colorTarget;
        int count = 0;

        if (!stack.isEmpty()) {
            for (COLOR color : stack) {
                if (color == currentColor) {
                    count++;
                }
                else {
                    return false;
                }
            }
        }

        if (count == 4) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean gameWon (Stack<COLOR>[] stacks) {
        COLOR currentColor;
        boolean allSameColor;
        int matchedColumns = 0;

        for (Stack<COLOR> stack : stacks) {
            if (stack.size() == 4) {
                currentColor = stack.peek();
                allSameColor = true;

                for (COLOR color : stack) {
                    if (color != currentColor) {
                        allSameColor = false;
                        break;
                    }
                }

                if (allSameColor) {
                    matchedColumns++;
                }
            }
        }
        return matchedColumns == 3;
    }

    public void setButtonColor(Button button, COLOR color) {
        switch (color) {
            case RED:
                button.setBackgroundColor(Color.parseColor("#FF0000"));
                break;
            case GREEN:
                button.setBackgroundColor(Color.parseColor("#00FF00"));
                break;
            case BLUE:
                button.setBackgroundColor(Color.parseColor("#0000FF"));
                break;
            case YELLOW:
                button.setBackgroundColor(Color.parseColor("#FFFF00"));
                break;
        }
    }

    public void playAgain(boolean gameWon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        if (gameWon) {
            builder.setMessage("You WON! Would you like to play again?");
        }
        else {
            builder.setMessage("You LOST! Would you like to play again?");
        }

        builder.setTitle("Done");
        builder.setCancelable(false);

        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
