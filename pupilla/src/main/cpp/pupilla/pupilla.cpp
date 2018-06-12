//
// Created by Shuyun on 2018/1/24 0024.
//

#include <jni.h>
#include <include/libuvc/libuvc.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_shuyun_pupilla_Pupilla_getConfiguration(JNIEnv *env, jobject instance) {

    uvc_error_t result = UVC_ERROR_BUSY;
    uvc_context_t *context;
    result = uvc_init(&context, NULL);
    if (result == UVC_SUCCESS) {
        return env->NewStringUTF("UVC_SUCCESS");
    } else {
        return env->NewStringUTF("OTHER");
    }
}