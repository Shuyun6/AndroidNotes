package com.shuyun.androidnotes.gles;

import android.content.Context;
import android.content.res.Resources;

import com.shuyun.androidnotes.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.Buffer;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glValidateProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

public class GLProgramImpl implements IGLProgram {

    private WeakReference<Context> weakContext;

    GLProgramImpl(Context context) {
        weakContext = new WeakReference<>(context);
    }

    private Context getContext(){
        return weakContext.get();
    }

    @Override
    public int getAttribId(int programId, String field) {
        return glGetAttribLocation(programId, field);
    }

    @Override
    public int genVertexShader(int source) {
        return compileShader(GL_VERTEX_SHADER, readResource(source));
    }

    @Override
    public int genFragmentShader(int source) {
        return compileShader(GL_FRAGMENT_SHADER, readResource(source));
    }

    @Override
    public int genProgram(int vertexShaderSource, int fragmentShaderSource) {
        int id = linkProgram(genVertexShader(vertexShaderSource), genFragmentShader(fragmentShaderSource));
        glUseProgram(id);
        return id;
    }

    @Override
    public void makeAttribRef(int id, Buffer data, int stride) {
        glVertexAttribPointer(id, 2, GL_FLOAT, false, stride, data);
        glEnableVertexAttribArray(id);
    }

    @Override
    public int getUniformId(int programId, String fieldName) {
        return glGetUniformLocation(programId, fieldName);
    }

    private int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = glCreateProgram();
        if(programObjectId == 0){
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        glValidateProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        Log.Companion.e("linkProgram "+linkStatus[0]);
        if(linkStatus[0] == 0){
            glDeleteProgram(programObjectId);
            return 0;
        }
        return programObjectId;
    }

    private int compileShader(int type, String shaderCode){
        int id = glCreateShader(type);
        if(id == 0){
            return id;
        }
        //Add shader source into a shader object with id
        glShaderSource(id, shaderCode);
        //Then compile it by id
        glCompileShader(id);
        final int[] compileStatus = new int[1];
        //get compilation status from id
        glGetShaderiv(id, GL_COMPILE_STATUS, compileStatus, 0);
        Log.Companion.e("compileStatus[0] "+compileStatus[0]);
        if(compileStatus[0] == 0){
            glDeleteShader(id);
            Log.Companion.e(glGetShaderInfoLog(id));
            return 0;
        }
        return id;
    }

    private String readResource(int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = getContext().getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return body.toString();
    }

}
