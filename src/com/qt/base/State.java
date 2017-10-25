package com.qt.base;

public class State {
    private static State state=new State();

    private boolean debug=false;
    public static State instance(){
        return state;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }
}
