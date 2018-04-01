package com.example.carlosyang.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by CarlosYang on 2017/1/12.
 */

public class CustomView extends View {
    private float r=300;//px
    private float[] left_circle=new float[2];
    private long refresh_internal=1000/60l;//ms
    private float rotate_time=0;//ms
    private float rotate_speed=100;//度/秒
    private float trans_time=0;//ms
    private float trans_speed=200;//px/s
    private Boolean canRotate=true;
    private Boolean canTrans=false;
    private Boolean drawLast=false;
    private Paint text_paint;
    private Paint dashPaint;
    private Paint linePaint;
    private Paint lineDashPaint;
    private Path left_circle_path;
    private Path line_path;

    public CustomView(Context context) {
        super(context);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        left_circle[0]=(width-r)/2f;

        left_circle[1]=(height+0.866f*r)/2f;

        left_circle_path=new Path();

        left_circle_path.addCircle(left_circle[0],left_circle[1],r, Path.Direction.CW);


        line_path=new Path();
        line_path.moveTo(left_circle[0],left_circle[1]);
        line_path.lineTo(left_circle[0]+r,left_circle[1]);

        text_paint=new Paint();
        text_paint.setColor(Color.BLACK);
        text_paint.setStyle(Paint.Style.FILL);
        text_paint.setTextSize(40);


        DashPathEffect dash=new DashPathEffect(new float[]{20,10},1);
        dashPaint=new Paint();
        dashPaint.setColor(Color.BLACK);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setStrokeWidth(5);
        dashPaint.setAntiAlias(true);
        dashPaint.setPathEffect(dash);

        linePaint=new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);


        lineDashPaint=new Paint();
        lineDashPaint.setStyle(Paint.Style.STROKE);
        lineDashPaint.setColor(Color.RED);
        lineDashPaint.setStrokeWidth(5);
        lineDashPaint.setAntiAlias(true);
        lineDashPaint.setPathEffect(dash);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(left_circle_path,dashPaint);
        canvas.save();
        canvas.translate(-30,30);
        canvas.drawText("A",left_circle[0],left_circle[1],text_paint);
        canvas.restore();
        canvas.save();
        canvas.translate(r,0);
        canvas.drawPath(left_circle_path,dashPaint);
        canvas.translate(20,20);
        canvas.drawText("B",left_circle[0],left_circle[1],text_paint);
        canvas.restore();
        canvas.save();
        canvas.translate(0.5f*r,-0.866f*r);
        canvas.drawPath(left_circle_path,dashPaint);
        canvas.translate(30,-30);
        canvas.drawText("C",left_circle[0],left_circle[1],text_paint);
        canvas.restore();

        if (canRotate) {
            linePaint.setColor(Color.BLACK);
            if (rotate_speed/1000f*rotate_time>=60) {
                linePaint.setColor(Color.BLUE);
            }
            canvas.save();
            canvas.rotate(-rotate_speed/1000f*rotate_time,left_circle[0],left_circle[1]);
            canvas.drawLine(left_circle[0],left_circle[1],left_circle[0]+r,left_circle[1],linePaint);
            rotate_time+=refresh_internal;
            canvas.restore();
            if (rotate_speed/1000f*rotate_time>=120) {
                canRotate=false;
                canTrans=true;
            }
        }

        if (canTrans) {
            canvas.save();
            canvas.translate(trans_time*trans_speed/1000f,0);
            canvas.rotate(-120,left_circle[0],left_circle[1]);
            canvas.drawPath(line_path,lineDashPaint);
            trans_time+=refresh_internal;
            canvas.restore();
            if (trans_time*trans_speed/1000f>=r) {
                canTrans=false;
                drawLast=true;
            }
        }

        if (rotate_speed/1000f*rotate_time>=60){
            linePaint.setColor(Color.BLACK);
            canvas.drawLine(left_circle[0],left_circle[1],left_circle[0]+r,left_circle[1],linePaint);
        }
        if (rotate_speed/1000f*rotate_time>=120) {
            linePaint.setColor(Color.BLUE);
            canvas.save();
            canvas.rotate(-60,left_circle[0],left_circle[1]);
            canvas.drawLine(left_circle[0],left_circle[1],left_circle[0]+r,left_circle[1],linePaint);
            canvas.restore();
        }

        if (drawLast) {
            linePaint.setColor(Color.RED);
            canvas.save();
            canvas.rotate(60,left_circle[0]+r,left_circle[1]);
            canvas.drawLine(left_circle[0],left_circle[1],left_circle[0]+r,left_circle[1],linePaint);
            canvas.restore();
        }

        postInvalidateDelayed(refresh_internal);

    }

    public  void resetView(){
        canRotate=true;
        canTrans=false;
        drawLast=false;
        trans_time=0;//ms
        rotate_time=0;//ms
    }
}
