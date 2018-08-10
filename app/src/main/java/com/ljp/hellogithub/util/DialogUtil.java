package com.ljp.hellogithub.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljp.hellogithub.R;


/**
 * 自定义仿iOS弹出框效果
 * Created by lijipei on 2016/7/15.
 */
public class DialogUtil {

    public interface MyCustomDialogListener {

        void ok(Dialog dialog, String str);

        void no();

    }

    public interface MyCustomDialogListener2 {

        void ok();

        void no();

    }

    public interface MyCustomDialogListener3 {
        void item(int i);
    }

    public interface MyCustomDialogListener4 {

        void ok();

        void center();

        void no();

    }

    /**
     * 显示键盘
     *
     * @param edit
     */
    public static void showKeyboard(EditText edit) {
        if (edit != null) {
            //设置可获得焦点
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            //请求获得焦点
            edit.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) edit
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edit, 0);
        }
    }


    public static void showCustomDialog(Context context, final String message, String ok, String no,
                                        final MyCustomDialogListener2 o) {
        showCustomDialog(context, null, message, ok, no, o);
    }

    /**
     * 显示带标题的提示框
     *
     * @param context
     * @param message
     * @param ok
     * @param no
     * @param o
     */
    public static void showCustomDialog(Context context, String titleStr, final String message, String ok, String no,
                                        final MyCustomDialogListener2 o) {
        final Dialog dialog = new Dialog(context, R.style.Translucent_NoTitle);
        final View view = LayoutInflater.from(context).inflate(
                R.layout.mycustom_dialog_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView title = (TextView) view.findViewById(R.id.dialog_message);
        TextView ok_tx = (TextView) view.findViewById(R.id.dialog_ok);
        TextView no_tx = (TextView) view.findViewById(R.id.dialog_no);
        LinearLayout linlin = (LinearLayout) view.findViewById(R.id.linlin);
        View line = view.findViewById(R.id.custom_line);
        View line2 = view.findViewById(R.id.dialog_line2);
        ok_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (o != null) {
                    o.ok();
                }
            }
        });
        no_tx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (o != null) {
                    o.no();
                }
            }
        });
        if (ok == null) {
            ok_tx.setVisibility(View.GONE);
            line2.setVisibility(View.INVISIBLE);
        }
        if (no == null) {
            no_tx.setVisibility(View.GONE);
            line2.setVisibility(View.INVISIBLE);
        }
        if (ok == null && no == null) {
            linlin.setVisibility(View.GONE);
            line.setVisibility(View.INVISIBLE);
        }
        title.setText(message);

        if (titleStr == null) {
            //如果是null,隐藏标题栏
            dialog_title.setVisibility(View.GONE);
        } else if (titleStr.equals("")) {
            //空串默认是--提示
        } else {
            dialog_title.setText(titleStr);
        }
        //        if (dialog_title != null && !dialog_title.getText().equals("")){
        //            dialog_title.setText(titleStr);
        //        }
        if (ok != null) {
            ok_tx.setText(ok);
        }
        if (no != null) {
            no_tx.setText(no);
        }
        // progressDialog.setMessage(msg);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().addContentView(
                view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();
        showAnimationCustom(view);
    }

    private static void showAnimationCustom(final View view) {
        @SuppressLint("ObjectAnimatorBinding")
        ObjectAnimator oo = ObjectAnimator.ofFloat(view, "uuu", 0.5f, 1f);
        oo.setDuration(200);
        oo.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                view.setScaleX(value);
                view.setScaleY(value);
                view.setAlpha(value);
            }
        });
        oo.setInterpolator(new DecelerateInterpolator());
        oo.start();
    }

}
