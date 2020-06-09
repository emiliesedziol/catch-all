package com.example.tictactoe;

public class CellPlayedStatus {

    enum CellStatus {NOT_PLAYED, NOUGHT_PLAYED, CROSS_PLAYED };

    private CellStatus mCurrentState = CellStatus.NOT_PLAYED;

    CellPlayedStatus(CellStatus state) {
        mCurrentState = state;
    }

    public void setCurrentState(CellStatus cellState) {
        mCurrentState = cellState;
    }

    public CellStatus getCurrentState() {

        return mCurrentState;
    }
}
