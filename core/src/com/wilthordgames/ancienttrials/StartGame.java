package com.wilthordgames.ancienttrials;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.Engine.InputEngine;
import com.wilthordgames.ancienttrials.Engine.MyContactListener;
import com.wilthordgames.ancienttrials.Entities.Disparo;
import com.wilthordgames.ancienttrials.Entities.ImpulsoAire;
import com.wilthordgames.ancienttrials.Entities.Personaje;
import com.wilthordgames.ancienttrials.Entities.GameObject;
import com.wilthordgames.ancienttrials.util.Constantes;
import com.wilthordgames.ancienttrials.util.MapBodyBuilder;

import java.util.ArrayList;

public class StartGame extends ApplicationAdapter {
	//SpriteBatch batch;
	Texture img;
	OrthogonalTiledMapRenderer renderer;
	TiledMap mapa;
	OrthographicCamera camera;
	InputEngine inputEngine;

	Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Box2D.init();
		GameState.world = new World(new Vector2(0, -10), true);
		GameState.world.setContactListener(new MyContactListener());
		debugRenderer = new Box2DDebugRenderer();

		mapa = new TmxMapLoader().load("Terrenos/Nivel02.tmx");
		MapBodyBuilder.buildShapes(mapa, 64, GameState.world);
		renderer = new OrthogonalTiledMapRenderer(mapa, Constantes.ESCALA_GENERAL);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w * Constantes.ESCALA_GENERAL + 7, h * Constantes.ESCALA_GENERAL + 7);
		renderer.setView(camera);
		//camera.zoom+=0.5;
		camera.position.x=(w*2)*Constantes.ESCALA_GENERAL/2;
		camera.position.y=h*Constantes.ESCALA_GENERAL/2;
		camera.update();

		//GameState.player = new Personaje(new Vector2(328*Constantes.ESCALA_GENERAL, 68*Constantes.ESCALA_GENERAL));
		//GameState.entidades.add(GameState.player);

		inputEngine = new InputEngine();
		Gdx.input.setInputProcessor(inputEngine);

		//GameState.entidades.add(new Disparo(new Vector2(128*Constantes.ESCALA_GENERAL, 128*Constantes.ESCALA_GENERAL), new Vector2(1,1)));

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Se realiza el update de todos los GameObjects
		doPhysicsStep(Gdx.graphics.getDeltaTime());
		this.update(Gdx.graphics.getDeltaTime());

		float tempPlayerPosx = GameState.player.getPosicion().x;
		float tempPlayerPosy = GameState.player.getPosicion().y;
		if(camera.position.x-tempPlayerPosx>Constantes.DISTACIA_PLAYER_CAMARA.x){
			camera.position.x=tempPlayerPosx+Constantes.DISTACIA_PLAYER_CAMARA.x;
		}else if(camera.position.x-tempPlayerPosx<-Constantes.DISTACIA_PLAYER_CAMARA.x){
			camera.position.x=tempPlayerPosx-Constantes.DISTACIA_PLAYER_CAMARA.x;
		}

		if(camera.position.y  - tempPlayerPosy > Constantes.DISTACIA_PLAYER_CAMARA.y){
			camera.position.y=tempPlayerPosy+Constantes.DISTACIA_PLAYER_CAMARA.y;
		}else if(camera.position.y-tempPlayerPosy<-Constantes.DISTACIA_PLAYER_CAMARA.y){
			camera.position.y=tempPlayerPosy-Constantes.DISTACIA_PLAYER_CAMARA.y;
		}


		//camera.position.x=cameraPos;
		camera.update();
		renderer.setView(camera);
		renderer.render();

		GameState.batch.setProjectionMatrix(camera.combined);
		GameState.batch.begin();
		for(GameObject go:GameState.entidades){
			go.draw();
		}
		GameState.batch.end();

		//GameState.world.step(1 / 45f, 6, 2);

		debugRenderer.render(GameState.world, camera.combined);

	}

	public void update(float deltaTime){

		if(GameState.tiempoProximoDisparo>0){
			GameState.tiempoProximoDisparo-=deltaTime;
		}
		if(inputEngine.isClicked) {
			inputEngine.isClicked = false;
			if(GameState.tiempoProximoDisparo<=0f) {
				GameState.tiempoProximoDisparo=Constantes.TIEMPO_CARGA_DISPARO;
				Vector3 projectedPos = new Vector3(inputEngine.lastPressed.x, inputEngine.lastPressed.y, 0);
				Vector3 tempPos = camera.unproject(projectedPos);
				Vector2 direccion = new Vector2(tempPos.x , tempPos.y);//.sub(GameState.player.getPosicion()).nor();
				GameState.entidades.add(new Disparo(new Vector2(GameState.player.getPosicion().x, GameState.player.getPosicion().y), direccion));
			}else{
				System.out.println("Cargando Poder!!!"+GameState.tiempoProximoDisparo);
			}
		}

		for(GameObject go:GameState.entidades){
			if(!go.alive && !go.muriendo){
				GameState.entidadesEliminar.add(go);
			}
			go.update();

		}

		for(GameObject go:GameState.entidadesEliminar){
			GameState.entidades.remove(go);
			GameState.world.destroyBody(go.body);
		}

		GameState.entidadesEliminar.clear();

		for(Vector2 go:GameState.entidadesCrear){
			GameState.entidades.add(new ImpulsoAire(go, false));
		}
		GameState.entidadesCrear.clear();

	}

	private float accumulator = 0;

	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		GameState.world.step(deltaTime, Constantes.VELOCITY_ITERATIONS, Constantes.POSITION_ITERATIONS);
		/*
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Constantes.TIME_STEP) {
			GameState.world.step(Constantes.TIME_STEP, Constantes.VELOCITY_ITERATIONS, Constantes.POSITION_ITERATIONS);
			accumulator -= Constantes.TIME_STEP;
		}*/
	}

}
