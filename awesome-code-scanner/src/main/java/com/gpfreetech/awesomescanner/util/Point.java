package com.gpfreetech.awesomescanner.util;

public final class Point {
    private final int mX;
    private final int mY;

    public Point(final int x, final int y) {
        mX = x;
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public int hashCode() {
        return mX ^ ((mY << (Integer.SIZE / 2)) | (mY >>> (Integer.SIZE / 2)));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Point) {
            final Point other = (Point) obj;
            return mX == other.mX && mY == other.mY;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + mX + "; " + mY + ")";
    }
}
