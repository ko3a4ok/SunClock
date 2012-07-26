package com.someclock.java.figures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/25/12
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Circle extends Figure{
    static int n = 100;
    final Texture texture;
    public Circle() {
        super(n+1,n+1);

        float[] v = new float[5*(n+1)];

        v[0] = v[1] = 0;
        v[2] = 0;
        v[3] = v[4] = .5f;
        v[8] = v[9] = 0f;
        float r = 1.5f;
        for (int i = 0; i < n; i++) {
            v[i*5+5] = (float) (v[0] + r*Math.sin(i*2.*Math.PI/n));
            v[i*5+6] = (float) (v[1] + r*Math.cos(i * 2. * Math.PI / n));
            v[i*5+7] = v[2];
            v[i*5+8] = (float) (.5f + .5f*Math.sin(i*2.*Math.PI/n));
            v[i*5+9] = (float) (.5f + .5f*Math.cos(i * 2. * Math.PI / n));
        }


        setVertices(v);
        short[] idxs = new short[n+1];
        for (int i = 0; i < n; idxs[i++] = (short) i);
        setIndices(idxs);
        texture = new Texture(Gdx.files.internal("1853.jpg"));
    }

    @Override
    public void paint() {
        Gdx.gl10.glColor4f(1f, 1f, 1f, .1f);
        Gdx.gl.glActiveTexture(GL10.GL_TEXTURE0);
        Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
        texture.bind();
        render(GL10.GL_TRIANGLE_FAN);
        Gdx.gl.glDisable(GL10.GL_TEXTURE_2D);
    }
}
