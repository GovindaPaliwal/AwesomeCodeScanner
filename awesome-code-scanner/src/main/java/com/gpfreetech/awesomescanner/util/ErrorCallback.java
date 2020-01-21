package com.gpfreetech.awesomescanner.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

/**
 * Error callback
 */
public interface ErrorCallback {
    /**
     * Callback to suppress errors
     */
    ErrorCallback SUPPRESS = new Utils.SuppressErrorCallback();

    /**
     * Called when error has occurred
     * <br>
     * Note that this method always called on a worker thread
     *
     * @param error Exception that has been thrown
     * @see Handler
     * @see Looper#getMainLooper()
     * @see Activity#runOnUiThread(Runnable)
     */
    @WorkerThread
    void onError(@NonNull Exception error);
}
