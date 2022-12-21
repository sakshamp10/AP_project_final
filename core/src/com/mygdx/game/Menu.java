package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Vector;

public class Menu implements Screen {

    final MyGdxGame game;

    Texture Settings;
    SpriteBatch batch;
    OrthographicCamera camera;


    final float viewWidth = 1920;
    final float viewHeight = 1080;
    public Menu(MyGdxGame game) {
        this.game = game;
        Settings = new Texture("Settings.png");
        camera = new OrthographicCamera(viewWidth, viewHeight);
        camera.position.set( viewWidth/2,viewHeight/2,0);
        System.out.println("MEnu");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch = new SpriteBatch();
        batch.begin();
        batch.draw(Settings, 0, viewHeight-724, 512, 362);
        batch.end();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
//        renderer.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println(touchPos.x + " " + touchPos.y);
            System.out.println(touchPos.x + " " + touchPos.y);
            if (touchPos.x > 243 && touchPos.x < 470 && touchPos.y > 886 && touchPos.y < 966) {
                this.dispose();
                game.setScreen(new inGame(game));
            }
            else if (touchPos.x > 245 && touchPos.x < 475 && touchPos.y > 590 && touchPos.y < 680) {
                this.dispose();
                game.setScreen(new Home(game));
            }

        }



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Settings.dispose();
        batch.dispose();
    }
}
