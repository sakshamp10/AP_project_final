package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Loading implements Screen {

    final MyGdxGame game;
    SpriteBatch batch;

    Sprite image;
    Texture button;

    Sprite sprite;
    OrthographicCamera camera;

    final float viewWidth = 1920;
    final float viewHeight =  1080;

    public Loading(MyGdxGame game){
        this.game=game;
        batch = new SpriteBatch();
        image = new Sprite(new Texture("loading.png"));
        image.setPosition(0,0);
        image.setSize(viewWidth, viewHeight);
        float ratio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        camera = new OrthographicCamera(viewWidth,viewHeight);
        camera.position.set( viewWidth/2,viewHeight/2,0);
    }

//    public void create(){
//        batch = new SpriteBatch();
//        image = new Texture("loading.png");
//        button = new Texture("playBUTTON.png");
//        sprite = new Sprite(button);
//    }

    public void show(){

    }

    public void render(float delta){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        image.draw(batch);
        batch.end();
//        batch.draw(sprite, Gdx.graphics.getWidth()/2-sprite.getWidth()/2,Gdx.graphics.getHeight()/3-sprite.getHeight()/2);
        if(Gdx.input.isTouched()){
            this.dispose();
            game.setScreen(new Home(game));
//            game.setScreen(new SelectTankPlayerOne(game,1));
        }



    }
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

    public void dispose(){
        image.getTexture().dispose();
        batch.dispose();
    }
}
