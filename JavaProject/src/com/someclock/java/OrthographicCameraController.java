package com.someclock.java;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/14/12
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrthographicCameraController implements ApplicationListener, PositionDataInterface {

    static final int WIDTH  = 300;
    static final int HEIGHT = 300;

    private OrthographicCamera cam;
    private Texture texture;
    private Mesh mesh;
    private Rectangle glViewport;
    private float                           rotationSpeed;

    @Override
    public void create() {
        rotationSpeed = 0.5f;
        mesh = new Mesh(true, 4, 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 3,"attr_Position"),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "attr_texCoords"));
        System.err.println("LOCAL path: " + Gdx.files.getExternalStoragePath());
//        texture = new Texture(Gdx.files.external("sc_map.png"));
        float max = 100;
        float z = 0;
        mesh.setVertices(new float[] {
                0, 0, z, 0, 1,
                max, 0, z, 1, 1,
                0, max, z, 1, 0,
                -max,  max, z, 0, 0
        });
        mesh.setIndices(new short[] { 0, 1, 2});

        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.zoom  = 1f;
        //cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
        glViewport = new Rectangle(0, 0, WIDTH, HEIGHT);

    }


    private float angle = Float.MAX_VALUE;

    public void setAngle(float a) {
        angle = a;

    }

    @Override
    public void setAsimut(float a) {

    }

    @Override
    public void render() {
        handleInput();
        //cam.rotate(angle, 0, 1, 0);
        if (angle != Float.MAX_VALUE)
            cam.direction.set((float) Math.sqrt(1-angle*angle), 0f, angle);
        else
            cam.direction.set((float)(Math.sin(al)),0f, -(float) Math.cos(al));
        System.err.println(cam.direction + " | " + cam.position  + " " + cam.zoom);


        GL10 gl = Gdx.graphics.getGL10();
        // Camera --------------------- /
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glViewport((int) glViewport.x, (int) glViewport.y,
                (int) glViewport.width, (int) glViewport.height);

        cam.update();
        cam.apply(gl);

//        // Texturing --------------------- /
//        gl.glActiveTexture(GL10.GL_TEXTURE0);
//        gl.glEnable(GL10.GL_TEXTURE_2D);
//        texture.bind();

        mesh.render(GL10.GL_TRIANGLES);

    }

    private void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (cam.position.x > 0)
                cam.translate(-3, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (cam.position.x < 1024)
                cam.translate(3, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (cam.position.y > 0)
                cam.translate(0, -3, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (cam.position.y < 1024)
                cam.translate(0, 3, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            //cam.rotate(rotationSpeed, 0, 1, 0);
            al += .01f;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
//            cam.rotate(-rotationSpeed, 0, 1, 0);
            al -= .01f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)){
            cam.position.z -= 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)){
            cam.position.z += 3;
        }

    }

    private float al;

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