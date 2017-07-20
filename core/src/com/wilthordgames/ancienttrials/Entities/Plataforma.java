package com.wilthordgames.ancienttrials.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.util.Constantes;

/**
 * Created by wilthord on 28/08/2016.
 */
public class Plataforma extends GameObject implements IElementoMovil{

    String idObjeto="";
    boolean activada = false;
    //Body pivote ;
    RevoluteJoint joint;

    {
        curSprite = new Sprite(new Texture(Constantes.IMAGEN_PLATAFORMA));
        pos=new Vector2(0,0);
        tam = new Vector2(8*Constantes.ESCALA_GENERAL, 64*Constantes.ESCALA_GENERAL);
        curSprite.setSize(tam.x, tam.y);
    }

    public Plataforma(Vector2 posIni, Vector2 tamanio, String idObj){

        this.pos = new Vector2(posIni.x, posIni.y);
        tam = new Vector2(tamanio.x, tamanio.y);
        this.idObjeto=idObj;
        curSprite.setSize(tam.x, tam.y);
        //pivote=crearPivote(posIni, 0.1f);
        //tam=new Vector2(tamanio.x, tamanio.y);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(posIni.x, posIni.y);
        bodyDef.gravityScale=0;

        body = GameState.world.createBody(bodyDef);
        body.setUserData(this);
        body.setGravityScale(0f);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox((tam.x / 2), tam.y / 2);       //Se reduce el tamaño en x, para que se vea mejor el impulso

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = forma;
        fixtureDef.density = 1;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;

        fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_ESCENARIO;
        fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_ESCENARIO;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        forma.dispose();
        body.setTransform(body.getPosition(), 90 * MathUtils.degreesToRadians);
/*
        RevoluteJointDef revoluteJointDef=new RevoluteJointDef();
        revoluteJointDef.initialize(pivote, body, pivote.getWorldCenter());
        revoluteJointDef.collideConnected=true;
        revoluteJointDef.enableMotor=true;
        revoluteJointDef.maxMotorTorque=20;
        //revoluteJointDef.localAnchorA.set(0,1);
        //revoluteJointDef.motorSpeed=90* MathUtils.degreesToRadians;
*/
        //joint = (RevoluteJoint)GameState.world.createJoint(revoluteJointDef);

    }

    public void update(){

        if(activada){
            //System.out.println("Angulo");
            body.setTransform(body.getPosition(), 0 * MathUtils.degreesToRadians);
            activada=false;
        }

        this.pos.x = body.getPosition().x-(tam.x/2);
        this.pos.y = body.getPosition().y-(tam.y/2);
        this.angulo = body.getAngle()*MathUtils.radiansToDegrees;
    }

    @Override
    public void activar() {
        //System.out.println("Plataforma Activada");
        activada=true;
        //body.setTransform(body.getPosition().x,body.getPosition().y, -90);
    }

    public Body crearPivote(Vector2 pos, float radius){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x-(tam.x/2), pos.y-(tam.y/2));
        bodyDef.angle=0;

        Body body = GameState.world.createBody(bodyDef);
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=1;
        fixtureDef.restitution=1;
        fixtureDef.friction=0;
        fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_ESCENARIO;
        fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_ESCENARIO;

        CircleShape forma = new CircleShape();
        forma.setRadius(radius);
        fixtureDef.shape=forma;

        body.createFixture(fixtureDef);
        forma.dispose();
        return body;
    }
}
