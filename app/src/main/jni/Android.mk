#LOCAL_PATH := $(call my-dir)

#include $(CLEAR_VARS)

# Enable PIE manually. Will get reset on $(CLEAR_VARS). This
# is what enabling PIE translates to behind the scenes.
#LOCAL_CFLAGS += -fPIE
#LOCAL_LDFLAGS += -fPIE -pie

#LOCAL_MODULE     := hello
#LOCAL_SRC_FILES  := hello.c

#include $(BUILD_EXECUTABLE)

include $(call all-subdir-makefiles)