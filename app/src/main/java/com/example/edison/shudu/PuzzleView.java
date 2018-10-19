package com.example.edison.shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

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
            Paint hint = new Paint();
            int c[] = {getResources().getColor(R.color.puzzle_hint_0),
                getResources().getColor(R.color.puzzle_hint_1),
                getResources().getColor(R.color.puzzle_hint_2)};
            Rect r = new Rect();
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++) {
                    int mouseleft = 9 - game.getUsedTiles(i, j).length;
                    if(mouseleft < c.length){
                        getRect(i,j,r);
                        hint.setColor(c[mouseleft]);
                        canvas.drawRect(r,hint);
                    }
                }
            }
        }
        //绘制选定区
        Log.e(TAG,"selRect" + selRect);
        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));
        canvas.drawRect(selRect,selected);
        super.onDraw(canvas);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.e(TAG,"onKeyDown:keycode="+keyCode+"event="+event);
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                select(selX,selY-1);
            case KeyEvent.KEYCODE_DPAD_DOWN:
                select(selX,selY+1);
            case KeyEvent.KEYCODE_DPAD_LEFT:
                select(selX-1,selY);
            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
                select(selX+1,selY);
                break;;

            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                setSelectedTile(0);
                break;
            case KeyEvent.KEYCODE_1:
                setSelectedTile(1);
                break;
            case KeyEvent.KEYCODE_2:
                setSelectedTile(2);
                break;
            case KeyEvent.KEYCODE_3:
                setSelectedTile(3);
                break;
            case KeyEvent.KEYCODE_4:
                setSelectedTile(4);
                break;
            case KeyEvent.KEYCODE_5:
                setSelectedTile(5);
                break;
            case KeyEvent.KEYCODE_6:
                setSelectedTile(6);
                break;
            case KeyEvent.KEYCODE_7:
                setSelectedTile(7);
                break;
            case KeyEvent.KEYCODE_8:
                setSelectedTile(8);
                break;
            case KeyEvent.KEYCODE_9:
                setSelectedTile(9);
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                game.showKeyPadOrError(selX,selY);
                break;
            default:
                return super.onKeyDown(keyCode,event);
        }
        return true;
    }

    public void setSelectedTile(int tile){
        if(game.setTilefVlid(selX,selY,tile)){
            invalidate();
        }else {
            Log.e(TAG,"setSelectedTile:invalid" + tile);
            startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()!= MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }
        select((int)(event.getX()/width),(int)(event.getY()/height));
        game.showKeyPadOrErro(selX,selY);
        Log.e(TAG,"onTouchEvent:x"+selX+",y" +selY);
        return true;
    }

    private void select(int x, int y){
        invalidate(selRect);
        selX = Math.min(Math.max(x,0),8);
        selY = Math.min(Math.max(y,0),8);
        getRect(selX,selY,selRect);
        invalidate(selRect);
    }

    private void getRect(int x, int y, Rect rect){
        rect.set((int)(x*width),(int)(y*height),(int)(x*width + width), (int)(y*height + height));
    }
}
