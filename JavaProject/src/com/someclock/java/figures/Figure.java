package com.someclock.java.figures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/25/12
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Figure extends Mesh {
    List<Side> sides = new ArrayList<Side>();

    public Figure() {
        this(8,4);
    }

    public Figure(int maxV, int maxI) {
            super(true, maxV, maxI,
                    new VertexAttribute(VertexAttributes.Usage.Position, 3,"attr_Position"),
                    new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "attr_texCoords"));
    }
    public  void paint() {
        for (Side s : sides) {
            Gdx.gl10.glColor4f(s.r(), s.g(), s.b(), 1f);
            setIndices(s.idx);
            render(GL10.GL_TRIANGLE_STRIP, 0, s.idx.length);
        }
    }

    public static class Side{
        int color;
        short[] idx;
        public Side(int c, int...i) {
            color = c;
            idx = new short[i.length];
            for (int j = 0; j < idx.length; j++)
                idx[j] = (short) i[j];

        }

        public float r(){
            return (1f*((color >> 16) & 0xff)/0xff);
        }
        public float g() {
            return (1f*((color >> 8) & 0xff)/0xff);
        }
        public float b() {
            return (1f*(color & 0xff)/0xff);
        }
    }
}
