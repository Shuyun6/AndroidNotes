package com.shuyun.androidnotes.gles;

import android.content.Context;
import android.graphics.Rect;

import java.nio.Buffer;

class GLHelperImpl implements IGLHelper {

    private IGLDraw iglDraw;
    private IGLProgram iglProgram;
    private IGLTexture iglTexture;
    private IGLegl igLegl;

    GLHelperImpl(Context context) {
        Factory factory = new Factory(context);
        iglDraw = factory.getGLDraw();
        iglProgram = factory.getGLProgram();
        iglTexture = factory.getGLTexture();
        igLegl = factory.getGLegl();
    }

    @Override
    public void clearView() {
        iglDraw.clearView();
    }

    @Override
    public void setViewPort(Rect rect) {
        iglDraw.setViewPort(rect);
    }

    @Override
    public void setViewPort(int width, int height) {
        iglDraw.setViewPort(width, height);
    }

    @Override
    public void drawTriangle(int from, int to) {
        iglDraw.drawTriangle(from, to);
    }

    @Override
    public void updateUniformColor(int id, float red, float green, float blue, float alpha) {
        iglDraw.updateUniformColor(id, red, green, blue, alpha);
    }

    @Override
    public int getAttribId(int programId, String field) {
        return iglProgram.getAttribId(programId, field);
    }

    @Override
    public int genVertexShader(int source) {
        return iglProgram.genVertexShader(source);
    }

    @Override
    public int genFragmentShader(int source) {
        return iglProgram.genFragmentShader(source);
    }

    @Override
    public int genProgram(int vertexShaderSource, int fragmentShaderSource) {
        return iglProgram.genProgram(vertexShaderSource, fragmentShaderSource);
    }

    @Override
    public void makeAttribRef(int id, Buffer data, int stride) {
        iglProgram.makeAttribRef(id, data, stride);
    }

    @Override
    public int getUniformId(int programId, String fieldName) {
        return iglProgram.getUniformId(programId, fieldName);
    }

    @Override
    public int genTexture() {
        return iglTexture.genTexture();
    }

    @Override
    public void bindTexture(int textureId) {
        iglTexture.bindTexture(textureId);
    }

    @Override
    public void unbindTexture() {
        iglTexture.unbindTexture();
    }

    @Override
    public void initTextureParams() {
        iglTexture.initTextureParams();
    }

    private static final class Factory {

        static volatile Factory factory;
        Context context;

        private Factory(Context context) {
            this.context = context;
        }

        static Factory getInstance(Context context){
            if (null == factory) {
                synchronized (Factory.class){
                    if (null == factory) {
                        factory = new Factory(context);
                    }
                }
            }
            return factory;
        }

        IGLDraw getGLDraw(){
            return new GLDrawImpl();
        }

        IGLProgram getGLProgram(){
            return new GLProgramImpl(context);
        }

        IGLTexture getGLTexture(){
            return new GLTextureImpl();
        }

        IGLegl getGLegl(){
            return new GLeglImpl();
        }

    }

}
