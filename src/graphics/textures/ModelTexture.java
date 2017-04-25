package graphics.textures;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.stb.STBImage.*;

public class ModelTexture 
{

	private int textureID;
	
	private float shineDamper = 1;
	private float refectivity = 0;
	private final int width;
	private final int height;
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	public ModelTexture(int width, int height, ByteBuffer data)
	{
        textureID = GL11.glGenTextures();
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }
	
	public static ModelTexture loadTexture(String filename) 
	{
        /* Prepare image buffers */
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        /* Load image */
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load("./Ressources/Textures/"+filename+".png", w, h, comp, 4);
        if (image == null) {
            throw new RuntimeException("Failed to load a texture file!"+ System.lineSeparator() + stbi_failure_reason());
        }

        /* Get width and height of image */
        int width = w.get();
        int height = h.get();

        return new ModelTexture(width, height, image);
    }
	
	
	
	public boolean isHasTransparency() 
	{
		return hasTransparency;
	}



	public void setHasTransparency(boolean hasTransparency) 
	{
		this.hasTransparency = hasTransparency;
	}


	public boolean isUseFakeLighting() 
	{
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) 
	{
		this.useFakeLighting = useFakeLighting;
	}

	public int getID()
	{
		return this.textureID;
	}

	public float getShineDamper() 
	{
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) 
	{
		this.shineDamper = shineDamper;
	}

	public float getRefectivity() 
	{
		return refectivity;
	}

	public void setRefectivity(float refectivity) 
	{
		this.refectivity = refectivity;
	}
	
	public int getWidth() 
	{
        return width;
    }
	
	public int getHeight() 
	{
        return height;
    }
	
	public void delete() 
	{
        glDeleteTextures(textureID);
    }
	
	public void bind() 
	{
        glBindTexture(GL_TEXTURE_2D, textureID);
    }
	
}
