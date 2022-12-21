package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class SavedGames implements Screen {

    MyGdxGame game;
    SpriteBatch batch;
    Sprite menu,s1,s2,s3,cross,bg;
    OrthographicCamera camera;
    final float viewWidth = 1920;
    final float viewHeight = 1080;

    public SavedGames(MyGdxGame game){
        this.game = game;
        batch = new SpriteBatch();
        menu = new Sprite(new Texture("menuBG2.png"));
//        bg = new Sprite(new Texture("canyon.png"));
//        bg.setColor(0,0,0,0.4f);
//        bg.setSize(viewWidth,viewHeight);
//        bg.setPosition(0,0);
        s1 = new Sprite(new Texture("s1.png"));
        s2 = new Sprite(new Texture("s2.png"));
        s3 = new Sprite(new Texture("s3.png"));
        cross = new Sprite(new Texture("cross.png"));
        camera = new OrthographicCamera(viewWidth, viewHeight);
        camera.position.set( viewWidth/2,viewHeight/2,0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0,0,0,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(menu,2*viewWidth/3,0,viewWidth/3,viewHeight);
        batch.draw(s1,2*viewWidth/3+100,100+viewHeight/2);
        batch.draw(s2,2*viewWidth/3+100,-200+viewHeight/2);
        batch.draw(s3,2*viewWidth/3+100,-500+viewHeight/2);
        batch.draw(cross,viewWidth-100,viewHeight-100);
        batch.end();

        if(Gdx.input.isTouched()){
            Vector3 v = new Vector3();
            v.x= Gdx.input.getX();
            v.y= Gdx.input.getY();
            camera.unproject(v);
            if(v.x>1820 && v.x<=1920 && v.y>=980 && v.y<=1080){
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
        batch.dispose();
        menu.getTexture().dispose();
        s1.getTexture().dispose();
        s2.getTexture().dispose();
        s3.getTexture().dispose();
        cross.getTexture().dispose();

    }
}
