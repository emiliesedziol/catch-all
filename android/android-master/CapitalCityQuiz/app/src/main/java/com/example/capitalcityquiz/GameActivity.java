package com.example.capitalcityquiz;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {

    static final String CURRENT_QUESTION_INDEX = "index";
    static final String CURRENT_CORRECT_SCORE = "correct_score";
    static final String CURRENT_WRONG_SCORE = "wrong_score";
    static final String CURRENT_SKIP_SCORE = "skip_score";

    QuizQuestion[] mQuestion = new QuizQuestion[5];

    int mNumberOfQuestions = 0;
    int mNumberSkippedAnswers = 0;
    int mNumberCorrectAnswers = 0;
    int mNumberOfWrongAnswers = 0;
    int mCurrentQuestionIndex = 0;

    TextView mScoreTextView;
    TextView mQuestionTextView;
    TextView mProgressTextView;
    Button mPlayAgainButton;
    Button mAbortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialiseQuestions();

        mQuestionTextView = findViewById(R.id.questionTextView);
        mProgressTextView = findViewById(R.id.progressTextView);

        // if the phone is rotated a new instance is created
        // the following is to use the saved instance created before rotation
        if (savedInstanceState != null) {

            restoreData(savedInstanceState);
        }
        updateQuestion();
        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handelQuestionSkipped();
            }
        });

        mAbortButton = findViewById(R.id.abortButton);
        mAbortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopQuiz();
            }
        });

        mPlayAgainButton = findViewById(R.id.playAgainButton);
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
                updateProgress();
                updateScores(false);
                hidePlayAgainButton();
                enableAbortButton();
            }
        });
        mScoreTextView = findViewById(R.id.scoreTextView);

        updateProgress();
        updateScores(false);

        hidePlayAgainButton();
        enableAbortButton();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // persist data

        persistData(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    void initialiseQuestions() {
        mQuestion[0] = new QuizQuestion("Is Paris the capital of France?", true );

        mQuestion[1] = new QuizQuestion("Is Barcelona the capital of Spain?", false );

        mQuestion[2] = new QuizQuestion("Lisbon is the capital of Sweden",false);

        mQuestion[3] = new QuizQuestion("Is Brussels a city in Belgium",true);

        mQuestion[4] = new QuizQuestion("Is New York the capital of the US",false);


        mNumberOfQuestions = mQuestion.length;
    }

    void checkAnswer(boolean userAnswered) {
        if (mQuestion[mCurrentQuestionIndex].getAnswer() == userAnswered) {
            mNumberCorrectAnswers++;
        }
        else {
            mNumberOfWrongAnswers++;
        }
        moveToNextQuestion();
    }

    void moveToNextQuestion() {
        mCurrentQuestionIndex++;
        if (mCurrentQuestionIndex > (mNumberOfQuestions -1)) {
            updateScores(true);
            newGame();
        }
        else {
            updateQuestion();
            updateProgress();
            updateScores(false);
        }

    }

    void updateScores(boolean quizOver) {
        String scoreText;
        if (quizOver) {
            scoreText = getResources().getString(R.string.final_score);
        }
        else {
            scoreText = getResources().getString(R.string.current_score);
        }
        scoreText = scoreText + "\n\n" + getResources().getString(R.string.correct_answers)
                + Integer.toString(mNumberCorrectAnswers) +
            "\n" + getResources().getString(R.string.wrong_answers)
                + Integer.toString(mNumberOfWrongAnswers)+
            "\n" + getResources().getString(R.string.skipped_answers)
                + Integer.toString(mNumberSkippedAnswers);
        mScoreTextView.setText(scoreText);
    }

    void updateQuestion() {
        mQuestionTextView.setText(mQuestion[mCurrentQuestionIndex].getQuestion());
    }

    void updateProgress() {
        String progress = getResources().getString(R.string.question) +
                Integer.toString(mCurrentQuestionIndex + 1) +
                getResources().getString(R.string.delimiter)+
                Integer.toString(mNumberOfQuestions);
        mProgressTextView.setText(progress);
    }

    void newGame() {
        mNumberCorrectAnswers = 0;
        mNumberOfWrongAnswers = 0;
        mNumberSkippedAnswers = 0;
        mCurrentQuestionIndex = 0;
        showPlayAgainButton();
        disableAbortButton();
    }

    void handelQuestionSkipped() {
        mNumberSkippedAnswers++;
        moveToNextQuestion();
    }

    void stopQuiz() {
        // Make sure user wants to quit
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        String abortText = getResources().getString(R.string.quiz_aborted);
                        mScoreTextView.setText(abortText);
                        newGame();

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage(getResources().getString(R.string.abort_confirmation))
                .setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }

    void hidePlayAgainButton() {
        mPlayAgainButton.setVisibility(View.INVISIBLE);
    }
    void showPlayAgainButton() {
        mPlayAgainButton.setVisibility(View.VISIBLE);
    }

    void  enableAbortButton() {
        mAbortButton.setClickable(true);
        mAbortButton.setAlpha(1.0f);
    }
    void disableAbortButton() {
        mAbortButton.setClickable(false);
        mAbortButton.setAlpha(0.25f);
    }

    void persistData(Bundle savedInstanceData) {

        //Save scores and index for the current question into instance data
        savedInstanceData.putInt(CURRENT_QUESTION_INDEX, mCurrentQuestionIndex);
        savedInstanceData.putInt(CURRENT_CORRECT_SCORE, mNumberCorrectAnswers);
        savedInstanceData.putInt(CURRENT_WRONG_SCORE, mNumberOfWrongAnswers);
        savedInstanceData.putInt(CURRENT_SKIP_SCORE, mNumberSkippedAnswers);

    }

    void restoreData(Bundle saveInstanceData) {
        mCurrentQuestionIndex = saveInstanceData.getInt(CURRENT_QUESTION_INDEX);
        mNumberCorrectAnswers = saveInstanceData.getInt(CURRENT_CORRECT_SCORE);
        mNumberOfWrongAnswers = saveInstanceData.getInt(CURRENT_WRONG_SCORE);
        mNumberSkippedAnswers = saveInstanceData.getInt(CURRENT_SKIP_SCORE);
    }
}
