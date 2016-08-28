package com.wilthordgames.ancienttrials.util;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.wilthordgames.ancienttrials.Engine.GameState;
import com.wilthordgames.ancienttrials.Entities.Enemigo;
import com.wilthordgames.ancienttrials.Entities.Prision;

/**
 * Created by wilthord on 27/08/2016.
 */
public class MapBodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 0;

    public static Array<Body> buildShapes(Map map, float pixels, World world) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get("Obstacles").getObjects();

        //System.out.println(objects.getCount());

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject)object);
                if(object.getName()!=null) {
                    if (object.getName().trim().equals(Constantes.TILEOBJ_ENEMIGO)) {
                        Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
                        GameState.entidades.add(new Enemigo(new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                                (rectangle.y + rectangle.height * 0.5f ) / ppt)));
                        continue;
                    }else if (object.getName().trim().equals(Constantes.TILEOBJ_PRISION)) {
                        Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
                        GameState.entidades.add(new Prision(new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt, (rectangle.y + rectangle.height * 0.5f ) / ppt),
                                new Vector2(rectangle.width / ppt, rectangle.height / ppt)));
                        continue;
                    }
                }
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else {
                continue;
            }



            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            //fixtureDef.isSensor=true;
            fixtureDef.density=1f;
            if(object.getName()!=null) {
                if (object.getName().trim().equals(Constantes.TILEOBJ_LIMITE_ENEMIGO)) {
                    fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_PLAYER;
                    fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_PLAYER;
                }else{
                    fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_ESCENARIO;
                    fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_ESCENARIO;
                }
            }else{
                fixtureDef.filter.categoryBits = Constantes.BOX2D_CATEGORIA_ESCENARIO;
                fixtureDef.filter.maskBits = Constantes.BOX2D_MASK_ESCENARIO;
            }

            Fixture tempF = body.createFixture(fixtureDef);
            if(object.getName()!=null) {
                tempF.setUserData(object.getName().trim());
            }

            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                rectangle.height * 0.5f / ppt,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}
