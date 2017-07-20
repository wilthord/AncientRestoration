package com.wilthordgames.ancienttrials.Engine;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.wilthordgames.ancienttrials.Entities.Disparo;
import com.wilthordgames.ancienttrials.Entities.Enemigo;
import com.wilthordgames.ancienttrials.Entities.GameObject;
import com.wilthordgames.ancienttrials.Entities.IActor;
import com.wilthordgames.ancienttrials.Entities.ImpulsoAire;
import com.wilthordgames.ancienttrials.Entities.Interruptor;
import com.wilthordgames.ancienttrials.Entities.Personaje;
import com.wilthordgames.ancienttrials.Entities.Prision;
import com.wilthordgames.ancienttrials.util.Constantes;

import java.util.Objects;

/**
 * Created by wilthord on 27/08/2016.
 */
public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Object objA = contact.getFixtureA().getUserData();
        Object objB = contact.getFixtureB().getUserData();

        //Detectamos las colisiones de los objetos con los impulsores
        if(objA!= null && objB!=null && (objA instanceof ImpulsoAire || objB instanceof ImpulsoAire)){
            //System.out.println(objA.toString());
            if(objA instanceof IActor) {
                ((IActor)objA).impulsar(10f);
                return;
            }else if(objB instanceof IActor){
                ((IActor)objB).impulsar(10f);
                return;
            }
        }

        //Detectamos las colisiones de un disparo
        if(objA!= null  && objA instanceof Disparo){
            if(calcularColisionDisparo((Disparo) objA, objB)){
                return;
            }
        }else if(objB!= null  && objB instanceof Disparo){
            if(calcularColisionDisparo((Disparo) objB, objA)){
                return;
            }
        }

        //Detectamos las colisiones de la prision
        if(objA!= null  && objA instanceof Prision){
            if(calcularColisionPrision((Prision) objA, objB)){
                return;
            }
        }else if(objB!= null  && objB instanceof Prision){
            if(calcularColisionPrision((Prision) objB, objA)){
                return;
            }
        }

        //Detectamos las colisiones de un Enemigo
        if(objA!= null  && objA instanceof Enemigo){
            if(calcularColisionEnemigo((Enemigo) objA, objB)){
                return;
            }
        }else if(objB!= null  && objB instanceof Enemigo){
            if(calcularColisionEnemigo((Enemigo) objB, objA)){
                return;
            }
        }
        /*
        if(objA!= null && objB!=null && (objA instanceof Disparo || objB instanceof Disparo)){
            if(objA instanceof String && objA.equals("piso")) {
                ((Disparo)objB).desplegar();
                return;
            }else if(objB instanceof String && objB.equals("piso")){
                ((Disparo)objA).desplegar();
                return;
            }else{      //Si los dos elementos tienen userData, pero el otro no es piso
                if (objA!= null && objA instanceof Disparo){
                    //System.out.println(contact.getFixtureB().getType().toString());
                    ((Disparo)objA).destruir();
                    return;
                }else if(objB!= null && objB instanceof Disparo){
                    ((Disparo)objB).destruir();
                    return;
                }
            }
        }else{
            if (objA!= null && objA instanceof Disparo){
                //System.out.println(contact.getFixtureB().getType().toString());
                ((Disparo)objA).destruir();
                return;
            }else if(objB!= null && objB instanceof Disparo){
                ((Disparo)objB).destruir();
                return;
            }
        }*/

        //DETECTAMOS LAS COLISIONES DEL ENEMIGO
        if(objA!= null  && objA instanceof Enemigo){
            if(calcularColisionEnemigo((Enemigo)objA, objB)){
                return;
            }
        }else if(objB!= null  && objB instanceof Enemigo){
            if(calcularColisionEnemigo((Enemigo)objB, objA)){
                return;
            }
        }

        //DETECTAMOS LAS COLISIONES DEL Interruptor
        if(objA!= null  && objA instanceof Interruptor){
            if(calcularColisionInterruptor((Interruptor) objA, objB)){
                return;
            }
        }else if(objB!= null  && objB instanceof Interruptor){
            if(calcularColisionInterruptor((Interruptor) objB, objA)){
                return;
            }
        }

    }

    public boolean calcularColisionEnemigo(Enemigo enemigo, Object otro){

        if(otro!=null){

            //Si es String, es un body estatico con caracteristicas especiales
            if(otro instanceof String){

                if(((String) otro).trim().equals(Constantes.TILEOBJ_LIMITE_ENEMIGO)){
                    enemigo.cambiarDireccion();
                    return true;
                }
            }else if(otro instanceof Personaje){

                ((Personaje) otro).destruir();
                return true;
            }
        }

        return false;
    }

    public boolean calcularColisionDisparo(Disparo disp, Object otro){

        if(otro != null) {
            if (otro instanceof String && otro.equals(Constantes.TILEOBJ_PISO)) {
                disp.desplegar();
                return true;
            }
        }

        //Si el disparo colisiona con cualquier otra cosa, se debe destruir
        disp.destruir();

        return true;
    }

    public boolean calcularColisionPrision(Prision prision, Object otro){

        if(otro!=null){

            //Si es String, es un body estatico con caracteristicas especiales
            if(otro instanceof IActor){
                ((IActor) otro).destruir();
                return true;
            }
        }
        return false;
    }

    public boolean calcularColisionInterruptor(Interruptor inter, Object otro){

        //if(otro != null ) {
            //if (otro instanceof String && otro.equals(Constantes.TILEOBJ_PISO)) {
        if(!inter.activado){
            inter.activar();

        }

        return true;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
