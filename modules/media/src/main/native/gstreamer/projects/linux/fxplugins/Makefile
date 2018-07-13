#
# Linux Makefile for fxplugins
#

BUILD_DIR = $(OUTPUT_DIR)/$(BUILD_TYPE)
TARGET_DIRS = $(BUILD_DIR)

BASE_DIR = ../../..

GLIB_VERSION = 2.56.1
GLIB_DIR = $(BASE_DIR)/3rd_party/glib/glib-$(GLIB_VERSION)

SRCBASE_DIR = ../../../plugins
OBJBASE_DIR = $(BUILD_DIR)/obj/fxplugins

DIRLIST = progressbuffer       \
          progressbuffer/posix \
          javasource

TARGET = $(BUILD_DIR)/lib$(BASE_NAME).so

CFLAGS = -fPIC                   \
         -Wformat                \
         -Wextra                 \
         -Wformat-security       \
         -Werror=implicit-function-declaration \
         -msse2                  \
         -fbuiltin               \
         -DHAVE_STDINT_H         \
         -DLINUX                 \
         -DENABLE_PULL_MODE      \
         -DENABLE_SOURCE_SEEKING \
         -D__MEDIALIB_OLD_NAMES  \
         -DGST_DISABLE_LOADSAVE  \
         -DGST_DISABLE_GST_DEBUG \
         -DGSTREAMER_LITE \
         -DG_DISABLE_DEPRECATED \
         -ffunction-sections -fdata-sections

ifeq ($(BUILD_TYPE), Release)
    CFLAGS += -Os
else
    CFLAGS += -g -Wall
endif


INCLUDES = -I$(SRCBASE_DIR) \
	   $(addprefix -I$(SRCBASE_DIR)/,$(DIRLIST)) \
           -I../../../gstreamer-lite/gstreamer       \
           -I../../../gstreamer-lite/gstreamer/libs  \
           -I$(GLIB_DIR)                             \
           -I$(GLIB_DIR)/glib                        \
           -I$(GLIB_DIR)/gmodule

LDFLAGS = -L$(BUILD_DIR) -lgstreamer-lite -lglib-lite \
          -static-libgcc -static-libstdc++ -Wl,--gc-sections

ifeq ($(ARCH), x32)
    CFLAGS += -m32
    LDFLAGS += -m32
    INCLUDES += -I$(GLIB_DIR)/build/linux/x86
else
    INCLUDES += -I$(GLIB_DIR)/build/linux/x64
endif

SOURCES = fxplugins.c                        \
          progressbuffer/progressbuffer.c    \
          progressbuffer/hlsprogressbuffer.c \
          progressbuffer/posix/filecache.c   \
          javasource/javasource.c            \
          javasource/marshal.c

ifneq ($(strip $(ON2_SRCDIR)),)
DIRLIST += vp6

INCLUDES += -I$(ON2_SRCDIR)/include \
            -I$(ON2_SRCDIR)/src \
            -I$(ON2_SRCDIR)/config/linux

SOURCES += vp6/flvdemux.c    \
           vp6/flvmetadata.c \
           vp6/flvparser.c   \
           vp6/vp6decoder.c

CFLAGS += -DENABLE_ON2_DECODER
endif

OBJ_DIRS = $(addprefix $(OBJBASE_DIR)/,$(DIRLIST))
OBJECTS = $(patsubst %.c,$(OBJBASE_DIR)/%.o,$(SOURCES))

.PHONY: default list

default: $(TARGET)

$(OBJBASE_DIR)/%.o: $(SRCBASE_DIR)/%.c
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

$(OBJECTS): | $(OBJ_DIRS) $(TARGET_DIRS)

$(OBJ_DIRS):
	mkdir -p $(OBJ_DIRS)

$(TARGET_DIRS):
	mkdir -p $(TARGET_DIRS)

$(TARGET): $(OBJECTS)
	$(CC) -shared $(OBJECTS) $(ON2_LIB) $(ON2_LIB) $(LDFLAGS) -o $@
