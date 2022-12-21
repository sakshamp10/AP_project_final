package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import static java.lang.System.exit;

public class Home implements Screen {

    final MyGdxGame game;
    SpriteBatch batch;
    OrthographicCamera camera;
    Sprite img2,img,frost,logo;
    Sprite bg,match,menu,exit,home;
    Sprite selectBlazer,selectFrost,selectMark;

    final float viewWidth = 1920;
    final float viewHeight = 1080;
    public Home(MyGdxGame game){
        this.game=game;
        batch= new SpriteBatch();
        home=new Sprite(new Texture("home.png"));
        img= new Sprite(new Texture("MenuBG.png"));
        img2 = new Sprite(new Texture("menuBG2.png"));
        frost = new Sprite(new Texture("frost.png"));
        logo = new Sprite(new Texture("logo.png"));
        match = new Sprite(new Texture("1v1.png"));
        menu = new Sprite(new Texture("saved games.png"));
        exit = new Sprite(new Texture("Exit.png"));
        camera = new OrthographicCamera(viewWidth, viewHeight);
        camera.position.set( viewWidth/2,viewHeight/2,0);
    }

    public void render(float delta){
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        batch.draw(img,0,0,2*viewWidth/3,viewHeight);
        batch.draw(home,0,0,2*viewWidth/3,viewHeight);

//        batch.draw(frost,viewWidth/3-290,viewHeight/3-110,viewWidth/4,viewHeight/3);
//        batch.draw(logo,viewWidth/3-190,viewHeight/3+400,450,240);
        batch.draw(img2,2*viewWidth/3,0,viewWidth/3,viewHeight);
        batch.draw(match,2*viewWidth/3+100,100+viewHeight/2);
        batch.draw(menu,2*viewWidth/3+100,viewHeight/2-200);
        batch.draw(exit,2*viewWidth/3+100,viewHeight/2-500);
        batch.end();
        if(Gdx.input.isTouched()) {
            Vector3 v = new Vector3();
            v.x = Gdx.input.getX();
            v.y = Gdx.input.getY();
            camera.unproject(v);
            System.out.println(v.x +" "+v.y);


            if (v.x >= 1382 && v.x <= 1842 && v.y <= 799 && v.y >= 645) {
                this.dispose();
                game.setScreen(new SelectTankPlayerOne(game, 1));
//                game.setScreen(new inGame(game));
//                dispose();
            }
            if (v.x >= 1382 && v.x <= 1842 && v.y <= 497 && v.y >= 345) {
                this.dispose();
                game.setScreen(new SavedGames(game));


            }
            if (v.x >= 1382 && v.x <= 1842 && v.y <= 197 && v.y >= 195) {
                //exit functionality
            }
//            game.setScreen(new inGame(game));
//            dispose();
        }
    }

    public void show(){

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
        batch.dispose();
        img.getTexture().dispose();
        img2.getTexture().dispose();
        frost.getTexture().dispose();
        logo.getTexture().dispose();
        match.getTexture().dispose();
        menu.getTexture().dispose();
        exit.getTexture().dispose();
    }
}
