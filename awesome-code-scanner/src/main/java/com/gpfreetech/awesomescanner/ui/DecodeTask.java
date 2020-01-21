package com.gpfreetech.awesomescanner.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.gpfreetech.awesomescanner.util.Point;
import com.gpfreetech.awesomescanner.util.Rect;
import com.gpfreetech.awesomescanner.util.Utils;

public final class DecodeTask {

    private final byte[] mImage;
    private final Point mImageSize;
    private final Point mPreviewSize;
    private final Point mViewSize;
    private final Rect mViewFrameRect;
    private final int mOrientation;
    private final boolean mReverseHorizontal;

    public DecodeTask(@NonNull final byte[] image, @NonNull final Point imageSize,
                      @NonNull final Point previewSize, @NonNull final Point viewSize,
                      @NonNull final Rect viewFrameRect, final int orientation,
                      final boolean reverseHorizontal) {
        mImage = image;
        mImageSize = imageSize;
        mPreviewSize = previewSize;
        mViewSize = viewSize;
        mViewFrameRect = viewFrameRect;
        mOrientation = orientation;
        mReverseHorizontal = reverseHorizontal;
    }

    @Nullable
    @SuppressWarnings("SuspiciousNameCombination")
    public Result decode(@NonNull final MultiFormatReader reader) throws ReaderException {
        int imageWidth = mImageSize.getX();
        int imageHeight = mImageSize.getY();
        final int orientation = mOrientation;
        final byte[] image = Utils.rotateYuv(mImage, imageWidth, imageHeight, orientation);
        if (orientation == 90 || orientation == 270) {
            final int width = imageWidth;
            imageWidth = imageHeight;
            imageHeight = width;
        }
        final Rect frameRect =
                Utils.getImageFrameRect(imageWidth, imageHeight, mViewFrameRect, mPreviewSize,
                        mViewSize);
        final int frameWidth = frameRect.getWidth();
        final int frameHeight = frameRect.getHeight();
        if (frameWidth < 1 || frameHeight < 1) {
            return null;
        }
        return Utils.decodeLuminanceSource(reader,
                new PlanarYUVLuminanceSource(image, imageWidth, imageHeight, frameRect.getLeft(),
                        frameRect.getTop(), frameWidth, frameHeight, mReverseHorizontal));
    }
}
