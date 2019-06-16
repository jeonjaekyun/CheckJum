package com.example.jm.checkjum;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JM on 2018-05-22.
 */

//도서관검색(f_library.xml) 프래그먼트
public class F_library extends Fragment {

    SupportMapFragment mapFragment;
    GoogleMap map;
    String myJSON;
    View dialogView;

    /*private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "Library_name";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_LONGITUDE = "Longitude";*/
    private static final String TAG_RESULTS="result";
    private static final String TAG_Ln="Library_name";
    private static final String TAG_Latitude="Latitude";
    private static final String TAG_Longitude="Longitude";
    private static final String TAG_Holiday="Holiday";
    private static final String TAG_Weekday="Weekday";
    private static final String TAG_Saturday="Saturday";
    private static final String TAG_Publicholiday="PublicHoliday";
    private static final String TAG_Address="Address";
    private static final String TAG_Tel="Tel";
    private static final String TAG_Homepage="Homepage";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.f_library, container, false);

        final String url = "http://ipAddress/GetLibraryData.php";

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                getLocationPermission();
                getData(url, 1);

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Log.d("markertitle", marker.getTitle());
                        String url2 = "http://ipAddress/GetLibraryInfo.php?Library_name=" + marker.getTitle();
                        getData(url2, 2);
                        return false;
                    }
                });
            }
        });

        Button btn = view.findViewById(R.id.btnCurrent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMyLocation();
            }
        });

        return view;
    }


    private void getLocationPermission() {
        if(ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            Log.d("DB", "PERMISSION GRANTED");
        }
    }

    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 1000000;
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.d("test", String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    //url을 json형식으로 받아오는 함수
    //flag로 받아오는 url을 구분
    public void getData(String url, final int flag) {
        Log.d("url", url);
        class GetDataJSON extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... strings) { //Strings는 url을 담고있는 배열

                String uri = strings[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    //버퍼로 con(open한 url)을 받아옴
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    //String sb에 버퍼로 읽어온 url의 json 배열들을 저장해줌
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                        Log.d("json", json);
                    }

                    //onPostExecute로 리턴해줌
                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                //받아온 url에 해당하는 함수 실행
                if (flag == 1) {
                    showMark();
                } else if (flag == 2) {
                    MarkerClick();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    //마커 클릭시 대화상자 보여주기
    private void MarkerClick() {
        try {
            //json을 받아온 myJSON을 jsonObject에 넣어줌
            JSONObject jsonObject = new JSONObject(myJSON);
            Log.d("myJSON", myJSON);

            //myJSON안에 TAG_RESULTS(result)를 배열로 받아옴
            JSONArray librarys = jsonObject.getJSONArray(TAG_RESULTS);

            //배열을 librarys의 길이만큼 받아옴
            for (int i = 0; i < librarys.length(); i++) {
                JSONObject c = librarys.getJSONObject(i);
                String Library_name = c.getString(TAG_Ln);
                String Holiday = c.getString(TAG_Holiday);
                String Weekday = c.getString(TAG_Weekday);
                String Saturday = c.getString(TAG_Saturday);
                String Publicholiday = c.getString(TAG_Publicholiday);
                String Address = c.getString(TAG_Address);
                final String Tel = c.getString(TAG_Tel);
                String Homepage = c.getString(TAG_Homepage);


                dialogView = (View) View.inflate(getActivity(), R.layout.libraryinfo, null);
                TextView L_Name, L_holiday, L_Address, L_Saturday, L_PublicHoliday, L_Weekday, L_Homepage, L_Tel;

                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

                L_Name = dialogView.findViewById(R.id.L_Name);
                L_holiday = dialogView.findViewById(R.id.L_holiday);
                L_Address = dialogView.findViewById(R.id.L_Address);
                L_Saturday = dialogView.findViewById(R.id.L_Saturday);
                L_PublicHoliday = dialogView.findViewById(R.id.L_PublicHoliday);
                L_Weekday = dialogView.findViewById(R.id.L_WeekDay);
                L_Homepage = dialogView.findViewById(R.id.L_Homepage);
                L_Tel = dialogView.findViewById(R.id.L_Tel);

                L_Name.setText("도서관명 : " + Library_name);
                L_holiday.setText("휴관일 : " + Holiday);
                L_Address.setText("주소 : " + Address);
                L_Saturday.setText("토요일운영시간 : " + Saturday);
                L_PublicHoliday.setText("공휴일운영시간 : " + Publicholiday);
                L_Weekday.setText("평일운영시간 : " + Weekday);
                L_Homepage.setText(Homepage);
                L_Tel.setText(Tel);
                dlg.setTitle("도서관정보");
                dlg.setView(dialogView);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //지도에 도서관 마커 보여주기
    protected void showMark() {

        try {
            //json을 받아온 myJSON을 jsonObject에 넣어줌
            JSONObject jsonObject = new JSONObject(myJSON);
            Log.d("myJSON", myJSON);

            //myJSON안에 TAG_RESULTS(result)를 배열로 받아옴
            JSONArray librarys = jsonObject.getJSONArray(TAG_RESULTS);

            //배열을 librarys의 길이만큼 받아옴
            for (int i = 0; i < librarys.length(); i++) {
                JSONObject c = librarys.getJSONObject(i);
                String Library_name = c.getString(TAG_Ln);
                String Latitude = c.getString(TAG_Latitude);
                String Longitude = c.getString(TAG_Longitude);

                Log.d("library", Library_name);
                Log.d("library", Latitude);
                Log.d("library", Longitude);

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(Latitude),Double.parseDouble(Longitude)))
                        .title(Library_name));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}