package com.someclock.java.figures;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/25/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Piramida extends Figure {
    public Piramida() {
        super();
        float max = .1f;
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
        sides.add(new Side(0xff9000, 0,1,2));
        sides.add(new Side(0xff0000, 0,1,4));
        sides.add(new Side(0xffffff, 0,2,4));
        sides.add(new Side(0x0000ff, 1,2,4));
//        sides.add(new Side(0xffff00, 2,3,6,7));
//        sides.add(new Side(0x00ff00, 0,4,2,6));
    }
}
