package com.shuyun.androidnotes.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Surface
import android.view.TextureView
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.utils.Log
import kotlinx.android.synthetic.main.activity_camera2.*

/**
 * An activity holds camera2 to preview(advance experience)
 * @author shuyun
 * @created on 2018/7/4 0004 15:35
 * @changed on 2018/7/4 0004 15:35
 */
class Camera2PlusActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {

    private lateinit var handler: Handler
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var imageReader: ImageReader

    private var cameraDevice: CameraDevice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera2)
        //1. initialize handler for thread
        handler = Handler()
        //2. initialize surface
        ttvCamera.surfaceTextureListener = this
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions[0] == Manifest.permission.CAMERA && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //5. open camera
            cameraManager.openCamera(cameraId, stateCallback, null)
        } else {
            Log.e("CAMERA PERMISSION DENY")
        }
    }

    /** Following is the same code from Java **/
    /*    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {

            @Override
            public void onOpened(CameraDevice camera) {
            }

            @Override
            public void onError(CameraDevice camera, int error) {
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
            }
        };*/
    private val stateCallback = object : CameraDevice.StateCallback(){
        override fun onOpened(camera: CameraDevice?) {
            cameraDevice = camera
            preview(cameraDevice)
        }

        override fun onDisconnected(camera: CameraDevice?) {
            cameraDevice?.close()
        }

        override fun onError(camera: CameraDevice?, error: Int) {

        }

    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        //3. set camera id
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = CameraCharacteristics.LENS_FACING_FRONT.toString()
//        val cameraChara = cameraManager.getCameraCharacteristics(cameraId)
//        val configMap = cameraChara.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
//        val previewSize = configMap.getOutputSizes(SurfaceTexture::class.java)
//        val m = cameraManager.cameraIdList
//        for (id in m) {
//            Log.e("test", id)
//        }
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                1)
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
        } else {
            TODO("VERSION.SDK_INT < M")
        }) {
        }

    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        cameraDevice?.close()
        return true
    }

    private fun preview(camera: CameraDevice?) {
        val surfaceTexture = ttvCamera.surfaceTexture
        val w = ttvCamera.width
        val h = ttvCamera.height
        surfaceTexture.setDefaultBufferSize(w, h)
        val surface = Surface(surfaceTexture)
        //create capture session
        val builder = camera?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
//        imageReader = ImageReader.newInstance(w, h, ImageFormat.YUV_420_888, 2)
//        imageReader.setOnImageAvailableListener({  }, null)
        builder?.addTarget(surface)
//        builder?.addTarget(imageReader.surface)
        val list = ArrayList<Surface>()
        list.add(surface)
//        list.add(imageReader.surface)
        camera?.createCaptureSession(list, object : CameraCaptureSession.StateCallback(){
            override fun onConfigureFailed(session: CameraCaptureSession?) {
            }

            override fun onConfigured(session: CameraCaptureSession?) {
                builder?.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                builder?.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                session?.setRepeatingRequest(builder?.build(), null, null)
            }
        }, null)

    }

}