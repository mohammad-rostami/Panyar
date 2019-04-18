package in.public_static.carin.paniar.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import in.public_static.carin.paniar.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Activity_Map extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private int mInt_X = 0;
    private String mStr_Address;
    private LatLng mLatLng_Marker;
    private Marker marker;
    private LatLng mLatLng_Current;
    private Dialog Dialog_Location;
    private ImageView mImageView_MapType;
    private EditText mEditText_Search;
    private Typeface type;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        type = Typeface.createFromAsset(getAssets(), "vazir.ttf");


        Dialog_Location = new Dialog(this);
        Dialog_Location.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog_Location.setContentView(R.layout.dialog_location);
        Dialog_Location.setCancelable(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mFragmentMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_fr_main);
        mFragmentMap.getMapAsync(this);


        LinearLayout mLinearLayout_MapType = (LinearLayout) findViewById(R.id.activity_map_ll_type);
        mImageView_MapType = (ImageView) findViewById(R.id.activity_map_img_type);
//        mImageView_MapType.setImageResource(R.drawable.ic_satellite_black_24dp);

        TextView mDialogTextView_Topic = (TextView) Dialog_Location.findViewById(R.id.dialog_location_txt_topic);
        LinearLayout mDialogLinearLayout_Current = (LinearLayout) Dialog_Location.findViewById(R.id.dialog_location_ll_current);
        TextView mDialogTextView_Current = (TextView) Dialog_Location.findViewById(R.id.dialog_location_txt_current);
        LinearLayout mDialogLinearLayout_marker = (LinearLayout) Dialog_Location.findViewById(R.id.dialog_location_ll_marker);
        final TextView mDialogTextView_marker = (TextView) Dialog_Location.findViewById(R.id.dialog_location_txt_marker);
        final ImageView mDialogImageView_marker = (ImageView) Dialog_Location.findViewById(R.id.dialog_location_img_marker);

//        mDialogTextView_Topic.setTypeface(G.TYPE_FACE_SANS);
//        mDialogTextView_marker.setTypeface(G.TYPE_FACE_SANS);
//        mDialogTextView_Current.setTypeface(G.TYPE_FACE_SANS);

        Button mButton_Confirm = (Button) findViewById(R.id.activity_map_btn_confirm);
        mButton_Confirm.setTypeface(type);


        mButton_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker == null) {
                    mDialogImageView_marker.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_gray));
                    mDialogTextView_marker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_gray));
                } else {
                    mDialogImageView_marker.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_default));
                    mDialogTextView_marker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_txt));
                }

                Dialog_Location.show();
            }
        });

        mDialogLinearLayout_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker != null) {
                    getCompleteAddressString(mLatLng_Marker.latitude, mLatLng_Marker.longitude);
                    Dialog_Location.dismiss();
                    ActivityOrder.address_setter(mStr_Address);
                    finish();
                }
            }
        });
        mDialogLinearLayout_Current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getCompleteAddressString(mLatLng_Current.latitude, mLatLng_Current.longitude);
                    Dialog_Location.dismiss();
                    ActivityOrder.address_setter(mStr_Address);
                    finish();
                } catch (Exception e) {
                    Log.d("error", String.valueOf(e));
                }
            }
        });


        mLinearLayout_MapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Activity_Map.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(Activity_Map.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//                if (marker != null) {
//                    marker.remove();
//                }

                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                if (mLastLocation != null) {
                //place marker at current position
                //mGoogleMap.clear();
                try {
                    LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                }catch (Exception e){

                }
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Current Position");
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                marker = mMap.addMarker(markerOptions);
//                }
//                switch (mInt_X) {
//                    case 0:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                        mImageView_MapType.setImageResource(R.drawable.ic_terrain_black_24dp);
//                        mInt_X = 1;
//                        break;
//                    case 1:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                        mImageView_MapType.setImageResource(R.drawable.ic_map_black_24dp);
//                        mInt_X = 2;
//                        break;
//                    case 2:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                        mImageView_MapType.setImageResource(R.drawable.ic_satellite_black_24dp);
//                        mInt_X = 0;
//                        break;
//                }
            }
        });

        ImageView mImageView_Search = (ImageView) findViewById(R.id.activity_map_btn_search);
        ImageView mImageView_Clean = (ImageView) findViewById(R.id.activity_map_btn_clean);
        mEditText_Search = (EditText) findViewById(R.id.activity_map_et_location);
        mEditText_Search.setTypeface(type);

        mImageView_Clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText_Search.setText("");
            }
        });
        mImageView_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapSearch();
            }
        });
        mEditText_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onMapSearch();
                return false;
            }
        });

    }

    public void onMapSearch() {
        String location = mEditText_Search.getText().toString();
        List<android.location.Address> mList_address = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                mList_address = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                android.location.Address address = mList_address.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                if (marker != null) {
                    marker.remove();
                }

                marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                mLatLng_Marker = marker.getPosition();
            } catch (Exception e) {
                Toast.makeText(Activity_Map.this, "منطقه مورد نظر یافت نشد!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
//        //Place current location marker
        mLatLng_Current = new LatLng(location.getLatitude(), location.getLongitude());

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng_Current));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        if (marker != null) {
            marker.remove();
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        if (marker != null) {
        }

        mStr_Address = "";
        String mStr_Language = "fa";
        Locale mLocale = new Locale(mStr_Language);
        Geocoder geocoder = new Geocoder(this, mLocale);
        try {
            List<android.location.Address> mList_address1 = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (mList_address1 != null) {
                android.location.Address returnedAddress = mList_address1.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                mStr_Address = strReturnedAddress.toString();
                Log.w("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Cannot get Address!");
        }
        return mStr_Address;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .draggable(true).visible(true));

        mLatLng_Marker = marker.getPosition();

    }
}
