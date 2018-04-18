/**
 * 3D Tic Tac Toe for Android
 * Copyright (C) 2018  Michel Kremer (kremi151)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package lu.kremi151.a3dtictactoe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.OnBoardTapListener;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class GameBoard extends SurfaceView implements Runnable {

    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Paint fieldPaint, fieldValuePaint;
    private boolean mRunning;
    private Thread mGameThread;

    private boolean portrait;
    private int mViewWidth, mViewHeight;
    private float layerSize, layerSpacing, fieldSize;
    private OnBoardTapListener listener;

    private GameCube cube = new GameCube();

    public GameBoard(Context context) {
        super(context);
        init(context);
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public GameBoard setCube(GameCube cube){
        this.cube = cube;
        return this;
    }

    private void init(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        fieldPaint = new Paint();
        fieldPaint.setColor(Color.DKGRAY);
        fieldPaint.setStrokeWidth(1);
        fieldPaint.setStyle(Paint.Style.STROKE);
        fieldValuePaint = new Paint(fieldPaint);
        fieldValuePaint.setStrokeWidth(4);
    }

    public void pause() {
        mRunning = false;
        try {
            // Stop the thread (rejoin the main thread)
            mGameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        if(!mRunning){
            mRunning = true;
            mGameThread = new Thread(this);
            mGameThread.start();
        }
    }

    public void setListener(OnBoardTapListener listener){
        this.listener = listener;
    }

    @Override
    public void run() {
        Canvas canvas;
        while(mRunning){
            if (mSurfaceHolder.getSurface().isValid()) {
                canvas = mSurfaceHolder.lockCanvas();
                canvas.save();
                canvas.drawColor(Color.WHITE);

                for(int z = 0 ; z < this.cube.depth() ; z++){
                    for(int x = 0 ; x < this.cube.width() ; x++){
                        for(int y = 0 ; y < this.cube.height() ; y++){
                            float left, top;
                            if(portrait){
                                left = (z * layerSpacing) + (x * fieldSize);
                                top = (z * layerSize) + (y * fieldSize);
                            }else{
                                left = (z * layerSize) + (x * fieldSize);
                                top = (z * layerSpacing) + (y * fieldSize);
                            }
                            canvas.drawRect(
                                    left,
                                    top,
                                    left + fieldSize,
                                    top + fieldSize,
                                    fieldPaint);
                            switch (cube.valueAt(x, y, z)){
                                case CIRCLE:
                                    canvas.drawCircle(left + (fieldSize / 2), top + (fieldSize / 2), fieldSize * 0.4f, fieldValuePaint);
                                    break;
                                case CROSS:
                                    drawCross(canvas, left + (fieldSize * 0.1f), top + (fieldSize * 0.1f), left + (fieldSize * 0.9f), top + (fieldSize * 0.9f), fieldValuePaint);
                                    break;
                            }
                        }
                    }
                }

                canvas.restore();
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawCross(Canvas canvas, float left, float top, float right, float bottom, Paint paint){
        canvas.drawLine(left, top, right, bottom, paint);
        canvas.drawLine(left, bottom, right, top, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.portrait = h >= w;
        this.mViewWidth = w;
        this.mViewHeight = h;
        if(this.portrait){
            this.layerSize = h / 4;
            this.layerSpacing = (w - layerSize) / 3;
        }else{
            this.layerSize = w / 4;
            this.layerSpacing = (h - layerSize) / 3;
        }
        this.fieldSize = this.layerSize / 4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int bX, bY, bZ;
            if(portrait){
                bZ = (int) Math.floor(event.getY() / layerSize);
            }else{
                bZ = (int) Math.floor(event.getX() / layerSize);
            }
            if((portrait && event.getX() >= bZ * layerSpacing && event.getX() < (bZ * layerSpacing) + layerSize)
                    || (!portrait && event.getY() >= bZ * layerSpacing && event.getY() < (bZ * layerSpacing) + layerSize)){
                if(portrait){
                    bY = (int) Math.floor((event.getY() % layerSize) / fieldSize);
                    bX = (int) Math.floor(((event.getX() - (bZ * layerSpacing)) % layerSize) / fieldSize);
                }else{
                    bY = (int) Math.floor(((event.getY() - (bZ * layerSpacing)) % layerSize) / fieldSize);
                    bX = (int) Math.floor((event.getX() % layerSize) / fieldSize);
                }
                if(listener != null && listener.onTap(bX, bY, bZ)){
                    invalidate();
                }
            }
        }
        return true;
    }

}
