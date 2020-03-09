package com.shuyun.androidnotes.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.shuyun.androidnotes.R;
import com.shuyun.androidnotes.utils.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Camera2WithSurfaceViewActivity extends AppCompatActivity {

    private AutoFitSurfaceView sv;
    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private CameraCaptureSession session;
    private CaptureRequest.Builder captureRequestBuilder;
    private ImageReader imageReader;
    private Handler handler, handler2;
    private SurfaceHolder holder;
    private String cameraId = String.valueOf(CameraCharacteristics.LENS_FACING_FRONT);
    private int width = 2400, height = 1080;

    private DisplayMetrics dm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        window.setFlags(flag, flag);
        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_camera2_surfaceview);
        sv = findViewById(R.id.sv);
        HandlerThread handlerThread = new HandlerThread("cameraHandlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        HandlerThread handlerThread2 = new HandlerThread("cameraHandlerThread2");
        handlerThread2.start();
        handler2 = new Handler(handlerThread2.getLooper());
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        Log.Companion.e("screen display size: "+display.getWidth()+"-"+display.getHeight());
        sv.setRate(width, height);
        holder = sv.getHolder();


    }

    @Override
    protected void onResume() {
        super.onResume();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                int w = dm.widthPixels;
                int h = dm.heightPixels;
                Log.Companion.e("screen pixel size: "+w+"-"+h);

                holder.setFixedSize(height, width);
                Log.Companion.e("setting size: "+w+"-"+h);
                requestCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.Companion.e("surfaceChanged size: "+width+"-"+height);
                sv.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.Companion.e("view size: "+sv.getWidth()+"-"+sv.getHeight());
                    }
                }, 300);

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != session) {
            session.close();
//            session = null;
        }
        if (null != cameraDevice) {
            cameraDevice.close();
//            cameraDevice = null;
        }
    }

    private void requestCamera() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length > 0 && permissions[0].equals(Manifest.permission.CAMERA) && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                openCamera();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void openCamera() throws CameraAccessException {
        cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            previewCamera();
                        } catch (CameraAccessException e) {
                            Log.Companion.e("test "+e.getMessage());
                        }

                    }
                };
                cameraDevice = camera;
                runnable.run();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                cameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int i) {

            }
        }, handler);
    }

    private void previewCamera() throws CameraAccessException {

        CameraCharacteristics chars = cameraManager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap map = chars.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] sizes = map.getOutputSizes(SurfaceHolder.class);
//        for (Size size : sizes) {
//            Log.Companion.e(size.toString());
//        }

        imageReader = ImageReader.newInstance(4000, 3000, ImageFormat.YUV_420_888, 2);
        imageReader.setOnImageAvailableListener(imageReader -> {
            Image image = imageReader.acquireNextImage();
            try {
                if (null != image) {
                    handleFrame(image);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != image) {
                    image.close();
                }
                capture();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }, handler2);
        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        captureRequestBuilder.addTarget(holder.getSurface());
//        captureRequestBuilder.addTarget(imageReader.getSurface());
        List<Surface> list = new ArrayList<>();
        list.add(holder.getSurface());
        list.add(imageReader.getSurface());
        cameraDevice.createCaptureSession(list, new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                CaptureRequest request = captureRequestBuilder.build();
                session = cameraCaptureSession;
                try {
                    session.setRepeatingRequest(request, new CameraCaptureSession.CaptureCallback() {
                        @Override
                        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                            super.onCaptureStarted(session, request, timestamp, frameNumber);
                            startCapture();

                        }
                    }, handler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

            }
        }, handler);


    }

    private boolean isFirst = true;
    private void startCapture() {
        if (!isFirst) {
            return;
        }
        isFirst = false;
        try {
            capture();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void capture() throws CameraAccessException {
        if (null == cameraDevice || null == session) {
            return;
        }
        CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        builder.addTarget(imageReader.getSurface());
        session.capture(builder.build(), new CameraCaptureSession.CaptureCallback() {
            @Override
            public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                super.onCaptureCompleted(session, request, result);

            }
        }, handler2);
    }

    private void handleFrame(Image image) {
        Image.Plane[] planes = image.getPlanes();
        Image.Plane yPlane = planes[0];
        Image.Plane uPlane = planes[1];
        Image.Plane vPlane = planes[2];
        ByteBuffer yBuff = yPlane.getBuffer();
        ByteBuffer uBuff = uPlane.getBuffer();
        ByteBuffer vBuff = vPlane.getBuffer();
        byte[] yData = new byte[yBuff.limit()];
        byte[] uData = new byte[uBuff.limit()];
        byte[] vData = new byte[vBuff.limit()];
        yBuff.get(yData);
        uBuff.get(uData);
        vBuff.get(vData);
        int width = image.getWidth();
        int height = image.getHeight();
        int rectSize = width * height;
        byte[] data = new byte[rectSize*3/2];

        int yRowStride = yPlane.getRowStride();
        int yPixelStride = yPlane.getPixelStride();
        int vRowStride = vPlane.getRowStride();
        int vPixelStride = vPlane.getPixelStride();
        if (yRowStride == width) {
            System.arraycopy(yData, 0, data, 0, yData.length);
            System.arraycopy(vData, 0, data, rectSize, vData.length);
        } else {
            for (int r = 0; r < height; r++) {
                System.arraycopy(yData, r * yRowStride, data, width * r, width);
                if (r % 2 == 0) {
                    if (r == height - 2) {
                        System.arraycopy(vData,  vRowStride * r /2, data, rectSize + width * r / 2, width-1);
                    } else {
                        System.arraycopy(vData, vRowStride * r /2, data, rectSize + width * r / 2, width);
                    }
                }
            }
        }

        Bitmap bitmap = rawByteArray2RGBABitmap2(data, image.getWidth(), image.getHeight());
        if (null == bitmap) {

        }
    }

    public byte[] rotate(byte[] data, int width, int height) {
        byte[] yuv = new byte[width * height * 3 / 2];
        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int y = height - 1; y >= 0; y--) {
                yuv[index] = data[y * width + i];
                index++;
            }
        }
        index = width * height * 3 / 2 - 1;
        for (int i = width - 1; i > 0; i = i - 2) {
            for (int j = 0; j < height / 2; j++) {
                yuv[index] = data[(width * height) + (j * width) + i];
                index--;
                yuv[index] = data[(width * height) + (j * width) + i - 1];
                index--;
            }
        }
        return yuv;

    }

    public Bitmap rawByteArray2RGBABitmap2(byte[] data, int width, int height) {
        int frameSize = width * height;
        int[] rgba = new int[frameSize];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = (0xff & ((int) data[i * width + j]));
                int u = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 0]));
                int v = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 1]));
                y = y < 16 ? 16 : y;

                int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
                int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
                int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));

                r = r < 0 ? 0 : (r > 255 ? 255 : r);
                g = g < 0 ? 0 : (g > 255 ? 255 : g);
                b = b < 0 ? 0 : (b > 255 ? 255 : b);

                rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;
            }

        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.setPixels(rgba, 0, width, 0, 0, width, height);
        return bmp;
    }


}
