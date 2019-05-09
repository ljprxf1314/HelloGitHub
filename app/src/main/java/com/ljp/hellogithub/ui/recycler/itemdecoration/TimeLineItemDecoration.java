package com.ljp.hellogithub.ui.recycler.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ljp.hellogithub.R;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/5/9
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TimeLineItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint,paintCircle;
    private float offsetLeft;//左边边距
    private float nodeRadius;//原点半径
    private float offsetTop;
    private Context context;

    public TimeLineItemDecoration(Context context) {
        this.context = context;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);//抗锯齿

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(Color.BLUE);
        paintCircle.setStyle(Paint.Style.STROKE);

        offsetLeft = context.getResources().getDimension(R.dimen.item_margin_left);
        nodeRadius = context.getResources().getDimension(R.dimen.item_node_radius);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildCount()!=0){
            outRect.top = 1;
            offsetTop = 1;
        }
        outRect.left = (int) offsetLeft;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getChildCount()!=0){
            for (int i=0;i<parent.getChildCount();i++){
                View view = parent.getChildAt(i);
                int index = parent.getChildAdapterPosition(view);
                float dividerTop = view.getTop() - offsetTop;
                if (index==0){
                    dividerTop = view.getTop();
                }
                float dividerLeft = parent.getPaddingLeft();
                float dividerBottom = view.getBottom();
                float dividerRight = parent.getWidth() - parent.getPaddingRight();

                float centerX = dividerLeft + offsetLeft / 2;
                float centerY = dividerTop + (dividerBottom-dividerTop) / 2;

                float upLineTopX = centerX;
                float upLineTopY = dividerTop;
                float upLineBottomX = centerX;
                float upLineBottomY = centerY - nodeRadius;
                //绘制上半部分线
                c.drawLine(upLineTopX,upLineTopY,upLineBottomX,upLineBottomY,mPaint);
                //绘制圆形
                c.drawCircle(centerX,centerY,nodeRadius,paintCircle);

                float downLineTopX = centerX;
                float downLineTopY = centerY + nodeRadius;
                float downLineBottomX = centerX;
                float downLineBottomY = dividerBottom;
                //绘制上半部分线
                c.drawLine(downLineTopX,downLineTopY,downLineBottomX,downLineBottomY,mPaint);

            }
        }
    }
}
