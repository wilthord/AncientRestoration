package com.wilthordgames.ancienttrials.Engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.wilthordgames.ancienttrials.Entities.GameObject;
import com.wilthordgames.ancienttrials.Entities.IElementoMovil;
import com.wilthordgames.ancienttrials.Entities.Personaje;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilthord on 27/08/2016.
 */
public class GameState {

    public static SpriteBatch batch;
    public static World world;
    public static ArrayList<GameObject> entidades;
    public static ArrayList<GameObject> entidadesEliminar;
    public static ArrayList<Vector2> entidadesCrear;
    public static HashMap<String, IElementoMovil> elementosMoviles;
    public static Personaje player;

    public static float tiempoProximoDisparo;

    static {

        batch = new SpriteBatch();
        entidades = new ArrayList<GameObject>();
        entidadesEliminar=new ArrayList<GameObject>();
        entidadesCrear=new ArrayList<Vector2>();
        elementosMoviles = new HashMap<String, IElementoMovil>();

    }

    public static void activarElemento(String idElemento){

        IElementoMovil ele = elementosMoviles.get(idElemento);

        if(ele != null){
            ele.activar();
        }
    }
}
