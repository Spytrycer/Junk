package toolbox;

import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

import entities.Camera;

public class Maths 
{
	
	private static double PI = 3.14159265358979323846;

	
	public static Matrix4f createTransformationMatrix(Vector3f translation, 
			float rx, float ry, float rz, float scale)
	{
		Matrix4f matrix = new Matrix4f();
		
		rx = (float) Math.toRadians(rx);
		ry = (float) Math.toRadians(ry);
		rz = (float) Math.toRadians(rz);
		
	   matrix.rotateY(ry);
	   
	   matrix.rotateX(rx);

	   matrix.rotateZ(rz);
	   
	   matrix.translate(translation.x, translation.y, translation.z);
	   
		  
		return matrix;	 
	}
	
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(new Vector3f(translation.x, translation.y, 1f));
		matrix.scale(new Vector3f(scale.x, scale.y, 1f));
		return matrix;
	}
	
	
	
	public static float coTangent(float angle) 
	{
		return (float)(1f / Math.tan(angle));
	}
	public static float degreesToRadians(float degrees)
	{
		return degrees * (float)(PI / 180d);
	}
}
