package com.gpfreetech.awesomescanner.util;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.gpfreetech.awesomescanner.ui.DecodeTask;

public final class Decoder {
    private final MultiFormatReader mReader;
    private final DecoderThread mDecoderThread;
    private final StateListener mStateListener;
    private final Map<DecodeHintType, Object> mHints;
    private final Object mTaskLock = new Object();
    private volatile DecodeCallback mCallback;
    private volatile DecodeTask mTask;
    private volatile State mState;

    public Decoder(@NonNull final StateListener stateListener,
            @NonNull final List<BarcodeFormat> formats, @Nullable final DecodeCallback callback) {
        mReader = new MultiFormatReader();
        mDecoderThread = new DecoderThread();
        mHints = new EnumMap<>(DecodeHintType.class);
        mHints.put(DecodeHintType.POSSIBLE_FORMATS, formats);
        mReader.setHints(mHints);
        mCallback = callback;
        mStateListener = stateListener;
        mState = State.INITIALIZED;
    }

    public void setFormats(@NonNull final List<BarcodeFormat> formats) {
        mHints.put(DecodeHintType.POSSIBLE_FORMATS, formats);
        mReader.setHints(mHints);
    }

    public void setCallback(@Nullable final DecodeCallback callback) {
        mCallback = callback;
    }

    public void decode(@NonNull final DecodeTask task) {
        synchronized (mTaskLock) {
            if (mState != State.STOPPED) {
                mTask = task;
                mTaskLock.notify();
            }
        }
    }

    public void start() {
        if (mState != State.INITIALIZED) {
            throw new IllegalStateException("Illegal decoder state");
        }
        mDecoderThread.start();
    }

    public void shutdown() {
        mDecoderThread.interrupt();
        mTask = null;
    }

    @NonNull
    public State getState() {
        return mState;
    }

    private boolean setState(@NonNull final State state) {
        mState = state;
        return mStateListener.onStateChanged(state);
    }

    private final class DecoderThread extends Thread {
        public DecoderThread() {
            super("cs-decoder");
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            mainLoop:
            for (; ; ) {
                setState(Decoder.State.IDLE);
                Result result = null;
                try {
                    final DecodeTask task;
                    for (; ; ) {
                        synchronized (mTaskLock) {
                            final DecodeTask t = mTask;
                            if (t != null) {
                                mTask = null;
                                task = t;
                                break;
                            }
                            try {
                                mTaskLock.wait();
                            } catch (final InterruptedException e) {
                                setState(Decoder.State.STOPPED);
                                break mainLoop;
                            }
                        }
                    }
                    setState(Decoder.State.DECODING);
                    result = task.decode(mReader);
                } catch (final ReaderException ignored) {
                } finally {
                    if (result != null) {
                        mTask = null;
                        if (setState(Decoder.State.DECODED)) {
                            final DecodeCallback callback = mCallback;
                            if (callback != null) {
                                callback.onDecoded(result);
                            }
                        }
                    }
                }
            }
        }
    }

    public interface StateListener {
        boolean onStateChanged(@NonNull State state);
    }

    public enum State {
        INITIALIZED,
        IDLE,
        DECODING,
        DECODED,
        STOPPED
    }
}
