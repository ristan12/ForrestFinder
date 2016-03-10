package com.example.aleksandar.forrestfinder;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by Aleksandar on 19/02/2016.
 */

public class Dugme extends ImageButton {
    public Dugme(Context context) {
        super(context);
    }

    public Dugme(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Dugme(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Dugme(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getIndeksVektora() {
        return indeksVektora;
    }

    public void setIndeksVektora(int indeksVektora) {
        this.indeksVektora = indeksVektora;
    }

    private int indeksVektora;
}
