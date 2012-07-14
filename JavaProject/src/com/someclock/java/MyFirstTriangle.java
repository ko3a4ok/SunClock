package com.someclock.java;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/14/12
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyFirstTriangle implements ApplicationListener {
    private Mesh mesh;

    @Override
    public void create() {
        if (mesh == null) {
            mesh = new Mesh(true, 3, 3,
                    new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"));

            mesh.setVertices(new float[] { -0.5f, -0.5f, 0,
                    0.5f, -0.5f, 0,
                    0, 0.5f, 0 });
            mesh.setIndices(new short[] { 0, 1, 2 });
        }
    }

    @Override
    public void dispose() { }

    @Override
    public void pause() { }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        mesh.render(GL10.GL_TRIANGLES, 0, 3);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void resume() { }}
