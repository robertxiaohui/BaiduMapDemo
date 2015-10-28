package com.jerry.bdmapdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示poi搜索功能
 */
public class PoiSearchDemo extends Activity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener, View.OnClickListener {

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private BaiduMap mBaiduMap = null;
    private List<String> suggest;

    private TextView mTVMetro;
    private TextView mTVBus;
    private TextView mTVBank;
    private TextView mTVAddress;
    private TextView mTVToNextPage;

    private MapView mMapView = null;

    private int page = 0;
    private LatLng localLatLng = new LatLng(22.564269, 113.89026);
    ;
    private String loaclKey;
    /**
     * 搜索关键字输入窗口
     */
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_poi_search);

        initView();
        initPoiSearch();
    }


    private void initView() {
        mTVBank = (TextView) findViewById(R.id.tv_bank);
        mTVBus = (TextView) findViewById(R.id.tv_bus);
        mTVMetro = (TextView) findViewById(R.id.tv_metro);
        mTVAddress = (TextView) findViewById(R.id.tv_address);
        mTVToNextPage = (TextView) findViewById(R.id.tv_next_page);

        mTVToNextPage.setOnClickListener(this);
        mTVBank.setOnClickListener(this);
        mTVBus.setOnClickListener(this);
        mTVMetro.setOnClickListener(this);

    }

    private void initPoiSearch() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
       /* mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);*/

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //定义地图初始状态
        MapStatus mMapStatus = new MapStatus.Builder().target(localLatLng).zoom(17).build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Toast.makeText(PoiSearchDemo.this, "点击了: " +point.toString(), Toast.LENGTH_SHORT).show();
            }

            public boolean onMapPoiClick(MapPoi poi) {
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        //  mSuggestionSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 城市收索
     *
     * @param
     */
    public void searchInCity(String city, String searchKey) {
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city)
                .pageCapacity(20)
                .keyword(searchKey));
    }

    /**
     * 周边收索
     */
    public void searcahInNearby(LatLng lng, String searchKey) {
        PoiNearbySearchOption option = new PoiNearbySearchOption().location(lng).keyword(searchKey).radius(1000).pageCapacity(50);
        mPoiSearch.searchNearby(option);
    }

    public void toNextPage() {
        if (localLatLng != null && loaclKey != null) {
            mPoiSearch.searchNearby(new PoiNearbySearchOption().location(localLatLng).keyword(loaclKey).radius(500).pageCapacity(50));
        }
    }

    /**
     * 区域收索
     */
  /*  public void searcahBound() {
        if (localLatLng != null && loaclKey != null){
            mPoiSearch.searchNearby(new PoiNearbySearchOption().location(localLatLng).keyword(loaclKey).radius(5000).pageCapacity(100).pageNum(page++));
        }

    }
*/
    public void onGetPoiResult(PoiResult result) {
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(PoiSearchDemo.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            int size = result.getAllPoi().size();
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
          //  overlay.zoomToSpan();

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(PoiSearchDemo.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(PoiSearchDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            mTVAddress.setText(result.getName() + ": " + result.getAddress());
            // Toast.makeText(PoiSearchDemo.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        suggest = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
        sugAdapter = new ArrayAdapter<String>(PoiSearchDemo.this, android.R.layout.simple_dropdown_item_1line, suggest);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_metro:
                page = 0;
                loaclKey = "地铁";
                searcahInNearby(localLatLng, loaclKey);
//                searchInCity("深圳市", "地铁");
                break;

            case R.id.tv_bus:
                page = 0;
                loaclKey = "公交";
                searcahInNearby(localLatLng, loaclKey);
                //searchInCity("深圳","公交");
                break;

            case R.id.tv_bank:
                page = 0;
                loaclKey = "银行";
                searcahInNearby(localLatLng, loaclKey);
                break;

            case R.id.tv_next_page:
                toNextPage();
                break;
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            // }
            return true;
        }
    }
}
