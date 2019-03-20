package com.shuyun.androidnotes.gles;

import android.opengl.GLSurfaceView;

import com.shuyun.androidnotes.App;
import com.shuyun.androidnotes.R;
import com.shuyun.androidnotes.utils.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class SimpleRenderer implements GLSurfaceView.Renderer {

    private IGLHelper helper;
    private int programId;
    private FloatBuffer vertexData;
//    private float[] triangle =
//            {
//                    0f, 0f,
//                    0.5f, 0.5f,
//                    0f, 0.5f
//
//            };
    private float[] triangleFan =
            {
                    0f, 0f,
                    -0.5f, -0.5f,
                    0.5f, -0.5f,
                    0.5f, 0.5f,
                    -0.5f, 0.5f,
                    -0.5f, -0.5f

            };
    private int field_u_Color;
    private int field_a_Position;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 1.0f, 0.5f);
        helper = GLHelperFactory.getGLHelper(App.Companion.getInstance());
        //generate a GL program with vertex shader and fragment shader
        programId = helper.genProgram(R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        //get a uniform field in shader file by it's name
        field_u_Color = helper.getUniformId(programId, "u_Color");
        //get a attribute filed in shader file by it's name
        field_a_Position = helper.getAttribId(programId, "a_Position");

        Log.Companion.e("field_u_Color "+ field_u_Color);
        Log.Companion.e("field_a_Position "+ field_a_Position);

        //set up native buffer through float[]
        vertexData = ByteBuffer.allocateDirect(triangleFan.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(triangleFan);
        vertexData.position(0);

        //Put vertex data into a_position field and enable it
        helper.makeAttribRef(field_a_Position, vertexData, 0);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        helper.setViewPort(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        //update u_Color values
        helper.updateUniformColor(field_u_Color, 1.0f, 0f, 0f, 1f);
        helper.drawTriangleFan(0, 6);

    }
}
