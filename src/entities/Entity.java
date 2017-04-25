package entities;


import org.joml.Matrix4f;
import org.joml.Vector3f;
import graphics.models.TexturedModel;


public class Entity
{

	private TexturedModel model;
	public Vector3f position;
	protected float rotX;
	protected float rotY;
	protected float rotZ;
	private float scale;
	public Vector3f linearAcc = new Vector3f();
	public Vector3f linearVel = new Vector3f();

    public Vector3f angularAcc = new Vector3f();
    public Vector3f angularVel = new Vector3f();
    
    public Matrix4f transformationMatrix= new Matrix4f();

	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
	{
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public void increasePosition(float dx, float dy, float dz)
	{
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	/*public Matrix4f createTransformationMatrix(double delta)
	{
		float dt = (float)delta;
		
		linearVel.fma(dt, linearAcc);

	    angularVel.fma(dt, angularAcc);
	   
	    transformationMatrix.rotateX(dt*angularVel.x)
	          .rotateY(dt*angularVel.y)
	          .rotateZ(dt*angularVel.z);

	    transformationMatrix.translate(-dt*linearVel.x, -dt*linearVel.y, -dt*linearVel.z);
	        
	    return transformationMatrix;
		
	}*/
	

	
	public TexturedModel getModel() 
	{
		return model;
	}

	public void setModel(TexturedModel model) 
	{
		this.model = model;
	}

	public Vector3f getPosition() 
	{
		return position;
	}

	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}

	public float getRotX() 
	{
		return rotX;
	}

	public void setRotX(float rotX) 
	{
		this.rotX = rotX;
	}

	public float getRotY() 
	{
		return rotY;
	}

	public void setRotY(float rotY) 
	{
		this.rotY = rotY;
	}

	public float getRotZ() 
	{
		return rotZ;
	}

	public void setRotZ(float rotZ) 
	{
		this.rotZ = rotZ;
	}

	public float getScale() 
	{
		return scale;
	}
	
	public void setScale(float scale) 
	{
		this.scale = scale;
	}
	
	
	
	
}
