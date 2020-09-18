package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class gameobject extends Sprite {
    public game mygame;
    public Body body;
    public gameobject(){}
    public gameobject.objecttype objtype;
    public enum move {RIGHT,LEFT,UP,DOWN}
    public move moving;
    public boolean status;
    public Fixture fixture;
    public Fixture fixture2;
    public Fixture fixture3;
    public Fixture fixture4;
    public Fixture fixture5;
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    public java.util.List<TextureRegion> animations;
    public enum objecttype {
        PLAYER, PLAYERLEFT,PLAYERRIGHT,PLAYERTOP,PLAYERBOTTOM, PLAYERCENTER,
        WALLLEFT,WALLTOP,WALLBOTTOM,WALLRIGHT, WALLCENTER, LADDER, FLOATER, SINKER,
        BOX, CHECKPOINT, COIN, KEY, LOCK, GIRL, SPIKE, GEAR, BREAKABLE, SPRING,
        SWITCH, SWITCHON, SWITCHOFF, PLATFORMH, PLATFORMV, PLATFORMS, MONSTER, MISC,
        LEFTSLOPE, RIGHTSLOPE
    }

    public void setupGameObject(World world, TiledMapTile tlcece, int xx, int yy, int width, BodyDef.BodyType type, gameobject.objecttype objecttype)
    {


        TextureRegion region = tlcece.getTextureRegion();
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth()/100f, region.getRegionHeight()/100f);
        setOrigin(8/100f, 8/100f);
        objtype = objecttype;
        setPosition(xx*16/100f,yy*16/100f);
        switch (objecttype) {
            //dorongable
            case BOX:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.linearDamping = 2f;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);

                //shape.setRadius(8/100f);
                // shape.set(0,0,0,0);
                fdef.shape = shape;

                MassData mas = new MassData();
                mas.mass = 10;
                body.setMassData(mas);
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.DEFAULT_BIT);


                break;
            //lewatable SENSOR
            case CHECKPOINT: case COIN:case KEY: case GIRL: case MISC: case LADDER:
            case FLOATER: case SINKER:
                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;


                case PLATFORMS:

                fdef.filter.categoryBits = game.MARKER_BIT;
                fdef.filter.maskBits = game.PLATFORM_BIT;
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            //lewatable SENSOR
                case SWITCHOFF:
                fdef.filter.categoryBits = game.DESTROYED_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                    fixture.setUserData(this);

                    //setCategoryFilter(game.COIN_BIT);


                break;


            case PLATFORMH: case PLATFORMV:
                fdef.filter.categoryBits = game.PLATFORM_BIT;
                fdef.filter.maskBits = game.MARKER_BIT | game.PLAYER_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                setPosition(xx*16/100f,yy*16/100f);
                body = world.createBody(bdef);
                body.setGravityScale(0);
                MassData md = new MassData();
                md.mass=999999;
                body.setMassData(md);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;


                //interact with environment & player
            case LOCK:  case BREAKABLE: case GEAR: case SPRING: case SPIKE:
            case SWITCH: case MONSTER:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                setPosition(xx*16/100f,yy*16/100f);
                body = world.createBody(bdef);

                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;


            //FULL STATIC MODE

            case LEFTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                setPosition(xx*16/100f,yy*16/100f);
                body = world.createBody(bdef);
                //top line



                EdgeShape shape = new EdgeShape();
                float size =8/100f;
                shape.set(-size,-size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(objecttype.WALLTOP);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line

                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture3.setUserData(objecttype.WALLRIGHT);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture4.setUserData(objecttype.WALLBOTTOM);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////

                break;

            case RIGHTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                setPosition(xx*16/100f,yy*16/100f);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =8/100f;
                shape.set(-size,size,size, -size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(objecttype.WALLTOP);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);

                shape.set(-size,size,-size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture3.setUserData(objecttype.WALLLEFT);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture4.setUserData(objecttype.WALLBOTTOM);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);

                break;


            case SWITCHON:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);
                setPosition(xx*16/100f,yy*16/100f);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =8/100f;
                shape.set(-size,size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(objecttype.WALLTOP);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line
                shape.set(-size,-size,-size, size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture2 = body.createFixture(fdef);
                fixture2.setUserData(objecttype.WALLLEFT);
                //setCategoryFilter(fixture2,game.DEFAULT_BIT);
                //right line
                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture3.setUserData(objecttype.WALLRIGHT);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture4.setUserData(objecttype.WALLBOTTOM);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////
                PolygonShape shap = new PolygonShape();

                shap.setAsBox(5 / 100f, 5 / 100f);
                fdef.shape = shap;
                fdef.isSensor=true;
                fixture5 = body.createFixture(fdef);
                fixture5.setUserData(objecttype.WALLCENTER);
                break;



            default:
        }




        if (objtype==objecttype.PLATFORMH){
            moving=move.LEFT;
        }

        if (objtype==objecttype.PLATFORMV){
            moving=move.UP;
        }

        if (objtype==objecttype.MONSTER){
            moving=move.LEFT;
        }
    }


    public void update(float dt){


       if (body!=null) setPosition(body.getPosition().x-8/100f,body.getPosition().y-8/100f);


        switch (objtype) {
            //dorongable
            case GEAR:
                rotate(5f);
                break;
            case PLATFORMH:
                if (moving== move.RIGHT)
                {
                    body.setLinearVelocity(0.5f,body.getLinearVelocity().y);
                }
                else if (moving== move.LEFT)
                {
                    body.setLinearVelocity(-0.5f,body.getLinearVelocity().y);
                }
                break;
            case PLATFORMV:
                if (moving== move.UP)
                {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0.5f);
                }
                else
                {
                    body.setLinearVelocity(body.getLinearVelocity().x, -0.5f);
                }
                break;

            case MONSTER:


                if (moving== move.RIGHT)
                {
                   // if (body.getLinearVelocity().x >=-0.5f) {
                       // body.applyLinearImpulse(-0.5f, 0, 0, 0, true);
                        body.setLinearVelocity(0.5f,body.getLinearVelocity().y);
                  //  }
                    setFlip(true,false);

                }
                else
                {
                   // if (body.getLinearVelocity().x <=0.5f) {
                        body.setLinearVelocity(-0.5f,body.getLinearVelocity().y);

                   // }
                    setFlip(false,false);

                }
                break;
        }

    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
        if (fixture2!=null) fixture2.setFilterData(filter);
        if (fixture3!=null) fixture3.setFilterData(filter);
        if (fixture4!=null) fixture4.setFilterData(filter);
        if (fixture5!=null) fixture5.setFilterData(filter);


    }

}