package graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;


public class Window 
{
	
	private static long window;
	private int Width;
	private int Height;
	
	public Window(int width, int height, String title)
	{
		
		this.Width = width;
		this.Height = height;
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GL11.GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GL11.GL_TRUE);
		//DECORATION
		glfwWindowHint(GLFW_DECORATED, GL11.GL_TRUE);
		glfwWindowHint(GLFW_FOCUSED, GL11.GL_TRUE);
		setWindow(glfwCreateWindow(Width,Height,"Junker.5",NULL,NULL));
	
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

	    glfwSetWindowPos(
	            getWindow(),
	             (vidmode.width() - width) / 2,
	            (vidmode.height() - height) / 2
	        ); 
	    
	    glfwMakeContextCurrent(getWindow());
	}
	
	public void dispose()
	{
		glfwDestroyWindow(getWindow());
	}
	
	public void hide()
	{
		glfwHideWindow(getWindow());
	}
	
	public void render()
	{
		glfwSwapBuffers(getWindow());
	}
	
	public void show()
	{
		glfwShowWindow(getWindow());
	}
	
	public void setTitle(String title)
	{
		glfwSetWindowTitle(getWindow(), title);
	}
	
	public boolean shouldClose() 
	{
		  if(!glfwWindowShouldClose(getWindow())) 
		  {
		   return false;
		  }
		  else
		  {
			  return true;
		  }
	}
	
	
	
	public int getWidth() 
	{
		return Width;
	}

	public void setWidth(int width) 
	{
		Width = width;
	}

	public int getHeight() 
	{
		return Height;
	}

	public void setHeight(int height) 
	{
		Height = height;
	}

	public void changecursor()
	{
		// Create the cursor object
		//long cursor = GLFW.glfwCreateCursor(imageBuffer, 0, 0);
		 
		/*if (cursor == MemoryUtil.NULL)
		    throw new RuntimeException("Error creating cursor");
		 
		// Set the cursor on a window
		GLFW.glfwSetCursor(window, cursor);*/
	}

	public long getWindow() 
	{
		return window;
	}

	public void setWindow(long window) {
		this.window = window;
	}

}
