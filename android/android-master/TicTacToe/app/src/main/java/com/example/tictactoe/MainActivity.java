package com.example.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    ImageButton mCellOneButton;
    ImageButton mCellTwoButton;
    ImageButton mCellThreeButton;
    ImageButton mCellFourButton;
    ImageButton mCellFiveButton;
    ImageButton mCellSixButton;
    ImageButton mCellSevenButton;
    ImageButton mCellEightButton;
    ImageButton mCellNineButton;

    TextView mResultsText;

    Button mPlayButton;

    CellPlayedStatus[] mCellPlayedStates = new CellPlayedStatus[9];

    enum GameState {
      GAME_OVER, GAME_WON, NOUGHT_TURN, CROSS_TURN
    };

    GameState mCurrentGameState = GameState.GAME_OVER;

    int goCount = 0;  // do not allow more than 9

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // listeners for each button
        mCellOneButton = findViewById(R.id.cellOneImageButton);
        mCellOneButton.setOnClickListener(this);
        setupCellButton(mCellOneButton);

        mCellTwoButton = findViewById(R.id.cellTwoImageButton);
        mCellTwoButton.setOnClickListener(this);
        setupCellButton(mCellTwoButton);

        mCellThreeButton = findViewById(R.id.cellThreeImageButton);
        mCellThreeButton.setOnClickListener(this);
        setupCellButton(mCellThreeButton);

        mCellFourButton = findViewById(R.id.cellFourImageButton);
        mCellFourButton.setOnClickListener(this);
        setupCellButton(mCellFourButton);

        mCellFiveButton = findViewById(R.id.cellFiveImageButton);
        mCellFiveButton.setOnClickListener(this);
        setupCellButton(mCellFiveButton);

        mCellSixButton = findViewById(R.id.cellSixImageButton);
        mCellSixButton.setOnClickListener(this);
        setupCellButton(mCellSixButton);

        mCellSevenButton = findViewById(R.id.cellSevenImageButton);
        mCellSevenButton.setOnClickListener(this);
        setupCellButton(mCellSevenButton);

        mCellEightButton = findViewById(R.id.cellEightImageButton);
        mCellEightButton.setOnClickListener(this);
        setupCellButton(mCellEightButton);

        mCellNineButton = findViewById(R.id.cellNineImageButton);
        mCellNineButton.setOnClickListener(this);
        setupCellButton(mCellNineButton);

        mPlayButton = findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  clear board
                clearBoard();

                mPlayButton.setVisibility(View.INVISIBLE);
                setResultText("");

                mCurrentGameState = GameState.NOUGHT_TURN;
            }
        });

        mResultsText = findViewById(R.id.resultsTextView);
        setResultText(getString(R.string.pressPlay));
        clearBoard();

        createCellState();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.cellOneImageButton:
                Log.d(TAG, "onClick cell 1");
                updateCellAsPlayed(1, mCellOneButton);
                break;
            case R.id.cellTwoImageButton:
                Log.d(TAG, "onClick cell 2");
                updateCellAsPlayed(2, mCellTwoButton);
                break;
            case R.id.cellThreeImageButton:
                Log.d(TAG, "onClick cell 3");
                updateCellAsPlayed(3, mCellThreeButton);
                break;
            case R.id.cellFourImageButton:
                Log.d(TAG, "onClick cell 4");
                updateCellAsPlayed(4, mCellFourButton);
                break;
            case R.id.cellFiveImageButton:
                Log.d(TAG, "onClick cell 5");
                updateCellAsPlayed(5, mCellFiveButton);
                break;
            case R.id.cellSixImageButton:
                Log.d(TAG, "onClick cell 6");
                updateCellAsPlayed(6, mCellSixButton);
                break;
            case R.id.cellSevenImageButton:
                Log.d(TAG, "onClick cell 7");
                updateCellAsPlayed(7, mCellSevenButton);
                break;
            case R.id.cellEightImageButton:
                Log.d(TAG, "onClick cell 8");
                updateCellAsPlayed(8, mCellEightButton);
                break;
            case R.id.cellNineImageButton:
                Log.d(TAG, "onClick cell 9");
                updateCellAsPlayed(9, mCellNineButton);
                break;
        }

    }

    void updateCellAsPlayed(int cellNumber, ImageButton cellButton) {
        Log.d(TAG, "image button" + cellNumber + " pressed");

        if (mCurrentGameState != GameState.GAME_OVER) {
            switch (mCellPlayedStates[cellNumber-1].getCurrentState()) {
                case NOT_PLAYED:
                    Log.d(TAG, "Not Played");

                    GameState stateBeforMove = mCurrentGameState;
                    if (mCurrentGameState == GameState.NOUGHT_TURN) {
                        cellButton.setImageResource(R.drawable.nought);
                        mCellPlayedStates[cellNumber - 1].setCurrentState(CellPlayedStatus.CellStatus.NOUGHT_PLAYED);
                        mCurrentGameState = GameState.CROSS_TURN;
                    }
                    else {
                        cellButton.setImageResource(R.drawable.cross);
                        mCellPlayedStates[cellNumber - 1].setCurrentState(CellPlayedStatus.CellStatus.CROSS_PLAYED);
                        mCurrentGameState = GameState.NOUGHT_TURN;
                    }

                    // check for winner
                    checkForWinner(cellNumber);
                    if (mCurrentGameState == GameState.GAME_WON) {
                        String winnerText;

                        if (stateBeforMove == GameState.NOUGHT_TURN) {
                            winnerText = getString(R.string.oWon);
                        } else {
                            winnerText = getString(R.string.xWon);
                        }
                        setResultText(winnerText);
                        resetForNewGame();
                    }
                    else {
                        goCount++;

                        if (goCount == 9) {
                            // no winners
                            setResultText(getString(R.string.noWinner));
                            resetForNewGame();
                        }
                    }
                    break;
                case CROSS_PLAYED:
                    Log.d(TAG, "cross played");
                    Toast.makeText(getApplicationContext(), R.string.xAlreadyPlayed, Toast.LENGTH_SHORT).show();
                    break;
                case NOUGHT_PLAYED:
                    Log.d(TAG, "nought played");
                    Toast.makeText(getApplicationContext(), R.string.oAlreadyPlayed, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.play, Toast.LENGTH_SHORT).show();
        }
    }

    void checkForWinner(int cellNumber) {
        switch (cellNumber) {
            case 1:
                if (checkRow(0)) {
                    break;
                } else if (checkColumn(0)) {
                    break;
                } else if (checkDiagonalLeftRightDown()) {
                    break;
                }
                break;

            case 2:
                if (checkRow(0)) {
                    break;
                } else if (checkColumn(1)) {
                    break;
                }
                break;

            case 3:
                if (checkRow(0)) {
                    break;
                } else if (checkColumn(2)) {
                    break;
                } else if (checkDiagonalLeftRightUp()) {
                    break;
                }
                break;

            case 4:
                if (checkRow(3)) {
                    break;
                } else if (checkColumn(0)) {
                    break;
                }
                break;

            case 5:
                if (checkRow(3)) {
                    break;
                } else if (checkColumn(1)) {
                    break;
                } else if (checkDiagonalLeftRightUp()) {
                    break;
                }
                break;

            case 6:
                if (checkRow(3)) {
                    break;
                } else if (checkColumn(2)) {
                    break;
                }
                break;

            case 7:
                if (checkRow(6)) {
                    break;
                } else if (checkColumn(0)) {
                    break;
                } else if (checkDiagonalLeftRightUp()) {
                    break;
                }
                break;

            case 8:
                if (checkRow(6)) {
                    break;
                } else if (checkColumn(1)) {
                    break;
                }
                break;

            case 9:
                if (checkRow(6)) {
                    break;
                } else if (checkColumn(2)) {
                    break;
                } else if (checkDiagonalLeftRightDown()) {
                    break;
                }
                break;
        }
    }

    // Look for a win by row
    boolean checkRow(int firstCell) {
        boolean allTheSame = false;

        // Check block of 3 cells in row, always from first given one
        if (mCellPlayedStates[firstCell].getCurrentState() == mCellPlayedStates[firstCell + 1].getCurrentState() &&
                mCellPlayedStates[firstCell].getCurrentState() == mCellPlayedStates[firstCell + 2].getCurrentState()) {
            allTheSame = true;

            mCurrentGameState = GameState.GAME_WON;
        }

        return allTheSame;
    }

    // Look for a win by column
    boolean checkColumn(int firstCell) {
        boolean allTheSame = false;

        // Check block of 3 cells in column, always from first given one
        if (mCellPlayedStates[firstCell].getCurrentState() == mCellPlayedStates[firstCell + 3].getCurrentState() &&
                mCellPlayedStates[firstCell].getCurrentState() == mCellPlayedStates[firstCell + 6].getCurrentState()) {
            allTheSame = true;

            mCurrentGameState = GameState.GAME_WON;
        }

        return allTheSame;
    }

    // Look for a win by diagonal up
    boolean checkDiagonalLeftRightDown() {
        boolean allTheSame = false;

        // Always same cells
        if (mCellPlayedStates[0].getCurrentState() == mCellPlayedStates[4].getCurrentState() &&
                mCellPlayedStates[0].getCurrentState() == mCellPlayedStates[8].getCurrentState()) {
            allTheSame = true;

            mCurrentGameState = GameState.GAME_WON;
        }

        return allTheSame;
    }

    // Look for a win by diagonal down
    boolean checkDiagonalLeftRightUp() {
        boolean allTheSame = false;

        // Always same cells
        if (mCellPlayedStates[6].getCurrentState() == mCellPlayedStates[4].getCurrentState() &&
                mCellPlayedStates[6].getCurrentState() == mCellPlayedStates[2].getCurrentState()) {
            allTheSame = true;

            mCurrentGameState = GameState.GAME_WON;
        }

        return allTheSame;
    }

    void setupCellButton(ImageButton cellButton) {
        cellButton.setVisibility(View.VISIBLE);
        cellButton.setBackgroundColor(Color.TRANSPARENT);

        cellButton.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    void setResultText(String text) {
        mResultsText.setText(text);
        mResultsText.setGravity(Gravity.CENTER);
    }

    void clearBoard() {
        hideButton(mCellOneButton);
        hideButton(mCellTwoButton);
        hideButton(mCellThreeButton);
        hideButton(mCellFourButton);
        hideButton(mCellFiveButton);
        hideButton(mCellSixButton);
        hideButton(mCellSevenButton);
        hideButton(mCellEightButton);
        hideButton(mCellNineButton);
    }

    void hideButton(ImageButton cellButton) {
        cellButton.setImageResource(android.R.color.transparent);
    }

    void createCellState(){
        for (int i = 0; i < 9; i++) {
            mCellPlayedStates[i] = new CellPlayedStatus(CellPlayedStatus.CellStatus.NOT_PLAYED);
        }
    }

    void resetCellStates() {
        for (int i = 0; i < 9; i++) {
            mCellPlayedStates[i].setCurrentState(CellPlayedStatus.CellStatus.NOT_PLAYED);
        }
    }

    void resetForNewGame() {
        mCurrentGameState = GameState.GAME_OVER;
        resetCellStates();
        mPlayButton.setVisibility(View.VISIBLE);
        goCount = 0;

    }
}
