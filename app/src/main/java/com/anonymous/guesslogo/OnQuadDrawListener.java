package com.anonymous.guesslogo;

import android.graphics.Rect;


interface OnQuadDrawListener {
    /**
     * @param rect
     * @param averageColor average color inside rect.
     */
    void onQuadDraw(Rect rect, int averageColor);
}
