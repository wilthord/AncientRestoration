package com.wilthordgames.ancienttrials.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.util.Constantes;

/**
 * Created by wilthord on 28/08/2016.
 */
public class Prision extends GameObject {

    //private Body body;


    {
        curSprite = new Sprite(new Texture(Constantes.IMAGEN_IMPULSO_AIRE));
        pos=new Vector2(0,0);
        tam = new Vector2(64*Constantes.ESCALA_GENERAL, 32*Constantes.ESCALA_GENERAL);
        curSprite.setSize(tam.x, tam.y);
    }

    public Prision(Vector2 posIni, Vector2 tamanio){

        this.pos = new Vector2(posIni.x, posIni.y);
        tam=new Vector2(tamanio.x, tamanio.y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posIni.x, posIni.y);

        body = GameState.world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(tam.x / 2, tam.y / 2);       //Se reduce el tamaño en x, para que se vea mejor el impulso

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = forma;
        fixtureDef.density = 1;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.isSensor=true;

        fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_ESCENARIO;
        fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_ESCENARIO;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        forma.dispose();

    }

    public void update(){
        this.pos.x = body.getPosition().x-(tam.x/2);
        this.pos.y = body.getPosition().y-(tam.y/2);
    }

    public void draw(){
        //No pintamos nada para este GameObject
    }
}
