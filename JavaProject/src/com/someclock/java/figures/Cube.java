package com.someclock.java.figures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/25/12
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cube extends Figure {
    public Cube(){
        super();
        float max = 1;
        setVertices(new float[] {
                0,    0, 0, 0, 1,
                max,    0, 0, 1, 1,
                0,  max, 0, 1, 0,
                max,  max, 0, 0, 0,
                0,    0,   1, 0, 1,
                max,    0, 1, 1, 1,
                0,  max,   1, 1, 0,
                max,  max, 1, 0, 0,
        });
        sides.add(new Side(0xff9000, 0,1,2,3));
        sides.add(new Side(0xff0000, 4,5,6,7));
        sides.add(new Side(0xffffff, 0,4,1,5));
        sides.add(new Side(0x0000ff, 1,5,3,7));
        sides.add(new Side(0xffff00, 2,3,6,7));
        sides.add(new Side(0x00ff00, 0,4,2,6));
    }

}
