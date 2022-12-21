package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class SelectTankPlayerOne implements Screen {

    final MyGdxGame game;
    SpriteBatch batch;
    OrthographicCamera camera;
    Sprite img2;
    Sprite selectBlazer,selectFrost,selectMark;
    Sprite chooseBlazer,chooseFrost,chooseMark;
    Sprite chooseTank;

    Sprite playButton;
    ShapeRenderer renderer;

    Circle blazerCircle,frostCircle,markCircle;
    Rectangle playerOneRectangle;

    int count = 1;
    final float viewWidth = 1920;
    final float viewHeight = 1080;

    int playerOneTank = 0;
    int vsPlayer;
    public SelectTankPlayerOne(MyGdxGame game, int vsPlayer){

        this.game=game;
        this.vsPlayer = vsPlayer;
        batch= new SpriteBatch();
        img2 = new Sprite(new Texture("menuBG2.png"));
        renderer = new ShapeRenderer();
        chooseBlazer = new Sprite(new Texture("chooseBlazer.png"));
        chooseFrost = new Sprite(new Texture("chooseFrost.png"));
        chooseMark = new Sprite(new Texture("chooseMark.png"));

        selectBlazer = new Sprite(new Texture("SelectBlazer.png"));
        selectFrost = new Sprite(new Texture("SelectFrost.png"));
        selectMark = new Sprite(new Texture("SelectMark.png"));

        chooseTank = new Sprite(new Texture("choose.png"));
        playButton = new Sprite(new Texture("playbutton.png"));

        camera = new OrthographicCamera(viewWidth, viewHeight);
        camera.position.set( viewWidth/2,viewHeight/2,0);
        blazerCircle = new Circle(2*viewWidth/3+325,viewHeight/2+200,75);
        frostCircle = new Circle(2*viewWidth/3+325,viewHeight/2,75);
        markCircle = new Circle(2*viewWidth/3+325,viewHeight/2-200,75);
        playerOneRectangle = new Rectangle(2*viewWidth/3+200,viewHeight/2-500, 250, 100);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glLineWidth(4);
        ScreenUtils.clear(0,0,0,1);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        renderer.setProjectionMatrix(camera.combined);
//        renderer.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img2,2*viewWidth/3,0,viewWidth/3,viewHeight);
        batch.draw(chooseTank,2*viewWidth/3+150,viewHeight/2+400);
        batch.draw(playButton,2*viewWidth/3+200,viewHeight/2-500, 250, 100);
        renderer.rect(playerOneRectangle.x,playerOneRectangle.y,playerOneRectangle.width,playerOneRectangle.height);

        batch.draw(chooseBlazer,2*viewWidth/3+250,blazerCircle.y-75,150,150);
        batch.draw(chooseFrost,2*viewWidth/3+250,frostCircle.y -75,150,150);
        batch.draw(chooseMark,2*viewWidth/3+250,markCircle.y -75, 150,150);
        if(count == 1){
            batch.draw(selectBlazer,0,0,2*viewWidth/3,viewHeight);
            renderer.circle(blazerCircle.x,blazerCircle.y,blazerCircle.radius);
        }
        else if(count == 2){
            batch.draw(selectFrost,0,0,2*viewWidth/3,viewHeight);
            renderer.circle(frostCircle.x,frostCircle.y,frostCircle.radius);

        }
        else if(count == 3){
            batch.draw(selectMark,0,0,2*viewWidth/3,viewHeight);
            renderer.circle(markCircle.x,markCircle.y,markCircle.radius);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            count++;
            if(count == 4){
                count = 1;
            }
            System.out.println(count);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            count--;
            if(count == 0){
                count = 3;
            }
            System.out.println(count);
        }
        if (Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println(touchPos.x + " " + touchPos.y);
            System.out.println(blazerCircle.x + " " + blazerCircle.y);
            if (blazerCircle.contains(touchPos.x,  touchPos.y)) {
                count = 1;
            }
            if (frostCircle.contains(touchPos.x,  touchPos.y)) {
                count = 2;
            }
            if (markCircle.contains(touchPos.x,  touchPos.y)) {
                count = 3;
            }
            if (playerOneRectangle.contains(touchPos.x,  touchPos.y)) {
                if(count == 1){
                    if (this.vsPlayer==1) {
                        this.dispose();
                        game.setScreen(new SelectTankPlayerTwo(game));
                    }
                }
                else if(count == 2){
                    if (this.vsPlayer==1) {
                        this.dispose();
                        game.setScreen(new SelectTankPlayerTwo(game));
                    }
                }
                else if(count == 3){
                    if (this.vsPlayer==1) {
                        this.dispose();
                        game.setScreen(new SelectTankPlayerTwo(game));
                    }
                }

            }


        }

        batch.end();

        renderer.end();







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
        chooseFrost.getTexture().dispose();
        chooseBlazer.getTexture().dispose();
        chooseTank.getTexture().dispose();

    }
}
