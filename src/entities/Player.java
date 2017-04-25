package entities;



import org.joml.Vector3f;

import graphics.models.TexturedModel;

public class Player extends Entity
{
	
	public static final float FLY_SPEED = 0.1f;
	private static final float TURN_SPEED = 0.5f;

	public float currentTurnSpeed_pitch =0;
	public float currentTurnSpeed_yaw =0;
	public float currentTurnSpeed_roll =0;
	public float currentSpeed = 0;
	public float pitch = 0;
	public float yaw = 0;
	public float roll= 0;

	
	

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
	{
		super(model, position, rotX, rotY, rotZ, scale);
		
	}
	
	public void move(double delta)
	{
		float dt = (float)delta;
		
		//linearVel.fma(dt, linearAcc);
		
	    //angularVel.fma(dt, angularAcc);
	    
		
		this.rotX += dt*angularVel.x;
	    this.rotY += dt*angularVel.y;
	    this.rotZ += dt*angularVel.z;
		
	    transformationMatrix
	    	  .rotateX((float)Math.toRadians(dt*angularVel.x))
	          .rotateY((float)Math.toRadians(dt*angularVel.y))
	          .rotateZ((float)Math.toRadians(dt*angularVel.z));
	    
	    transformationMatrix.translate( dt*linearVel.x, dt*linearVel.y, dt*linearVel.z);

	    transformationMatrix.getTranslation(position); // <- store entity's position
	}

	
	
	

   /* if (inputHandler.keys[GLFW_KEY_W]) ;
    if (inputHandler.keys[GLFW_KEY_S]) player.linearAcc.z += accFactor;
    if (inputHandler.keys[GLFW_KEY_D]) player.linearAcc.x += accFactor;
    if (inputHandler.keys[GLFW_KEY_A]) player.linearAcc.x -= accFactor;
    if (inputHandler.keys[GLFW_KEY_SPACE]) player.linearAcc.y += accFactor;
    if (inputHandler.keys[GLFW_KEY_LEFT_CONTROL]) player.linearAcc.y -= accFactor;
    if (inputHandler.keys[GLFW_KEY_Q]) rotateZ -= 1.0f;
    if (inputHandler.keys[GLFW_KEY_E]) rotateZ += 1.0f;
    if (inputHandler.buttons[GLFW_MOUSE_BUTTON_1])
    	{player.angularVel.set(-inputHandler.Mouse_y/10, -inputHandler.Mouse_x/10, rotateZ);
    	}*/

	//Move Forward
	
	
	public void moveForward()
	{
		//linearAcc.z = FLY_SPEED;
		linearVel.z = FLY_SPEED;
	}
	
	public void moveBack()
	{
		//linearAcc.z =  -FLY_SPEED;
		linearVel.z = -FLY_SPEED;
	}
	
	public void stopZ()
	{
		linearAcc.z = 0;
		linearVel.z = 0;
	}
	
	//Turn
	public void moveLeft()
	{
		//angularAcc.y = TURN_SPEED;
		angularVel.y = TURN_SPEED;
	}
	public void moveRight()
	{
		//angularAcc.y = -TURN_SPEED;
		angularVel.y = -TURN_SPEED;
		
	}
	public void stop_yaw()
	{
		angularAcc.y = 0;
		angularVel.y = 0;
	}
	
	//Pitch
	public void pitchUp()
	{
		angularVel.x = -TURN_SPEED;
	}
	public void pitchDown()
	{
		angularVel.x = TURN_SPEED;
	}
	public void stop_pitch()
	{
		angularVel.x = 0;
	}
	
	//Roll
	public void roll()
	{
		angularVel.z = TURN_SPEED;
	}
	
	public void rollback()
	{
		angularVel.z = -TURN_SPEED;
	}
	public void stop_roll()
	{
		angularVel.z = 0;
	}
	
	
	
	
	public void normalize90()
	{
		this.position = new Vector3f(0, 0, -50);
		this.rotX = 0;
		this.rotY = 90;
		this.rotZ = 0;
	}
	public void normalize()
	{
		this.linearAcc.zero();
		this.linearVel.zero();
		this.angularAcc.zero();
		this.angularVel.zero();
		this.position = new Vector3f(0, 0, 0);
		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
	}
	

}
