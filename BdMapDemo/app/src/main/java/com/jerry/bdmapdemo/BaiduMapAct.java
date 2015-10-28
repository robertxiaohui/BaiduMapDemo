package com.jerry.bdmapdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class BaiduMapAct extends Activity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private String TAG = "BaiduMapAct";
    private int defaultLevel = 12;
    private int isChangeLevel = 1;
    private float nowZoom;
    private Marker nowMarker;

    private List<CoordBean> mListA;
    private List<CoordBean> mListB;
    private List<CoordBean> mListC;
    private List<CoordBean> mListD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.act_baidu_map);

        initData();
        initBaiduMap();
    }

    private void initData() {

        CoordBean D01 = new CoordBean(15, 20, 113.870425, 22.575816, 4, "宝源中心");
        CoordBean D02 = new CoordBean(15, 20, 113.874701, 22.576984, 5, "金谷大楼");
        CoordBean D03 = new CoordBean(15, 20, 113.876929, 22.577217, 20, "麻布新村");
        CoordBean D04 = new CoordBean(15, 20, 113.878402, 22.573346, 40, "雅涛花园");
        CoordBean D05 = new CoordBean(15, 20, 113.880271, 22.575983, 23, "半岛名苑");
        CoordBean D06 = new CoordBean(15, 20, 113.865934,22.58556, 23, "汇潮大厦");

        CoordBean C01 = new CoordBean(13, 15, 113.887718, 22.56663, 50, "宝体");
        CoordBean C02 = new CoordBean(13, 15, 113.877189, 22.575273, 54, "坪洲");
        CoordBean C03 = new CoordBean(13, 15, 113.910966, 22.575474, 20, "灵芝");
        CoordBean C04 = new CoordBean(13, 15, 113.891383, 22.556083, 40, "宝华");
        CoordBean C05 = new CoordBean(13, 15, 113.893718, 22.560956, 23, "宝安中心");

        CoordBean B01 = new CoordBean(11, 13, 114.12, 22.55, 50, "罗湖区");
        CoordBean B02 = new CoordBean(11, 13, 114.05, 22.53, 50, "福田区");
        CoordBean B03 = new CoordBean(11, 13, 113.92, 22.52, 50, "南山区");
        CoordBean B04 = new CoordBean(11, 13, 113.90, 22.57, 50, "宝安区");
        CoordBean B05 = new CoordBean(11, 13, 114.27, 22.73, 50, "龙岗区");
        CoordBean B06 = new CoordBean(11, 13, 114.22, 22.55, 50, "盐田区");

        CoordBean A01 = new CoordBean(0, 11, 114.060363, 22.562667, 50, "深圳市");

        mListA = new ArrayList<CoordBean>();
        mListA.add(A01);

        mListB = new ArrayList<CoordBean>();
        mListB.add(B01);
        mListB.add(B02);
        mListB.add(B03);
        mListB.add(B04);
        mListB.add(B05);
        mListB.add(B06);

        mListC = new ArrayList<CoordBean>();
        mListC.add(C01);
        mListC.add(C02);
        mListC.add(C03);
        mListC.add(C04);
        mListC.add(C05);

        mListD = new ArrayList<CoordBean>();
        mListD.add(D01);
        mListD.add(D02);
        mListD.add(D03);
        mListD.add(D04);
        mListD.add(D05);
        mListD.add(D06);

        addBuilderMark();

    }

    public void initBaiduMap() {
//        LatLng point1 = new LatLng(22.583224, 113.860544);
//        LatLng point2 = new LatLng(22.573413, 113.891302);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
       /* //定义Maker坐标点
        LatLng point = new LatLng(22.556459, 113.891445);
        // LatLng point1 = new LatLng(22.560998, 113.893673);

        //定义地图初始状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(defaultLevel)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);*/

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(lngBoundsB));
//                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).target(nowMarker.getPosition()).build()));
        selecteLevel(defaultLevel);


        /*//构建Marker图标
        //BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        View view = getLayoutInflater().inflate(R.layout.point, null);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        OverlayOptions option1 = new MarkerOptions().position(point1).icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        mBaiduMap.addOverlay(option1);*/

        //selecteLevel(defaultLevel);

      /*  //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        TextView textView = new TextView(getApplicationContext());
//        textView.setText("aaaaa");
        InfoWindow mInfoWindow = new InfoWindow(textView, point, -47);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);*/

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                float zoom = mapStatus.zoom;
                Log.d(TAG, "倍数: " + zoom);
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                float zoom = mapStatus.zoom;
                // lngBounds = new LatLngBounds.Builder().build();
                // defaultLevel = (int)zoom;
                selecteLevel(zoom);
            }
        });

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                 Toast.makeText(BaiduMapAct.this, "点击了", Toast.LENGTH_SHORT).show();
                float zoom = mBaiduMap.getMapStatus().zoom;
                nowZoom = zoom;
                nowMarker = marker;
                int level = getLevel(zoom);
                switch (level) {
                    case 1:
                        /*  addBuilderMark(zoom);
                          MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(lngBounds);
                          mBaiduMap.setMapStatus(u);*/
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(lngBoundsB));
//                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(12).target(nowMarker.getPosition()).build()));
                        selecteLevel(11);
                        break;

                    case 2:
                       /* addBuilderMark(zoom);
                        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLngBounds(lngBounds);
                        mBaiduMap.setMapStatus(u1);*/
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(lngBoundsC));
//                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).target(nowMarker.getPosition()).build()));
                        selecteLevel(13);
                        break;

                    case 3:
                       /* addBuilderMark(zoom);
                        MapStatusUpdate u2 = MapStatusUpdateFactory.newLatLngBounds(lngBounds);
                        mBaiduMap.setMapStatus(u2);*/
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(lngBoundsD));
//                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).target(marker.getPosition()).build()));
                        selecteLevel(16);
                        break;
                    case 4:
                        Toast.makeText(BaiduMapAct.this, "显示房源信息", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    public void selecteLevel(float showLevel) {
        int level = getLevel(showLevel);
        switch (level) {
            case 1:
                if (level != isChangeLevel) {
                    mBaiduMap.clear();
                    isChangeLevel = level;
                }
                setMark(mListA);
                break;

            case 2:
                if (level != isChangeLevel) {
                    mBaiduMap.clear();
                    isChangeLevel = level;
                }
                setMark(mListB);
                break;

            case 3:
                if (level != isChangeLevel) {
                    mBaiduMap.clear();
                    isChangeLevel = level;
                }
                setMark(mListC);
                break;

            case 4:
                if (level != isChangeLevel) {
                    mBaiduMap.clear();
                    isChangeLevel = level;
                }
                setMark(mListD);
                break;
        }
    }

    private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }


    public void setMark(List<CoordBean> mMarkList) {
        if (mMarkList == null) {
            return;
        }
        for (int i = 0; i < mMarkList.size(); i++) {
            CoordBean coordBean = mMarkList.get(i);
            //构建Marker图标
            //BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
            LayoutInflater factorys = LayoutInflater.from(BaiduMapAct.this);
            View view = factorys.inflate(R.layout.point, null);
            TextView mNumber = (TextView) view.findViewById(R.id.tv_number);
            TextView mAddress = (TextView) view.findViewById(R.id.tv_address);
            if (coordBean.showMinLevel >= 15) {
                mAddress.setVisibility(View.GONE);
            } else {
                mAddress.setVisibility(View.VISIBLE);
            }

            mNumber.setText("" + coordBean.getNumber());
            mAddress.setText(coordBean.getAddress());

            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));

            //定义Maker坐标点
            LatLng point = new LatLng(coordBean.getY(), coordBean.getX());
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }

    }


    LatLngBounds lngBoundsB;
    LatLngBounds lngBoundsC;
    LatLngBounds lngBoundsD;

    public void addBuilderMark() {

        LatLngBounds.Builder builder;
        builder = new LatLngBounds.Builder();
        for (int i = 0; i < mListB.size(); i++) {
            CoordBean coordBean = mListB.get(i);
            //定义Maker坐标点
            LatLng point = new LatLng(coordBean.getY(), coordBean.getX());
            builder.include(point);
        }
        lngBoundsB = builder.build();

        LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
        for (int i = 0; i < mListC.size(); i++) {
            CoordBean coordBean = mListC.get(i);
            //定义Maker坐标点
            LatLng point = new LatLng(coordBean.getY(), coordBean.getX());
            builder1.include(point);
        }
        lngBoundsC = builder1.build();

        LatLngBounds.Builder builder2 = new LatLngBounds.Builder();
        for (int i = 0; i < mListD.size(); i++) {
            CoordBean coordBean = mListD.get(i);
            //定义Maker坐标点
            LatLng point = new LatLng(coordBean.getY(), coordBean.getX());
            builder2.include(point);
        }
        lngBoundsD = builder2.build();
    }

    public int getLevel(float level) {
        if (0 <= level && level < 11) {
            return 1;
        } else if (11 <= level && level < 13) {
            return 2;
        } else if (13 <= level && level < 16) {
            return 3;
        } else if (16 <= level) {
            return 4;
        } else {
            return 1;
        }
    }

}
