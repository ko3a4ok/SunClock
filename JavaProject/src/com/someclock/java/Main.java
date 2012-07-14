package com.someclock.java;

import com.badlogic.gdx.backends.jogl.JoglApplication;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/14/12
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public String getSome(int i) {
        return "result is " + i;
    }

    public static void main (String[] args) {
        new JoglApplication(new OrthographicCameraController(), "Game", 480, 320, false);
    }
}
