package com.wilthordgames.ancienttrials.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.util.Constantes;

/**
 * Created by wilthord on 27/08/2016.
 */
public class Disparo extends GameObject {

    //private Body body;
    //private Vector2 tam;

    {
        curSprite = new Sprite(new Texture(Constantes.IMAGEN_DISPARO_AIRE));
        pos=new Vector2(0,0);
        tam = new Vector2(64*Constantes.ESCALA_GENERAL, 64*Constantes.ESCALA_GENERAL);
        curSprite.setSize(tam.x, tam.y);
    }

    public Disparo(Vector2 posIni, Vector2 dir){

        this.pos = new Vector2(posIni.x, posIni.y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posIni.x, posIni.y+(tam.y/2));
        bodyDef.active=true;

        body = GameState.world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape forma = new CircleShape();
        forma.setRadius(7 * Constantes.ESCALA_GENERAL);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = forma;
        fixtureDef.density = 1;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.isSensor=true;

        fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_PLAYER;
        fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_PLAYER;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        forma.dispose();

        body.applyLinearImpulse(dir.sub(body.getWorldCenter()).nor().scl(Constantes.VELOCIDAD_DISPARO),body.getWorldCenter(), true);
        //body.applyLinearImpulse(new Vector2(5000, 5000), body.getWorldCenter(),true);

    }

    public void update(){
        this.pos.x = body.getPosition().x-(tam.x/2);
        this.pos.y = body.getPosition().y-(tam.y/2);
    }

    public void desplegar(){
        if(this.alive) {
            this.alive=false;
            //GameState.world.destroyBody(this.body);
            GameState.entidadesCrear.add(new Vector2(pos.x, pos.y));
            //GameState.entidadesEliminar.add(this);
        }
    }

    public void destruir(){
        if(this.alive) {
            this.alive=false;
            //GameState.world.destroyBody(this.body);
            //GameState.entidadesEliminar.add(this);
        }
    }

}
