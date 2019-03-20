//package com.shuyun.androidnotes.gles;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Rect;
//
//import java.nio.Buffer;
//
//public class GLHelperProxy implements IGLHelper {
//
//    private IGLHelper helper;
//
//    public GLHelperProxy(Context context) {
//        helper = new GLHelperImpl(context);
//    }
//
//    @Override
//    public void clearView() {
//        helper.clearView();
//    }
//
//    @Override
//    public void clearColor(int red, int green, int blue, int alpha) {
//        helper.clearColor(red, green, blue, alpha);
//    }
//
//    @Override
//    public void setViewPort(Rect rect) {
//        helper.setViewPort(rect);
//    }
//
//    @Override
//    public void setViewPort(int width, int height) {
//        helper.setViewPort(width, height);
//    }
//
//    @Override
//    public void drawTriangle(int from, int to) {
//        helper.drawTriangle(from, to);
//    }
//
//    @Override
//    public void drawTriangleFan(int from, int to) {
//        helper.drawTriangleFan(from, to);
//    }
//
//    @Override
//    public void drawTriangleStrip(int from, int to) {
//        helper.drawTriangleStrip(from, to);
//    }
//
//    @Override
//    public void updateUniformColor(int id, float red, float green, float blue, float alpha) {
//        helper.updateUniformColor(id, red, green, blue, alpha);
//    }
//
//    @Override
//    public int getAttribId(int programId, String field) {
//        return helper.getAttribId(programId, field);
//    }
//
//    @Override
//    public int genVertexShader(int source) {
//        return helper.genVertexShader(source);
//    }
//
//    @Override
//    public int genFragmentShader(int source) {
//        return helper.genFragmentShader(source);
//    }
//
//    @Override
//    public int genProgram(int vertexShaderSource, int fragmentShaderSource) {
//        return helper.genProgram(vertexShaderSource, fragmentShaderSource);
//    }
//
//    @Override
//    public void makeAttribRef(int id, Buffer data, int stride) {
//        helper.makeAttribRef(id, data, stride);
//    }
//
//    @Override
//    public int getUniformId(int programId, String fieldName) {
//        return helper.getUniformId(programId, fieldName);
//    }
//
//    @Override
//    public int genTexture() {
//        return helper.genTexture();
//    }
//
//    @Override
//    public void bindTexture(int textureId) {
//        helper.bindTexture(textureId);
//    }
//
//    @Override
//    public void setLocation(int location) {
//        helper.setLocation(location);
//    }
//
//    @Override
//    public void unbindTexture() {
//        helper.unbindTexture();
//    }
//
//    @Override
//    public void initTextureParams() {
//        helper.initTextureParams();
//    }
//
//    @Override
//    public void putBitmap(Bitmap bitmap) {
//        helper.putBitmap(bitmap);
//    }
//}
