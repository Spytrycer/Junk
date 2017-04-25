package graphics;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import graphics.models.RawModel;
import graphics.models.TexturedModel;
import graphics.renderEngine.EntityRenderer;
import graphics.renderEngine.Loader;
import graphics.shaders.StaticShader;
import graphics.skybox.SkyboxRenderer;
import graphics.terrains.Terrain;
import graphics.terrains.TerrainRenderer;
import graphics.terrains.TerrainShader;
import graphics.textures.ModelTexture;
import main.Main;
import toolbox.Maths;


public class MasterRenderer 
{
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.01f;
	private static final float FAR_PLANE = 1000f;
	
	private int Width = 1280;
	private int Height = 720;

	public double delta;

	private Matrix4f projectionMatrix;
	private StaticShader shader;
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader;
	
	private EntityRenderer renderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private SkyboxRenderer skyboxRenderer ;
	
	public MasterRenderer(Loader loader)
	{
		enableCulling();
	
		createProjectionMatrix();
		
		shader = new StaticShader();
		terrainShader = new TerrainShader();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		
		
	}
	
	public static void enableCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCullig()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights, Camera camera)
	{
		prepare();
		glEnable(GL_DEPTH_TEST);
		
		shader.start();
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		terrainShader.start();
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera);
		terrains.clear();
		entities.clear();
		
		
		
	}
	
	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity)
	{
		TexturedModel entityModel = 
				entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
		
	private void createProjectionMatrix()
	{
		float aspectRatio = (float) Width / (float) Height;
		float y_scale = Maths.coTangent(Maths.degreesToRadians(FOV / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00(x_scale);
		projectionMatrix.m11(y_scale);
		projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
		projectionMatrix.m23(-1);
		projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE ) / frustum_length));
		projectionMatrix.m33(0);	
	}

	public void prepare()
	{
	
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void cleanUp()
	{
		shader.cleanUp();
		terrainShader.cleanUp();
	}

}
