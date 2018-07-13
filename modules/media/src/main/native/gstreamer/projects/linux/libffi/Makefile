#
# Linux libffi Makefile
#

BUILD_DIR = $(OUTPUT_DIR)/$(BUILD_TYPE)
TARGET_DIRS = $(BUILD_DIR)

SRCBASE_DIR = ../../../3rd_party/libffi
OBJBASE_DIR = $(BUILD_DIR)/obj/libffi

DIRLIST = src src/x86

TARGET_NAME = lib$(BASE_NAME).a
TARGET = $(BUILD_DIR)/$(TARGET_NAME)

CFLAGS += -c \
         -Werror=implicit-function-declaration \
         -fPIC \
         -DHAVE_AS_X86_PCREL \
         -DGSTREAMER_LITE

ifeq ($(BUILD_TYPE), Release)
    CFLAGS += -Os
else
    CFLAGS += -O0 -g -Wall
endif

INCLUDES = -I$(SRCBASE_DIR)/include

C_SOURCES = src/closures.c \
	    src/java_raw_api.c \
	    src/prep_cif.c \
	    src/raw_api.c \
	    src/types.c \
            src/x86/ffi64.c \

ASM_SOURCES = src/x86/unix64.S

ifeq ($(ARCH), x32)
    CFLAGS += -m32
    LDFLAGS += -m32
    INCLUDES += -I$(SRCBASE_DIR)/include/x86
else
    INCLUDES += -I$(SRCBASE_DIR)/include/x64
endif

OBJ_DIRS = $(addprefix $(OBJBASE_DIR)/,$(DIRLIST))
OBJECTS  = $(patsubst %.c,$(OBJBASE_DIR)/%.o,$(C_SOURCES))\
	   $(patsubst %.S,$(OBJBASE_DIR)/%.o,$(ASM_SOURCES))

.PHONY: default list

default: $(TARGET)

$(OBJBASE_DIR)/%.o: $(SRCBASE_DIR)/%.c
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

$(OBJBASE_DIR)/%.o: $(SRCBASE_DIR)/%.S
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

$(OBJECTS): | $(OBJ_DIRS) $(TARGET_DIRS)

$(OBJ_DIRS):
	mkdir -p $(OBJ_DIRS)

$(TARGET_DIRS):
	mkdir -p $(TARGET_DIRS)

$(TARGET): $(OBJECTS)
	ar rcs $@ $(OBJECTS)
