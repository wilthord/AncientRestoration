package com.wilthordgames.ancienttrials.Engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.wilthordgames.ancienttrials.Entities.GameObject;
import com.wilthordgames.ancienttrials.Entities.Personaje;

import java.util.ArrayList;

/**
 * Created by wilthord on 27/08/2016.
 */
public class GameState {

    public static SpriteBatch batch;
    public static World world;
    public static ArrayList<GameObject> entidades;
    public static ArrayList<GameObject> entidadesEliminar;
    public static ArrayList<Vector2> entidadesCrear;
    public static Personaje player;

    public static float tiempoProximoDisparo;

    static {

        batch = new SpriteBatch();
        entidades = new ArrayList<GameObject>();
        entidadesEliminar=new ArrayList<GameObject>();
        entidadesCrear=new ArrayList<Vector2>();

    }
}
