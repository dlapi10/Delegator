package freeuni.android.delegator.map;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import freeuni.android.delegator.R;
import freeuni.android.delegator.ui.SuperActivity;

public class MapActivity extends SuperActivity implements OnMapClickListener,
		OnMyLocationButtonClickListener {

	private GoogleMap mMap;
	private MarkerOptions mMarkerOptings;
	private static final String LAT_STRING = "lat";
	private static final String LNG_STRING = "lng";
	private double lat, lng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_main);
		setUpMapIfNeeded();
		
		if(savedInstanceState != null){
			lat = savedInstanceState.getDouble(LAT_STRING);
			lng = savedInstanceState.getDouble(LNG_STRING);
			mMarkerOptings=new MarkerOptions().position(new LatLng(lat, lng))
					.title("Meeting Place").draggable(true);
					mMap.addMarker(mMarkerOptings);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putDouble(LAT_STRING,lat);
		outState.putDouble(LNG_STRING, lng);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		
	}
	
	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			if (mMap != null) {
				mMap.setMyLocationEnabled(true);
				mMap.setOnMyLocationButtonClickListener(this);
				mMap.setOnMapClickListener(this);
			}
		}
	}
	
	@Override
	public boolean onMyLocationButtonClick() {
		return false;
	}

	@Override
	public void onMapClick(LatLng pos) {
		mMap.clear();
		mMarkerOptings = new MarkerOptions().position(pos)
				.title("Meeting Place").draggable(true);
		mMap.addMarker(mMarkerOptings);
		
		lat = pos.latitude;
		lng = pos.longitude;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public void onSaveButtonClicked(){
		//db chawera
	}
}
