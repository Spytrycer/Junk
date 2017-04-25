package entities;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Player;

public class Camera
{
	
	private float distanceFromPlayer = 22;
	public Vector3f position;
	public Vector3f Rotation;
	public float pitch = 0;
	public float yaw = 0;
	public float roll= 0;
	
	public float angleChange = 0;
	public float pitchChange = 0;
	public float yawChange = 0;
	public float zoomLevel = 0;
	
	public Vector3f linearAcc = new Vector3f();
	public Vector3f linearVel = new Vector3f();

    public Vector3f angularAcc = new Vector3f();
    public Vector3f angularVel = new Vector3f();
	
	public Matrix4f viewMatrix = new Matrix4f();
	
	private Player player;
	
	public Camera(Player players) throws IOException
	{
		
		position = new Vector3f(0,0,0);
		Rotation = new Vector3f(0,0,0);
		this.player = players;
		viewMatrix.rotateLocalY((float) Math.toRadians(180));
		
	}
	

	public void move(double delta)
	{
		
		
		float dt = (float)delta;
		
		//linearVel.fma(dt, linearAcc);
		
	    //angularVel.fma(dt, angularAcc);
		this.angularVel.x = player.angularVel.x;
	    this.angularVel.y = -player.angularVel.y;
	    this.angularVel.z = player.angularVel.z;
		
		this.Rotation.x += dt*angularVel.x;
	    this.Rotation.y += dt*angularVel.y;
	    this.Rotation.z += dt*angularVel.z;
	    
	    linearVel.x = dt * player.linearVel.x;
	    linearVel.y = dt * player.linearVel.y;
	    linearVel.z = dt * player.linearVel.z;
	    
		
	    viewMatrix
	    .rotateLocalX((float)Math.toRadians(dt*angularVel.x))
        .rotateLocalY((float)Math.toRadians(dt*angularVel.y))
        .rotateLocalZ((float)Math.toRadians(dt*angularVel.z))
	    .translateLocal(linearVel.x,linearVel.y, linearVel.z);

	    
	    viewMatrix.getTranslation(position); // <- store entity's position
	
	}
	
	public Vector3f getPosition() 
	{
		return position;
	}

	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}
	
	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance)
	{
		
		
		

		
		
		//this.yaw = -player.rotY -180;
		//this.pitch = -player.rotX;
		//this.roll = player.rotZ; 
		
		
	} 
	
	private float calculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
		
	}
	
	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer  * Math.sin(Math.toRadians(pitch)));
		
	}
	
	public void moveForward()
	{
		this.linearVel.z = player.FLY_SPEED;
	}
	public void moveBackwards()
	{
		this.linearVel.z = -player.FLY_SPEED;
	}
	
	
	private void calculateZoom()
	{
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch()
	{
		pitch -= pitchChange;
	}
	private void calculateYaw()
	{
		yaw -= yawChange;
	}
	
	
	public void increaseYaw()
	{
		this.yaw++;
	}
	
	
	
}




	

