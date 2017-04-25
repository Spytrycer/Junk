 package main;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import graphics.MasterRenderer;
import graphics.Window;
import graphics.models.RawModel;
import graphics.models.TexturedModel;
import graphics.renderEngine.Loader;
import graphics.renderEngine.OBJLoader;
import graphics.textures.ModelTexture;
import input.InputHandler;



public class Main 
{
	
	private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
	private Window window;
	private MasterRenderer renderer;
	private Loader loader;
	private InputHandler inputHandler;
	private int Width;
	private int Height;
	private boolean running = false;
	private double delta;
	
	List<Light> lights = new ArrayList<Light>();
	private Camera camera;
	private Player player;
	private Entity entity;
	
	/*LOOKAT
	 * 
	 * 
	 *   float normX = (float) ((xpos - width/2.0) / width * 2.0);
                float normY = (float) ((ypos - height/2.0) / height * 2.0);
                mouseX = Math.max(-width/2.0f, Math.min(width/2.0f, normX));
                mouseY = Math.max(-height/2.0f, Math.min(height/2.0f, normY));
	 * 
	 * 
	 * 
	 * */

	public static void main(String[] args) 
	{
		
		Main main = new Main();
		main.start();
		
	}
	public Main()
	{
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init() throws IOException
	{
		
		Width = 1280;
		Height = 720;
		glfwSetErrorCallback(errorCallback);
		glfwInit();
		
		
	
		setWindow(new Window(Width, Height, "Junker.5"));
		GL.createCapabilities();
		
		
		
		InputStream stream = new FileInputStream("./Ressources/Cursors/defaultcursor.png");
	    BufferedImage image = ImageIO.read(stream);

	    int width = image.getWidth();
	    int height = image.getHeight();

	    int[] pixels = new int[width * height];
	    image.getRGB(0, 0, width, height, pixels, 0, width);

	    // convert image to RGBA format
	    ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

	    for (int y = 0; y < height; y++)
	    {
	        for (int x = 0; x < width; x++)
	        {
	            int pixel = pixels[y * width + x];

	            buffer.put((byte) ((pixel >> 16) & 0xFF));  // red
	            buffer.put((byte) ((pixel >> 8) & 0xFF));   // green
	            buffer.put((byte) (pixel & 0xFF));          // blue
	            buffer.put((byte) ((pixel >> 24) & 0xFF));  // alpha
	        }
	    }
	    buffer.flip(); // this will flip the cursor image vertically

	    // create a GLFWImage
	    GLFWImage cursorImg= GLFWImage.create();
	    cursorImg.width(width);     // setup the images' width
	    cursorImg.height(height);   // setup the images' height
	    cursorImg.pixels(buffer);   // pass image data

	    // create custom cursor and store its ID
	    int hotspotX = 0;
	    int hotspotY = 0;
	    long cursorID = GLFW.glfwCreateCursor(cursorImg, hotspotX , hotspotY);

	    // set current cursor
	    glfwSetCursor(window.getWindow(), cursorID);
	    
		
		
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		inputHandler = new InputHandler(window);
		
		RawModel playerShip = OBJLoader.loadObjModel("Puddle Jumper", loader);
		TexturedModel playership = new TexturedModel(playerShip, ModelTexture.loadTexture("white"));
		player = new Player(playership, new Vector3f(0, 0, 0),0,0,0,1);
		
		
		RawModel ship = OBJLoader.loadObjModel("Puddle Jumper", loader);
		TexturedModel random = new TexturedModel(ship, ModelTexture.loadTexture("white"));
		entity = new Entity(random, new Vector3f(0, 0, -50),0,0,0,1);
		
		lights.add(new Light(new Vector3f(0,1000,-5000), new Vector3f(0f,0.4f,5f)));
		lights.add(new Light(new Vector3f(0,0,-10), new Vector3f(1,0,1), new Vector3f(1, 0.01f, 0.002f)));
		

		try {
			camera = new Camera(player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void start()
	{
		if(running) return;
		running = true;
		run();
		
		
	}
	
	
	private void input()
	{
		
		glfwPollEvents();
		if(getWindow().shouldClose()) stop();
		
		/*player.linearAcc.zero();
        float accFactor = 0.001f;
        float rotateZ = 0.0f;
        if (inputHandler.keys[GLFW_KEY_W]) ;
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
       

		//Move Forward/Backwards
		if(inputHandler.keys[GLFW_KEY_W])
		{
			
			player.moveForward();
			
		}
		else if(inputHandler.keys[GLFW_KEY_S])
		{
			player.moveBack();

		}
		else
		{
			player.stopZ();
		}
		
		//turn Left/Right
		if(inputHandler.keys[GLFW_KEY_A])
		{
			player.moveLeft();
		}
		else if(inputHandler.keys[GLFW_KEY_D])
		{
			player.moveRight();
		}
		else
		{
			player.stop_yaw();
		}
		
		
		if(inputHandler.keys[GLFW_KEY_Q])
		{
			player.pitchUp();
		}
		else if(inputHandler.keys[GLFW_KEY_E])
		{
			player.pitchDown();
		}
		else
		{
			player.stop_pitch();
		}
		
		if(inputHandler.keys[GLFW_KEY_Z])
		{
			player.rollback();
		}
		else if(inputHandler.keys[GLFW_KEY_C])
		{
			player.roll();
		}
		else
		{
			player.stop_roll();
		}
		

		//Camera Debug
		
		
		
	}
	
	
	
	private void update()
	{
	
		player.move(delta);
		camera.move(delta);
		
		
		//System.out.println("Player XPOS");
		//System.out.println(player.getPosition().x);
		//System.out.println("Player YPOS");
		//System.out.println(player.getPosition().y);
		//System.out.println("Player ZPOS");
		//System.out.println(player.getPosition().z);
		
		//System.out.println("Player Pitch");
		//System.out.println(player.getRotX());
		
		//System.out.println("Camera XPOS");
		//System.out.println(camera.getPosition().x);
		//System.out.println("Camera YPOS");
		//System.out.println(camera.getPosition().y);
		//System.out.println("Camera ZPOS");
		//System.out.println(camera.getPosition().z);
	}
	
	private  void render()
	{
		
		renderer.processEntity(player);
		renderer.processEntity(entity);
		renderer.render(lights, camera);
		getWindow().render();
	}
	
	private void run()
	{
		
		long lastTime = System.nanoTime();
		long currentTime = lastTime;
		long diff = 0;
		
		long timer = System.currentTimeMillis();
		
		double ns = 1000000000 / 60.0;
		delta = 0.0;
		
		double dfps = 1000000000 / 60.0;
		double d = 0.0;
		
		int fps = 0;
		int ups = 0;
		
		while(running)
		{
			
			currentTime = System.nanoTime();
			diff = currentTime - lastTime;
			delta += (diff)/ ns;
			d += diff/dfps;
			lastTime = currentTime;
			while(delta >= 1.0)
			{
				input();
				update();
				ups++;
				delta--;
			}
			
			if(d >- 1.0)
			{
				render();
				fps++;
				d = 0.0;
			}
			
			if(System.currentTimeMillis() > timer + 1000)
			{
				getWindow().setTitle("Junker.5 | ups: "+ups+"| fps: "+fps+"");
				ups = 0;
				fps = 0;
				timer += 1000;
			}
		}
		
		cleanUp();
	}
	
	
	
	
	
	public void stop()
	{
		if(!running) return;
		running = false;
	}
	
	private void cleanUp()
	{
		getWindow().hide();
		//guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		getWindow().dispose();
	}
	
	
	public int getWidth()
	{
		return getWindow().getWidth();
	}
	
	public int getheight()
	{
		return getWindow().getHeight();
	}
		
	public double getDelta()
	{
		return delta;
	}
	
	public Window getWindow() 
	{
		return window;
	}
	public void setWindow(Window window)
	{
		this.window = window;
	}

}
