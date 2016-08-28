package com.wilthordgames.ancienttrials.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.util.Constantes;

/**
 * Created by wilthord on 27/08/2016.
 */
public class Enemigo extends GameObject implements IActor {

    private int direccion =-1;
    private boolean capturado=false;

    {
        curSprite = new Sprite(new Texture(Constantes.IMAGEN_ENEMIGO));
        pos=new Vector2(0,0);
        tam = new Vector2(64*Constantes.ESCALA_GENERAL, 64*Constantes.ESCALA_GENERAL);
        curSprite.setSize(tam.x, tam.y);
    }

    public Enemigo(){
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
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits=Constantes.BOX2D_CATEGORIA_ENEMIGO;
        fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_ENEMIGO;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        forma.dispose();
    }

    public Enemigo(Vector2 posx){
        this();
        pos=new Vector2(posx.x, posx.y);
        body.setTransform(new Vector2(pos.x+(tam.x/2), pos.y+(tam.y/2)), 0f);
        Vector2 vel = body.getLinearVelocity();
        body.setLinearVelocity((1.5f*direccion), vel.y);

    }
/*
    public void draw(){
        Assets.getInstance().pintarSprite(GameState.batch, curSprite, pos, new Vector2(64,64), new Vector2(15/64, 15/64));
    }*/

    public void update(){

        if(!capturado) {
            Vector2 vel = body.getLinearVelocity();
            body.setLinearVelocity((1.5f * direccion), vel.y);
        }

        this.pos.x = body.getPosition().x-(tam.x/2);
        this.pos.y = body.getPosition().y-(tam.y/2);
    }

    public Vector2 getPosicion(){
        return body.getPosition();
    }

    public void cambiarDireccion(){
        this.direccion*=-1;
    }


    @Override
    public void impulsar(float impulso) {
        //System.out.println(impulso);
        body.applyLinearImpulse(new Vector2(0, impulso), body.getWorldCenter(), true);
    }

    @Override
    public void destruir(){
        this.alive=false;
        this.muriendo=false;
    }
}
