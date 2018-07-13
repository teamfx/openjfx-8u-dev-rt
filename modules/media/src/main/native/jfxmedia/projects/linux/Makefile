#
# Linux/ARM jfxmedia makefile
#

BUILD_DIR = $(OUTPUT_DIR)/$(BUILD_TYPE)

SRCBASE_DIR = ../..
OBJBASE_DIR = $(BUILD_DIR)/obj/jfxmedia

DIRLIST = jni                \
          PipelineManagement \
          MediaManagement    \
          Locator 	     \
          Utils 	     \
          Utils/posix 	     \
          platform/gstreamer

TARGET = $(BUILD_DIR)/lib$(BASE_NAME).so

CFLAGS = -DTARGET_OS_LINUX=1     \
         -D_GNU_SOURCE           \
         -DGST_REMOVE_DEPRECATED \
         -DGST_DISABLE_GST_DEBUG \
         -DGST_DISABLE_LOADSAVE  \
         -DGST_DISABLE_XML       \
         -DHAVE_CONFIG_H         \
         -DJFXMEDIA_JNI_EXPORTS  \
         -DLINUX                 \
         -DG_DISABLE_DEPRECATED  \
         -ffunction-sections -fdata-sections

CPPFLAGS = -fno-rtti -ffunction-sections -fdata-sections

BASE_INCLUDES = -I$(SRCBASE_DIR) \
		-I$(GENERATED_HEADERS_DIR)

ifdef HOST_COMPILE
	GSTREAMER_LITE_DIR = ../../../gstreamer/gstreamer-lite
	GLIB_LITE_DIR = ../../../gstreamer/3rd_party/glib/glib-2.56.1

	CFLAGS += -fPIC \
                  -Wformat \
                  -Wextra \
                  -Wformat-security \
                  -Werror=implicit-function-declaration \
		  -msse2 \
	          -DGSTREAMER_LITE

	INCLUDES = $(BASE_INCLUDES) \
		   -I$(JAVA_HOME)/include \
		   -I$(JAVA_HOME)/include/linux \
	           -I$(GSTREAMER_LITE_DIR)/gstreamer \
		   -I$(GSTREAMER_LITE_DIR)/gst-plugins-base/gst-libs \
	           -I$(GSTREAMER_LITE_DIR)/gstreamer/libs \
	           -I$(GLIB_LITE_DIR)/ \
	           -I$(GLIB_LITE_DIR)/glib \
	           -I$(GLIB_LITE_DIR)/gmodule

	LDFLAGS = -Wl,-rpath,\$$ORIGIN -L$(BUILD_DIR) -lgstreamer-lite -lglib-lite \
            -static-libgcc -static-libstdc++ -Wl,--gc-sections
else
	CFLAGS += $(EXTRA_CFLAGS)
	INCLUDES = $(BASE_INCLUDES)
	LDFLAGS = -Wl,-rpath,\$$ORIGIN -L$(BUILD_DIR) $(EXTRA_LDFLAGS)
endif

ifeq ($(BUILD_TYPE), Release)
	CFLAGS += -Os
else
	CFLAGS += -g -Wall -D_DEBUG
endif

ifeq ($(ARCH), x32)
    CFLAGS += -m32
    LDFLAGS += -m32
    INCLUDES += -I$(GLIB_LITE_DIR)/build/linux/x86
else
    INCLUDES += -I$(GLIB_LITE_DIR)/build/linux/x64
endif

CPP_SOURCES = \
        jni/com_sun_media_jfxmedia_logging_Logger.cpp           \
        jni/JavaBandsHolder.cpp 				\
        jni/JavaMediaWarningListener.cpp 			\
        jni/JavaPlayerEventDispatcher.cpp 			\
        jni/JniUtils.cpp 					\
        jni/Logger.cpp 						\
        jni/NativeVideoBuffer.cpp 				\
        jni/NativeAudioEqualizer.cpp \
        jni/NativeAudioSpectrum.cpp \
        jni/NativeEqualizerBand.cpp \
        jni/JavaInputStreamCallbacks.cpp 			\
        PipelineManagement/AudioTrack.cpp 			\
        PipelineManagement/Pipeline.cpp 			\
        PipelineManagement/PipelineFactory.cpp 			\
        PipelineManagement/Track.cpp 				\
        PipelineManagement/VideoFrame.cpp 			\
        PipelineManagement/VideoTrack.cpp 			\
        PipelineManagement/SubtitleTrack.cpp                    \
        MediaManagement/Media.cpp 				\
        MediaManagement/MediaManager.cpp 			\
        Locator/Locator.cpp 					\
        Locator/LocatorStream.cpp 				\
        Utils/MediaWarningDispatcher.cpp 			\
        Utils/posix/posix_critical_section.cpp          \
        platform/gstreamer/GstMedia.cpp                 \
        platform/gstreamer/GstMediaPlayer.cpp           \
        platform/gstreamer/GstPlatform.cpp              \
        platform/gstreamer/GstAudioEqualizer.cpp        \
        platform/gstreamer/GstAudioPlaybackPipeline.cpp \
        platform/gstreamer/GstAudioSpectrum.cpp         \
        platform/gstreamer/GstAVPlaybackPipeline.cpp    \
        platform/gstreamer/GstElementContainer.cpp      \
        platform/gstreamer/GstJniUtils.cpp              \
        platform/gstreamer/GstMediaManager.cpp          \
        platform/gstreamer/GstPipelineFactory.cpp       \
        platform/gstreamer/GstVideoFrame.cpp

C_SOURCES = Utils/ColorConverter.c


OBJECTS  = $(patsubst %.cpp,$(OBJBASE_DIR)/%.o,$(CPP_SOURCES)) $(patsubst %.c,$(OBJBASE_DIR)/%.o,$(C_SOURCES))
DEPFILES = $(patsubst %.cpp,$(OBJBASE_DIR)/%.d,$(CPP_SOURCES))

OBJ_DIRS = $(addprefix $(OBJBASE_DIR)/,$(DIRLIST))

DEP_DIRS = $(BUILD_DIR) $(OBJ_DIRS)

.PHONY: default list

default: $(TARGET)

$(DEPFILES): | $(DEP_DIRS)

$(DEP_DIRS):
	mkdir -p $(DEP_DIRS)

$(TARGET): $(DEPFILES) $(OBJECTS)
	$(LINK) -shared $(OBJECTS) $(LDFLAGS) -o $@

$(OBJBASE_DIR)/%.o: $(SRCBASE_DIR)/%.cpp
	$(CC) $(CFLAGS) $(CPPFLAGS) $(INCLUDES) -x c++ -c $< -o $@

$(OBJBASE_DIR)/%.o: $(SRCBASE_DIR)/%.c
	$(CC) $(CFLAGS) $(INCLUDES) -x c -c $< -o $@

# Build dependency graph
$(OBJBASE_DIR)/%.d: $(SRCBASE_DIR)/%.cpp
	@set -e; rm -f $@; \
	$(CC) $(CFLAGS) $(CPPFLAGS) $(INCLUDES) -M $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$

-include $(DEPFILES)
