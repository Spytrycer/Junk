package graphics.terrains;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Camera;
import entities.Light;
import graphics.shaders.ShaderProgram;
import toolbox.Maths;




public class TerrainShader extends ShaderProgram 
{

	public static final String VERTEX_FILE = "./Ressources/Shaders/TerrainVertexShader.txt";
	public static final String FRAGMENT_FILE = "./Ressources/Shaders/TerrainFragmentShader.txt";
	private static final int MAX_LIGHTS = 4;
	
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	
	public TerrainShader() 
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	protected void getAllUniformLocations()
	{
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		

		location_lightPosition = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		for(int i = 0;i<MAX_LIGHTS;i++)
		{
			location_lightPosition[i] = super.getUniformLocation("lightPosition["+i+"]");
			location_lightColour[i] = super.getUniformLocation("lightColour["+i+"]");	
			location_attenuation[i] = super.getUniformLocation("attenuation["+i+"]");	
		}
	}
	
	public void loadShineVariables(float damper, float reflectivity)
	{
		super.LoadFloat(location_shineDamper, damper);
		super.LoadFloat(location_reflectivity, reflectivity);
	}

	public void loadTreansformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights)
	{
		for(int i = 0;i<MAX_LIGHTS;i++)
		{
			if(i<lights.size())
			{
				super.LoadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.LoadVector(location_lightColour[i], lights.get(i).getColour());
				super.LoadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}
			else
			{
				super.LoadVector(location_lightPosition[i], new Vector3f(0,0,0));
				super.LoadVector(location_lightColour[i], new Vector3f(0,0,0));
				super.LoadVector(location_attenuation[i], new Vector3f(1,0,0));
			}
		}
	}
	
	public void loadViewMatrix(Camera camera)
	{

		super.loadMatrix(location_viewMatrix, camera.viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
}
