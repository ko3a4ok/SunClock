package com.someclock.java;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.someclock.java.figures.Circle;
import com.someclock.java.figures.Conus;
import com.someclock.java.figures.Figure;
import com.someclock.java.figures.Piramida;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/20/12
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyCube implements ApplicationListener, PositionDataInterface {

    static final int WIDTH = 50;
    static final int HEIGHT = 50;

    private OrthographicCamera camera;
    private List<Figure> figures = new ArrayList<Figure>();

    @Override
    public void create() {
        figures.add(new Conus());
        figures.add(new Circle());
        camera = new OrthographicCamera(WIDTH / 4, HEIGHT / 4);
        camera.zoom = .3f;
        Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    private long P = 2000;

    @Override
    public void render() {
        Gdx.gl.glClearColor(.5f, 0f, 0f, .3f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        float k = 2f;
        float y = k * X;
        float x = k * Y;
        float z = k * Z;
        camera.direction.set(-x, -y, -z);
        camera.position.set(x + .5f, y + .5f, z);
        camera.update();
        camera.apply(Gdx.gl10);

//        Gdx.gl.glActiveTexture(GL10.GL_TEXTURE0);
//        Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

        for (Figure f : figures)
            f.paint();
        if (true) return;
        if (Gdx.input.justTouched()) {
            axisA = a;
            axisB = b;
        }
    }
//
//    @Override
//    public void resize(int arg0, int arg1) {
//        float aspectRatio = (float) arg0 / (float) arg1;
//        camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
//        camera.near=0.1f;
//        camera.translate(0, 0, 0);
//    }


    @Override
    public void setChord(float x, float y, float z) {
        System.err.printf("%.2f   %.2f   %.2f\n", x, y, z);
        if (camera == null) return;

        b = x * PI / 20. - axisA;
        a = y * PI / 20. - axisB;
        double ta = tan(a);
        double tb = tan(b);
        double q = sqrt(ta * ta + tb * tb + 1);
        X = (float) (ta / q);
        Y = (float) (tb / q);
        Z = (float) q;
    }

    double axisA;
    double axisB;

    float X;
    float Y;
    float Z;
    double a;
    double b;

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }
}