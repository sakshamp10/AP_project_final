package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class inGame implements Screen {

    private World world;
    private Box2DDebugRenderer dr;
    Tank tank1,tank2;
    final MyGdxGame game;
    SpriteBatch batch;
    Sprite terrain,Bg,gamebar,Tank1, Tank2,health,e_health,vs,menu,fuel,fuel2,weapon1,weapon2,gameover;

    final float viewWidth = 1920;
    final float viewHeight = 1080;

    OrthographicCamera camera;

    BodyDef body,body2;

    Body T1,T2,ground,test;

    private float t1d=400;
    private float t2d=4;
    private float t1y=55;
    private final float t1x=80;
    private float t2y=t1y;
    private float t2x=t1x;
    private float friction=0.25f;
    private long lastDropTime;

    private Array<Rectangle> weaponFire1 = new Array<>();
    private Array<Rectangle>weaponFire2 = new Array<>();

    private float g = -1500.81f;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 15, POSITIONITERATIONS = 10;

    private Fixture T1f,groundFix;
    private Fixture T2f;

    private int over=0;
    private Array<Body> bodies = new Array<Body>();
    public inGame(MyGdxGame game){
        HealthGenerator h = HealthGenerator.getInstance();
        float h1 = h.getMaxHealth();
        this.tank1 = new Tank(h1,1,1);
        this.tank2 = new Tank(h1,1,2);
        this.game=game;
        batch = new SpriteBatch();
        terrain = new Sprite(new Texture("canyon.png"));
        Bg= new Sprite(new Texture("gameBG.png"));
        gamebar = new Sprite(new Texture("gamebar.png"));
        Tank1 = new Sprite(new Texture("frostInGame.png"));
        Tank2 = new Sprite(new Texture("Tank2.png"));
        health = new Sprite(new Texture("healthbar.png"));
        e_health = new Sprite(new Texture("healthbar.png"));
        vs = new Sprite(new Texture("vs (1).png"));
        fuel = new Sprite(new Texture("fuel bar.png"));
        fuel2= new Sprite(new Texture("fuel bar.png"));
        menu= new Sprite(new Texture("Menu.png"));
        weapon1 = new Sprite(new Texture("weaponA.png"));
        weapon2 = new Sprite(new Texture("weaponB.png"));
        gameover = new Sprite(new Texture("gameover.png"));
        camera = new OrthographicCamera(viewWidth, viewHeight);
        camera.position.set(viewWidth/2,viewHeight/2,0);
        batch.begin();

        Tank1.setSize(200,120);
        Tank1.setOrigin(Tank1.getWidth()/2,Tank1.getHeight()/2);

        Tank2.setSize(200,120);
        Tank2.setOrigin(Tank2.getWidth()/2,Tank2.getHeight()/2);

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


        body2 = new BodyDef();
        body2.type= BodyDef.BodyType.DynamicBody;
        body2.position.set(3*viewWidth/4 ,viewHeight/2 +100);
        T2 =world.createBody(body2);
        T2f = T2.createFixture(fix);


        T1.setUserData(Tank1);
        T2.setUserData(Tank2);



        box1.dispose();

        body.type= BodyDef.BodyType.DynamicBody;
        body.position.set(viewWidth/2,4*viewHeight/5);


        body.type= BodyDef.BodyType.StaticBody;
        body.position.set(0,0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(0,243),new Vector2(62,247),new Vector2(139,262),new Vector2(232,280),new Vector2(262,285),new Vector2(367,300),new Vector2(427,309),
                new Vector2(500,319),new Vector2(534,325),new Vector2(613,339),new Vector2(662,358),new Vector2(731,369),new Vector2(794,375),
                new Vector2(850,372),new Vector2(930,366),new Vector2(979,361),new Vector2(1025,354),new Vector2(1113,333),new Vector2(1172,322),
                new Vector2(1214,318),new Vector2(1289,304),new Vector2(1334,298),new Vector2(1437,283),new Vector2(1490,280),
                new Vector2(1542,279),new Vector2(1602,279),new Vector2(1676,285),new Vector2(1740,291),new Vector2(1812,319),new Vector2(1867,345),new Vector2(1915,354)});

        fix.friction=friction;
        fix.restitution=0f;
        fix.shape = groundShape;

        ground=world.createBody(body);
        groundFix=ground.createFixture(fix);

        groundShape.dispose();

    }


    private int count=0;
    private int flag=0;

    private int pressed=0;
    private int globalPressed=1;


    public void fire(final int player) {

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(player==1){
//                    System.out.println("Called MAINE");
                    Iterator<Rectangle> iter = weaponFire1.iterator();
                    while (iter.hasNext()) {
                        count++;
//                System.out.println(count);
                        Rectangle w = iter.next();
//                raindrop.x += 0.05;
                        w.x += 5;
                        w.y = -(w.x - 1920) * (w.x) / 1080 + 50;
                        if (w.y + 64 < 0) {
                            flag = 1;
                            iter.remove();
                            timer.cancel();
                            globalPressed = 1;
                            pressed = 0;
                            weaponFire1 = new Array<Rectangle>();
//                            spawnWeapon1();
                        }
//                weaponFire.add(raindrop);
                        if (w.overlaps(Tank2.getBoundingRectangle())) {
                            flag = 1;
                            tank2.setHealth(0.1f);
                            System.out.println("hit\n");//health
//health
                            iter.remove();
                            timer.cancel();
                            globalPressed = 1;
                            pressed = 0;
                            weaponFire1 = new Array<Rectangle>();
//                            spawnWeapon1();
                        }

                    }
                }

                else if(player==2){
                    System.out.println("Called MAINE");
                    Iterator<Rectangle> iter = weaponFire2.iterator();
                    while (iter.hasNext()) {
                        count++;
//                System.out.println(count);
                        Rectangle w = iter.next();
//                raindrop.x += 0.05;
                        w.x -= 5;
                        w.y = -(w.x - 1920) * (w.x) / 1080 + 50;
                        if (w.y + 64 < 0) {
                            flag = 1;
                            iter.remove();
                            timer.cancel();
                            globalPressed = 1;
                            pressed = 0;
                            weaponFire2 = new Array<Rectangle>();
//                            spawnWeapon2();
                        }
                        if (w.overlaps(Tank1.getBoundingRectangle())) {
                            flag = 1;
                            tank1.setHealth(0.1f);
                            System.out.println(tank1.getHealth());//health
                            iter.remove();
                            timer.cancel();
                            globalPressed = 1;
                            pressed = 0;
                            weaponFire2 = new Array<Rectangle>();
//                            spawnWeapon2();
                        }
                    }
                }
            }
//            sleep(1);
        }, 10, 7);

    }


    private void spawnWeapon1(float x, float y) {
        Rectangle weapon = new Rectangle();
        weapon.x = x;
        weapon.y = y;
        weapon.width = 5;
        weapon.height = 5;
        weaponFire1.add(weapon);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnWeapon2(float x, float y) {
        Rectangle weapon = new Rectangle();
        weapon.x=x;
        weapon.y =y;
        weapon.width = 5;
        weapon.height = 5;
        weaponFire2.add(weapon);
        lastDropTime = TimeUtils.nanoTime();
    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);



        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)&& over==0){
            test.applyForceToCenter(1e13f,1e13f,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& over==0){
            if(tank1.getFuel()!=0){
                T1f.setFriction(3*friction);
                groundFix.setFriction(friction);
                T1.setAwake(true);
                T1.setLinearVelocity(-800,-400);
                tank1.setFuel(0.005f);

                // need to put max fuel once attack done;
            }
            tank2.fuelRefill();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& over==0){
            if(tank1.getFuel()!=0) {
                T1f.setFriction(3 * friction);
                groundFix.setFriction(friction);
                T1.setAwake(true);
                T1.setLinearVelocity(800, -400);
                tank1.setFuel(0.005f);
            }
            tank2.fuelRefill();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)&& over==0){
            if(tank2.getFuel()!=0) {
                T2f.setFriction(3 * friction);
                groundFix.setFriction(friction);
                T2.setAwake(true);
                T2.setLinearVelocity(800, -400);
                tank2.setFuel(0.005f);
            }
            tank1.fuelRefill();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)&& over==0){
            if(tank2.getFuel()!=0) {
                T2f.setFriction(3 * friction);
                groundFix.setFriction(friction);
                T2.setAwake(true);
                T2.setLinearVelocity(-800, -400);
                tank2.setFuel(0.005f);
            }
            tank1.fuelRefill();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            game.setScreen(new Menu(game));
        }
        else {
            T1f.setFriction(1f);
            groundFix.setFriction(1f);

            T1.setLinearVelocity(0f,0f);
            T2.setLinearVelocity(0f,0f);

        }
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        if(T1.getAngle()>25){
            T1f.setFriction(1f);
            groundFix.setFriction(1f);
        }
        batch.begin();


        batch.draw(Bg,0,0,viewWidth,viewHeight);
        batch.draw(terrain,-10,0, viewWidth+100,viewHeight/2+15 );

        Tank1.setPosition(T1.getPosition().x-t1x,T1.getPosition().y-t1y);
        Tank1.setRotation(T1.getAngle()* MathUtils.radiansToDegrees);
        Tank1.draw(batch);

        Tank2.setPosition(T2.getPosition().x-t2x,T2.getPosition().y-t2y);
        Tank2.setRotation(T2.getAngle()* MathUtils.radiansToDegrees);
        Tank2.draw(batch);

        batch.draw(health,viewWidth/4-150,viewHeight-100,500*tank1.getHealth(),100);
        batch.draw(e_health,viewWidth/4+1100,viewHeight-100,-500*tank2.getHealth(),100);
        batch.draw(vs,viewWidth/4+425,viewHeight-100,100,100);
        batch.draw(menu,0,viewHeight-100,100,100);
        batch.draw(fuel,viewWidth/4-120,viewHeight-150,400*tank1.getFuel(),50);
        batch.draw(fuel2,viewWidth/4+1100,viewHeight-150,-400*tank2.getFuel(),50);
        for(Rectangle weaponA: weaponFire1 ){
            batch.draw(weapon1,weaponA.x+10,weaponA.y,30,30);
            break;
        }
        for(Rectangle weaponB : weaponFire2){
            batch.draw(weapon2,weaponB.x,weaponB.y,30,30);
        }
        if(tank1.getHealth()<=0 || tank2.getHealth()<=0){
            batch.draw(gameover,viewWidth/2,viewHeight/2,450,300);
            over=1;
        }
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.X) && globalPressed==1 && over==0) {
            spawnWeapon1(T1.getPosition().x,T1.getPosition().y);
            fire(1);
//            spawnRaindrop();
            pressed=1;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.M) && globalPressed==1 && over==0){
            spawnWeapon2(T2.getPosition().x,T2.getPosition().y);
            fire(2);
//            spawnRaindrop();
            pressed=1;
        }
        if (pressed==1){
            pressed=0;
            globalPressed=0;
        }

        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println("new Vector2("+(int)touchPos.x+","+(int)touchPos.y+"),");
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

    }
}
