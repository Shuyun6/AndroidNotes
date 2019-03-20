package com.shuyun.androidnotes.gles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;

import com.shuyun.androidnotes.App;
import com.shuyun.androidnotes.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.*;

public class TextureRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private IGLHelper helper;
    private int program;

    //vertex
    private int aPositionLocation;
    private int aTextureCoordinatesLocation;

    //fragment
    private int uTextureUnitLocation;

    private FloatBuffer vertexData, coordData;
    private float[] rect = {
            -1f, 1f,//left-top
            -1f, -1f,//left-bottom
            1f, 1f,//right-top
            1f, -1f//right-bottom
    };

    //Because the texture coordinate is difference from GL View's coordinate
    //It's the same as Android view's
    //the left-top is the (0, 0)
    //and then right-bottom is the (1, 1)
    private float coord[] = {
            0f, 0f,//left-top
            0f, 1f,//left-bottom
            1f, 0f,//right-top
            1f, 1f//right-bottom
    };

    private Bitmap bitmap;
    private int textureId;

    public TextureRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 1.0f, 0.5f);
        helper = GLHelperFactory.getGLHelper(App.Companion.getInstance());
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_luna);

        //generate a GL program with vertex shader and fragment shader
        program = helper.genProgram(R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        aPositionLocation = glGetAttribLocation(program, "a_Position");
        aTextureCoordinatesLocation = glGetAttribLocation(program, "a_TextureCoordinates");
        uTextureUnitLocation = glGetUniformLocation(program, "u_TextureUnit");

        //set up native buffer through float[]
        vertexData = ByteBuffer.allocateDirect(rect.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(rect);
        vertexData.position(0);
        coordData = ByteBuffer.allocateDirect(coord.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        coordData.put(coord);
        coordData.position(0);

        //Put vertex data into a_position field and enable it
        helper.makeAttribRef(aPositionLocation, vertexData, 0);
        helper.makeAttribRef(aTextureCoordinatesLocation, coordData, 0);

        loadImage();

    }

    private void loadImage(){
        textureId = helper.genTexture();
        helper.bindTexture(textureId);
        helper.initTextureParams();
        helper.putBitmap(bitmap);
        helper.bindTexture(0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        helper.setViewPort(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        helper.clearView();
        helper.drawTriangleStrip(0, 4);
    }
}
