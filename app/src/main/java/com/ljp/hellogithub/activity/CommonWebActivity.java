package com.ljp.hellogithub.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ljp.hellogithub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/2/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CommonWebActivity extends AppCompatActivity {

    @BindView(R.id.web)
    WebView mWeb;

    private String urlStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);
        ButterKnife.bind(this);

        urlStr = getIntent().getStringExtra("urlStr");
        mWeb.loadUrl(urlStr);
    }
}
