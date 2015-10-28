package com.jerry.bdmapdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity implements View.OnClickListener {
    private EditText mETSign;
    private Button mBTGetSign;
    private Button mETGotoMap;
    private Button mBTPoiSearch;

    private PackageManager manager;
    private PackageInfo packageInfo;
    private Signature[] signatures;
    private StringBuilder builder;
    private String signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBTGetSign = (Button) findViewById(R.id.bt_get_sign);
        mETGotoMap = (Button) findViewById(R.id.bt_goto_map);
        mETSign = (EditText) findViewById(R.id.et_sign);
        mBTPoiSearch = (Button) findViewById(R.id.bt_poi_search);

        mBTPoiSearch.setOnClickListener(this);
        mETGotoMap.setOnClickListener(this);
        mBTGetSign.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.bt_get_sign:
              //  getSignature();
                intent = new Intent(this,ShowStaticMapAct.class);
                startActivity(intent);
                break;

            case R.id.bt_goto_map:
                intent = new Intent(this,BaiduMapAct.class);
                startActivity(intent);
                break;

            case R.id.bt_poi_search:
                intent = new Intent(this,PoiSearchDemo.class);
                startActivity(intent);
                break;
        }
    }


    public void getSignature() {
        String pkgname = "com.jerry.bdmapdemo";
        manager = getPackageManager();
        builder = new StringBuilder();
        boolean isEmpty = TextUtils.isEmpty(pkgname);
        if (isEmpty) {
            Toast.makeText(this, "应用程序的包名不能为空！", Toast.LENGTH_SHORT);
        } else {
            try {
                /** 通过包管理器获得指定包名包含签名的包信息 **/
                packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
                /******* 通过返回的包信息获得签名数组 *******/
                signatures = packageInfo.signatures;
                /******* 循环遍历签名数组拼接应用签名 *******/
                for (Signature signature : signatures) {
                    builder.append(signature.toCharsString());
                }
                /************** 得到应用签名 **************/
                signature = builder.toString();
                mETSign.setText(signature);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
