package com.wilthordgames.ancienttrials.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by wilthord on 27/08/2016.
 */
public class Constantes {

    public static final String IMAGEN_PERSONAJE="Personaje01.png";
    public static final String IMAGEN_DISPARO_AIRE="DisparoAire01.png";
    public static final String IMAGEN_IMPULSO_AIRE="ImpulsoAire.png";
    public static final String IMAGEN_ENEMIGO="EnemigoX01.png";
    public static final String IMAGEN_INTERRUPTOR_ARRIBA="InterruptorA01.png";
    public static final String IMAGEN_INTERRUPTOR_ARRIBA_ACTIVO="InterruptorA02.png";
    public static final String IMAGEN_PLATAFORMA="Plataforma.png";

    public static final Vector2 DISTACIA_PLAYER_CAMARA = new Vector2(0.5f, 2f);
    //public static final Vector2 OFFSET_PLAYER_CAMARA = new Vector2(0f, 6f);

    public static final String TILEOBJ_PRISION = "Prision";
    public static final String TILEOBJ_LIMITE_ENEMIGO = "LimiteEnemigo";
    public static final String TILEOBJ_PISO = "Piso";
    public static final String TILEOBJ_ENEMIGO = "Enemigo";
    public static final String TILEOBJ_PLAYER = "Player";
    public static final String TILEOBJ_INTERRUPTOR = "Interruptor";
    public static final String TILEOBJ_PLATAFORMA = "Plataforma";
    public static final String TILEOBJ_IMPULSOR = "ImpulsorAire";

    public static final float MAXIMA_VELOCIDAD_PERSONAJE=2;
    public static final float VELOCIDAD_DISPARO=0.5f;
    public static final float TIEMPO_CARGA_DISPARO=2f;

    public static final float TIEMPO_VIDA_IMPULSO=5f;

    public static final float TIME_STEP=1/30f;
    public static final int VELOCITY_ITERATIONS=6;
    public static final int POSITION_ITERATIONS=2;

    public static final float ESCALA_GENERAL = 1 / 64f;

    public static final short BOX2D_CATEGORIA_PLAYER = 0X0001;
    public static final short BOX2D_CATEGORIA_ENEMIGO = 0X0002;
    public static final short BOX2D_CATEGORIA_ESCENARIO = 0X0004;

    public static final short BOX2D_MASK_PLAYER = BOX2D_CATEGORIA_ENEMIGO | BOX2D_CATEGORIA_ESCENARIO;
    public static final short BOX2D_MASK_ENEMIGO = ~BOX2D_CATEGORIA_ENEMIGO;//BOX2D_CATEGORIA_PLAYER | BOX2D_CATEGORIA_ESCENARIO;
    public static final short BOX2D_MASK_ESCENARIO = -1;

}
