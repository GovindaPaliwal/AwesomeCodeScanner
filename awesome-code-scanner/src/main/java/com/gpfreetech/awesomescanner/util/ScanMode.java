package com.gpfreetech.awesomescanner.util;

import com.gpfreetech.awesomescanner.ui.GpCodeScanner;

/**
 * Scan mode
 *
 * @see GpCodeScanner#setScanMode(ScanMode)
 */
public enum ScanMode {
    /**
     * Preview will stop after first decoded code
     */
    SINGLE,

    /**
     * Continuous scan, don't stop preview after decoding the code
     */
    CONTINUOUS,

    /**
     * Preview only, no code recognition
     */
    PREVIEW
}
