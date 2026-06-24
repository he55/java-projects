package com.example.commonservice.util;

import org.jspecify.annotations.Nullable;

public final class Preconditions {

    private Preconditions() {
    }

    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static <T> T checkNotNull(@Nullable T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

}
