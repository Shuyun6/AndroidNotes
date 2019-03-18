package com.shuyun.androidnotes.gles;

import java.nio.Buffer;

/**
 * Interface for OpenGL program relatives
 * @Author shuyun
 * @Create at 2019/3/18 0018 23:24
 * @Update at 2019/3/18 0018 23:24
*/
public interface IGLProgram {

    /**
     * Get attribute pointer in shader file from program's id
     * @param programId program id
     * @param field field defined in shader file
     * @return uniform field id
     */
    int getAttribId(int programId, String field);

    /**
     * Compile vertex shader from shader source
     * @param source source id from Resource
     * @return shader'id
     */
    int genVertexShader(int source);

    /**
     * Compile fragment shader from shader source
     * @param source source id from Resource
     * @return shader'id
     */
    int genFragmentShader(int source);

    /**
     * Get program from resource of vertex and fragment shaders
     * @param vertexShaderSource vertex shader source id
     * @param fragmentShaderSource fragment shader source id
     * @return program's id
     */
    int genProgram(int vertexShaderSource, int fragmentShaderSource);

    void makeAttribRef(int id, Buffer data, int stride);

    int getUniformId(int programId, String fieldName);

}
