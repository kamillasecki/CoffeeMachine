package com.klasecki;

public enum MachineState {
    MAIN(1),
    DEFAULT(1),
    BUY(1),
    FILL(1),
    TAKE(1),
    REMAINING(1),
    EXIT(1);

    int stage;

    MachineState (int stage) {
        this.stage = stage;
    }

    public int getStage () {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
