package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.ScreenUtils;

public class inGame implements Screen {

    private World world;
    private Box2DDebugRenderer dr;
    Tank tank1,tank2;
    final MyGdxGame game;
    SpriteBatch batch;
    Sprite terrain,Bg,gamebar,Tank1, Tank2,health,e_health,vs,menu;

    final float viewWidth = 1920;
    final float viewHeight = 1080;

    OrthographicCamera camera;

    BodyDef body;

    Body T1,T2,ground;

    private float t1d=1;
    private float t2d=1;
    private float t1y=60;
    private final float t1x=90;
    private float t2y=50;
    private float t2x=100;
    private float friction=0.25f;

    private float g = -15000.81f;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 15, POSITIONITERATIONS = 10;

    private Fixture T1f,groundFix;
    private Fixture T2f;

    public inGame(MyGdxGame game){



        this.tank1 = new Tank(1,1,1);
        this.tank2 = new Tank(1,1,2);
        this.game=game;
        batch = new SpriteBatch();
        terrain = new Sprite(new Texture("canyon.png"));
        Bg= new Sprite(new Texture("gameBG.png"));
        gamebar = new Sprite(new Texture("gamebar.png"));
        Tank1 = new Sprite(new Texture("frostInGame.png"));
        Tank2 = new Sprite(new Texture("Tank2.png"));
        health = new Sprite(new Texture("Health (1).png"));
        e_health = new Sprite(new Texture("e_health (1).png"));
        vs = new Sprite(new Texture("vs (1).png"));
         menu= new Sprite(new Texture("Menu.png"));
        camera = new OrthographicCamera(viewWidth, viewHeight);
        camera.position.set(viewWidth/2,viewHeight/2,0);
        batch.begin();
        batch.draw(Tank1,viewWidth/4 - 100,viewHeight/2 - 200,200,120);
        Tank1.setX(viewWidth/4 - 100);
        Tank1.setY(viewHeight/2 - 200);
        batch.draw(Tank2,3*viewWidth/4 ,viewHeight/2 - 200,200,120);
        Tank2.setX(3*viewWidth/4);
        Tank2.setY(viewHeight/2 - 200);
        batch.end();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0,g),false);
        dr = new Box2DDebugRenderer();

        body = new BodyDef();
        body.type= BodyDef.BodyType.DynamicBody;
        body.position.set(viewWidth/4 - 100,viewHeight/2 - 50);

        PolygonShape box1 = new PolygonShape();
        box1.setAsBox(t1x,t1y);

        FixtureDef fix = new FixtureDef();
        fix.shape= box1;
        fix.friction = 3*friction;
        fix.density= t1d;

        T1= world.createBody(body);
        T1f=T1.createFixture(fix);


        box1.dispose();

        body.type= BodyDef.BodyType.StaticBody;
        body.position.set(0,0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{ new Vector2(0,viewHeight/2-200),new Vector2(300,viewHeight/2-200),new Vector2(600,viewHeight/2+100), new Vector2(1400,viewHeight/2),new Vector2(1925,viewHeight/2-200)});

        fix.friction=friction;
        fix.restitution=0f;
        fix.shape = groundShape;

        ground=world.createBody(body);
        groundFix=ground.createFixture(fix);

        groundShape.dispose();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            T1f.setFriction(friction);
            groundFix.setFriction(friction);
            T1.applyForceToCenter(-1e9f,4*t1x*t2x*t1d*g,true);

//           if(T1.getAngle()<90){
//           T1.setLinearVelocity(-1000f,-1000f);
//           }

        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//            System.out.println("right");
//            T1.setLinearVelocity(1000f,-1000f);
            //            Tank2.translateX(1f);
            T1f.setFriction(friction);
            groundFix.setFriction(friction);
            T1.applyForceToCenter(1e10f,4*t1x*t2x*t1d*g,true);
//            T1.setLinearVelocity(0,-1000f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Tank1.translateX(1f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Tank1.translateX(-1f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            game.setScreen(new Menu(game));
        }
        else {
            T1f.setFriction(1f);
            groundFix.setFriction(1f);
            T1.setAwake(false);
            T1.setLinearVelocity(0f,0f);
//            T1.applyForceToCenter((float)(-4*t1x*t1y*t1d*g*Math.sin((T1.getAngle()))),(4*t1d*t1x*t1y*g),true);
        }
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        batch.begin();
        batch.draw(Bg,0,0,viewWidth,viewHeight);
        batch.draw(terrain,0,0, viewWidth,viewHeight/2 - 200);
        batch.draw(Tank1,Tank1.getX(),Tank1.getY(),200,120);
        batch.draw(Tank2,Tank2.getX() ,Tank2.getY(),200,120);
        batch.draw(health,viewWidth/4-150,viewHeight-100,500,100);
        batch.draw(e_health,viewWidth/4+600,viewHeight-100,500,100);
        batch.draw(vs,viewWidth/4+425,viewHeight-100,100,100);
        batch.draw(menu,0,viewHeight-100,100,100);
        dr.render(world,camera.combined);
        batch.end();
        System.out.println(Math.toDegrees(T1.getAngle()));
//        System.out.println(T1.getLinearVelocity());
//        System.out.println(T1.getPosition());
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

    }
}
