package com.ljp.hellogithub.activity.viewclicksystem;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.view.HorizontalScrollViewExDemo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/5
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class Demo2Activity extends BaseActivity {

    @BindView(R.id.myview)
    HorizontalScrollViewExDemo mMyview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_click_demo2);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        //获取屏幕宽高
        final int screenWidth = getScreenMetrics(this).widthPixels;
//        final int screenHeight = getScreenMetrics(this).heightPixels;

        for (int i=0;i<3;i++){
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout,mMyview,false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mMyview.addView(layout);
        }

    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Demo2Activity.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
