package com.example.edison.shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

public class PuzzleView extends View {
    private static final String TAG = "sudoku";
    private final GameActivity game ;
    private float width;
    private float height;
    private int selX;
    private int selY;
    private final Rect selRect = new Rect();

    //记录当前位置
    private static final String SELX = "selx";
    private static final String SELY = "sely";
    private static final String VIEW_STATE = "viewState";
    private static final int ID = 42;

    public PuzzleView(Context context) {
        super(context);
        this.game = (GameActivity)context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setId(ID);
    }

    /****************用于记录当前的位置********************/
    @Override
    protected void onRestoreInstanceState(Parcelable state){
        Log.e(TAG, "onRestoreInstanceState");
        Bundle bundle = (Bundle)state;
        select(bundle.getInt(SELX),bundle.getInt(SELY));
        super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
        return;
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        Parcelable p = super.onSaveInstanceState();
        Log.e(TAG, "onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putInt(SELX, selX);
        bundle.putInt(SELY, selY);
        bundle.putParcelable(VIEW_STATE, p);
        return bundle;
    }

    /***************************************************/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = w/9f;
        height = h/9f;
        getRect(selX, selY,selRect);
        Log.e(TAG,"onSizeChanged: width" +width+ " height" + height);
        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_backgrund));
        canvas.drawRect(0,0,getWidth(),getWidth(),background);

        //控制网格线
        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark));
        Paint hilite = new Paint();
        hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));

        //绘制主要网格线
        for(int i = 0 ; i < 9; i++){
            canvas.drawLine(0,i*height,getWidth(),i*height,light);
            canvas.drawLine(0,i*height + 1,getWidth(),i*height + 1,hilite);
            canvas.drawLine(i*width,0,i*width,getHeight(),light);
            canvas.drawLine(i*width + 1,0,i*width+1,getHeight(),hilite);
        }

        //绘制次要网格线
        for(int i = 0 ; i < 9; i++){
            if (i%3!=0){
                continue;
            }else {

                canvas.drawLine(0, i * height, getWidth(), i * height, dark);
                canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
                canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
                canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
            }
        }

        //输出数字
        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height*0.75f);
        foreground.setTextScaleX(width/height);
        foreground.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = foreground.getFontMetrics();
        float x = width/2;
        float y = height/2 - (fm.ascent + fm.descent)/2;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9 ;j++){
                canvas.drawText(this.game.getTitleString(i,j),i*width + x, j*height + y, foreground);

            }
        }
        //绘制hints
        if(SettingActivity.getHints(getContext())){             //判断是否显示高亮提示
            Paint hints = new Paint();
            int c[] = {getResources().getColor(R.color.puzzle_hint_0),
                getResources().getColor(R.color.puzzle_hint_1),
                getResources().getColor(R.color.puzzle_hint_2)};

        }
    }

    private void select(int x, int y){

    }

    private void getRect(int x, int y, Rect rect){
        rect.set((int)(x*width),(int)(y*height),(int)(x*width + width), (int)(y*height + height));
    }
}
