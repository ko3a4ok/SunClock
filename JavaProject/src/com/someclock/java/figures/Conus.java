package com.someclock.java.figures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/26/12
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Conus extends Figure {
    static int n = 100;

        public Conus() {
            super(n+1,n+2);

            float[] v = new float[5*(n+1)];

            v[0] = v[1] = 0;
            v[2] = 1f;
            float r = .05f;
            for (int i = 0; i < n; i++) {
                v[i*5+5] = (float) (v[0] + r*Math.sin(i*2.*Math.PI/n));
                v[i*5+6] = (float) (v[1] + r*Math.cos(i * 2. * Math.PI / n));
                v[i*5+7] = 0;
            }
            setVertices(v);
            short[] idxs = new short[n+2];
            for (int i = 0; i < idxs.length-1; idxs[i] = (short) i++);
            setIndices(idxs);
        }

        @Override
        public void paint() {
            Gdx.gl10.glColor4f(0,0,0, 1f);
            render(GL10.GL_TRIANGLE_FAN);
        }
}