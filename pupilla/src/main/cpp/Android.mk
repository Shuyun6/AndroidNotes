PROJ_PATH	:= $(call my-dir)
include $(CLEAR_VARS)
include $(PROJ_PATH)/pupilla/Android.mk
include $(PROJ_PATH)/libjpeg-turbo-1.5.0/Android.mk
include $(PROJ_PATH)/libusb1/android/jni/Android.mk
include $(PROJ_PATH)/libuvc1/android/jni/Android.mk