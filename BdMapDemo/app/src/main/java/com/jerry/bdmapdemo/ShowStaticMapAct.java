package com.jerry.bdmapdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by Administrator on 2015/10/27.
 */
public class ShowStaticMapAct extends Activity {

    public SimpleDraweeView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_static_map);

        avatar = (SimpleDraweeView) findViewById(R.id.address);
        //  http://api.map.baidu.com/images/marker_red.png
        String url1 = "http://api.map.baidu.com/staticimage?width=400&height=200&center=&markers=西乡&zoom=16&markerStyles=-1";
        String url2 = "http://api.map.baidu.com/staticimage?center=&markers=113.891481,22.556125&width=300&height=600&zoom=16&markerStyles=-1";
        Uri uri = Uri.parse(url2);
        avatar.setImageURI(uri);
    }
}
