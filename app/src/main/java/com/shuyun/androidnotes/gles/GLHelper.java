package com.shuyun.androidnotes.gles;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES30;

import com.shuyun.androidnotes.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import static android.opengl.GLES30.*;

public class GLHelper {

    private Context context;
    private static final int DEFAULT_COMPONENT_COUNT = 2;

    public GLHelper(Context context) {
        this.context = context;
    }

    public void updateUniformColor(int id, float red, float green, float blue, float alpha) {
        glUniform4f(id, red, green, blue, alpha);
    }

    /**
     * Make a attribute reference
     * @param id attribute field id
     * @param data attribute data from Java
     * @param stride stride count
     */
    public void makeAttribRef(int id, Buffer data, int stride) {
        glVertexAttribPointer(id, DEFAULT_COMPONENT_COUNT, GL_FLOAT, false, stride, data);
        glEnableVertexAttribArray(id);
    }

    /**
     * Get uniform pointer in shader file from program's id
     * @param programId program id
     * @param field field defined in shader file
     * @return uniform field id
     */
    public int getUniformId(int programId, String field) {
        return glGetUniformLocation(programId, field);
    }

    /**
     * Get attribute pointer in shader file from program's id
     * @param programId program id
     * @param field field defined in shader file
     * @return uniform field id
     */
    public int getAttribId(int programId, String field) {
        return glGetAttribLocation(programId, field);
    }

    /**
     * Compile vertex shader from shader source
     * @param source source id from Resource
     * @return shader'id
     */
    public int genVertexShader(int source) {
        return compileShader(GLES30.GL_VERTEX_SHADER, readResource(source));
    }

    /**
     * Compile fragment shader from shader source
     * @param source source id from Resource
     * @return shader'id
     */
    public int genFragmentShader(int source) {
        return compileShader(GLES30.GL_FRAGMENT_SHADER, readResource(source));
    }

    /**
     * Get program from resource of vertex and fragment shaders
     * @param vertexShaderSource vertex shader source id
     * @param fragmentShaderSource fragment shader source id
     * @return program's id
     */
    public int genProgram(int vertexShaderSource, int fragmentShaderSource) {
        return linkProgram(genVertexShader(vertexShaderSource), genFragmentShader(fragmentShaderSource));
    }

    private int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = GLES30.glCreateProgram();
        if(programObjectId == 0){
            return 0;
        }
        GLES30.glAttachShader(programObjectId, vertexShaderId);
        GLES30.glAttachShader(programObjectId, fragmentShaderId);
        GLES30.glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(programObjectId, GLES30.GL_LINK_STATUS, linkStatus, 0);
        if(linkStatus[0] == 0){
            GLES30.glDeleteProgram(programObjectId);
            return 0;
        }
        return programObjectId;
    }

    private int compileShader(int type, String shaderCode){
        int id = GLES30.glCreateShader(type);
        if(id == 0){
            return id;
        }
        //Add shader source into a shader object with id
        GLES30.glShaderSource(id, shaderCode);
        //Then compile it by id
        GLES30.glCompileShader(id);
        final int[] compileStatus = new int[1];
        //get compilation status from id
        GLES30.glGetShaderiv(id, GLES30.GL_COMPILE_STATUS, compileStatus, 0);
        Log.Companion.e("compileStatus[0] "+compileStatus[0]);
        if(compileStatus[0] == 0){
            GLES30.glDeleteShader(id);
            Log.Companion.e(GLES30.glGetShaderInfoLog(id));
            return 0;
        }
        return id;
    }

    private String readResource(int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
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
