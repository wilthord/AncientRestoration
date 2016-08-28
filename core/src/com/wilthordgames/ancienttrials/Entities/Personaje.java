package com.wilthordgames.ancienttrials.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.Entities.GameObject;
import com.wilthordgames.ancienttrials.util.Assets;
import com.wilthordgames.ancienttrials.util.Constantes;

/**
 * Created by wilthord on 27/08/2016.
 */
public class Personaje extends GameObject implements IActor {

    //private Body body;


    {
        curSprite = new Sprite(new Texture(Constantes.IMAGEN_PERSONAJE));
        pos=new Vector2(0,0);
        tam = new Vector2(64*Constantes.ESCALA_GENERAL, 128*Constantes.ESCALA_GENERAL);
        curSprite.setSize(tam.x, tam.y);
    }

    public Personaje(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        body = GameState.world.createBody(bodyDef);
        body.setUserData(this);
        body.setFixedRotation(true);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(tam.x/2, tam.y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = forma;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits=Constantes.BOX2D_CATEGORIA_PLAYER;
        fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_PLAYER;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        forma.dispose();
    }

    public Personaje(Vector2 posx){
        this();
        pos=new Vector2(posx.x, posx.y);
        body.setTransform(new Vector2(pos.x+(tam.x/2), pos.y+(tam.y/2)), 0f);

    }
/*
    public void draw(){
        Assets.getInstance().pintarSprite(GameState.batch, curSprite, pos, new Vector2(64,64), new Vector2(15/64, 15/64));
    }*/

    public void update(){

        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();

        //System.out.println(vel.x);

        if (Gdx.input.isKeyPressed(Input.Keys.A) && vel.x > -Constantes.MAXIMA_VELOCIDAD_PERSONAJE) {
            this.body.setLinearVelocity(vel.x - 0.5f, vel.y);
            //this.body.applyLinearImpulse(-0.40f, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && vel.x < Constantes.MAXIMA_VELOCIDAD_PERSONAJE) {
            this.body.setLinearVelocity(vel.x+0.5f, vel.y);
            //this.body.applyLinearImpulse(0.40f, 0, pos.x, pos.y, true);
        }

        this.pos.x = body.getPosition().x-(tam.x/2);
        this.pos.y = body.getPosition().y-(tam.y/2);
    }

    public Vector2 getPosicion(){
        return body.getPosition();
    }


    @Override
    public void impulsar(float impulso) {
        //System.out.println(impulso);
        if(body.getLinearVelocity().y<0){
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        body.applyLinearImpulse(new Vector2(0, impulso), body.getWorldCenter(), true);
    }

    @Override
    public void destruir(){
        this.alive=false;
        this.muriendo=false;
    }

}
