package com.shuyun.androidnotes.gles;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.shuyun.androidnotes.App;
import com.shuyun.androidnotes.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_LUMINANCE;
import static android.opengl.GLES20.GL_LUMINANCE_ALPHA;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glClearColor;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glUniform1i;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE1;

public class YuvRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private IGLHelper helper;
    private int program;

    //vertex
    private int aPositionLocation;
    private int aTextureCoordinatesLocation;

    //fragment
    private int uTextureUnitLocation, uTextureUnitLocation2;

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

    private int textureY, textureUV;
    private byte[] yuvData = new byte[640 * 480 * 2];
    private byte[] yData = new byte[640 * 480];
    private byte[] uvData = new byte[640 * 240];
    private ByteBuffer bufferY, bufferUV;


    public YuvRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 1.0f, 0.5f);
        helper = GLHelperFactory.getGLHelper(App.Companion.getInstance());
        try {
            InputStream is = context.getAssets().open("yuv.dat");
            is.read(yuvData);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.arraycopy(yuvData, 0, yData, 0, 640 * 480);
        bufferY = ByteBuffer.allocateDirect(640 * 480)
                .order(ByteOrder.nativeOrder());
        bufferY.put(yData, 0, 640 * 480);
        bufferY.position(0);

        System.arraycopy(yuvData, 640 * 480, uvData, 0, 640 * 240);
        bufferUV = ByteBuffer.allocateDirect(640 * 240)
                .order(ByteOrder.nativeOrder());
        bufferUV.put(uvData, 0, 640 * 240);
        bufferUV.position(0);

        //generate a GL program with vertex shader and fragment shader
        program = helper.genProgram(R.raw.texture_vertex_shader, R.raw.yuv_texture_fragment_shader);
        aPositionLocation = glGetAttribLocation(program, "a_Position");
        aTextureCoordinatesLocation = glGetAttribLocation(program, "a_TextureCoordinates");
        uTextureUnitLocation = glGetUniformLocation(program, "u_TextureY");
        uTextureUnitLocation2 = glGetUniformLocation(program, "u_TextureUV");

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
        textureY = helper.genTexture();
        helper.bindTexture(textureY);
        helper.initTextureParams();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, 640, 480,
                0, GL_LUMINANCE, GL_UNSIGNED_BYTE, bufferY);
        helper.bindTexture(0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureY);
        glUniform1i(uTextureUnitLocation, 0);

//        textureUV = helper.genTexture();
//        helper.bindTexture(textureUV);
//        helper.initTextureParams();

//        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE_ALPHA, 640/2, 480/2,
//                0, GL_LUMINANCE_ALPHA, GL_UNSIGNED_BYTE, bufferUV);
//        helper.bindTexture(0);
//        glActiveTexture(GL_TEXTURE1);
//        glBindTexture(GL_TEXTURE_2D, textureUV);
//        glUniform1i(uTextureUnitLocation2, 1);
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
