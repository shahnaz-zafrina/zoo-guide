package com.example.zooui;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.zooui.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    MarkerOptions marker;
    LatLng centerlocation;

    Vector<MarkerOptions> markerOptions;
    private Polyline routePolyline;
    private boolean drawStraightLine = false;

    Button routeButton;
    String receivedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent receiverIntent = getIntent();
        receivedValue = receiverIntent.getStringExtra("KEY_SENDER");

        // Retrieve the selected items from the intent
        ArrayList<String> selectedItems = getIntent().getStringArrayListExtra("SELECTED_ITEMS");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        centerlocation = new LatLng(3.20850, 101.75788);

        markerOptions = new Vector<>();

        if (receivedValue != null && receivedValue.equals("Multi-Animal Show")) {
            // Create and add markers to the map
            markerOptions.add(new MarkerOptions()
                    .position(new LatLng(3.20882, 101.76029))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mishow))
                    .title("Multi-Animal Show")
                    .snippet("11AM")
            );
        } else if (receivedValue != null && receivedValue.equals("Animal Feeding Session")) {
            markerOptions.add(new MarkerOptions()
                    .position(new LatLng(3.20998, 101.75978))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mifeeding))
                    .title("Animal Feeding")
                    .snippet("2.00PM - 3.00PM"));
        }

        // Use the selected items as needed
        if (selectedItems != null) {
            for (String item : selectedItems) {
                if (item.equals("Malayan Tiger")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21070, 101.75768))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mitiger))
                            .title("Malayan Tiger"));
                } else if (item.equals("White Lion")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21071, 101.75742))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.milion))
                            .title("White Lion"));
                } else if (item.equals("King Cheetah")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21043, 101.75790))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.micheetah))
                            .title("King Cheetah"));
                } else if (item.equals("Capybara")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20949, 101.76054))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.micapy))
                            .title("Capybara"));
                } else if (item.equals("Ring Tailed Lemur")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20934, 101.75948))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.milemur))
                            .title("Ring Tailed Lemur"));
                } else if (item.equals("Javan Deer")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20998, 101.75978))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mideer))
                            .title("Javan Deer"));
                } else if (item.equals("Children's World")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20920, 101.75949))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.micw))
                            .title("Children's World"));
                } else if (item.equals("Bear Complex")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21081, 101.76064))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mibear))
                            .title("Bear Complex"));
                } else if (item.equals("Giraffe")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20897, 101.75726))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.migiraffe))
                            .title("Giraffe"));
                } else if (item.equals("Himalayan Griffon")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20889, 101.75987))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.migriffon))
                            .title("Himalayan Griffon"));
                } else if (item.equals("Agile Wallaby")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21020, 101.75960))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miwallaby))
                            .title("Agile Wallaby"));
                } else if (item.equals("Asian Small-Clawed Otter")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20906, 101.75975))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miotter))
                            .title("Asian Small-Clawed Otter"));
                } else if (item.equals("Nile Hippopotamus")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20936, 101.75988))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mihippo))
                            .title("Nile Hippopotamus"));
                } else if (item.equals("Malayan Tapir")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21040, 101.75980))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mitapir))
                            .title("Malayan Tapir"));
                } else if (item.equals("Entrances")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20893, 101.75804))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mientrance))
                            .title("Entrance A"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20876, 101.76150))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mientrance))
                            .title("Entrance B"));
                } else if (item.equals("Ticket Counter")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20870, 101.75792))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miticket))
                            .title("Ticket Counter"));
                } else if (item.equals("Tram Stations")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20905, 101.75943))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mitram))
                            .title("Tram Station"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21030, 101.75831))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mitram))
                            .title("Tram Station"));
                } else if (item.equals("Souvenir Shop")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20893, 101.75823))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.misoushop))
                            .title("Souvenir Shop"));
                } else if (item.equals("Toilets")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20891, 101.75824))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miwc))
                            .title("Toilet"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20879, 101.76034))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miwc))
                            .title("Toilet"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21035, 101.76065))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miwc))
                            .title("Toilet"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21100, 101.75892))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.miwc))
                            .title("Toilet"));
                } else if (item.equals("Restaurants and Kiosks")) {
                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20909, 101.75984))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20895, 101.75822))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20907, 101.75881))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21035, 101.75976))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20994, 101.75995))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.20970, 101.75625))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));

                    markerOptions.add(new MarkerOptions()
                            .position(new LatLng(3.21112, 101.75892))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirest))
                            .title("Restaurant and Kiosk"));
                }
            }
        }

        Button drawRouteButton = findViewById(R.id.drawRouteButton);
        drawRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawRoutes();
            }
        });

        // Assuming you have a button with the ID "routeButton" in your layout XML file

        routeButton = findViewById(R.id.routeButton);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        enableMyLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation, 15));

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};

            // 2. Otherwise, request location permissions from the user.
            ActivityCompat.requestPermissions(this, perms, 200);
        }
    }

    private void drawRoutes() {
        // Get user's current location
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    // Draw routes from user's location to all selected markers
                    for (MarkerOptions mark : markerOptions) {
                        LatLng destination = mark.getPosition();
                        drawRoute(userLocation, destination);
                    }

                    // Include the markers initialized from receivedValue
                    if (receivedValue != null && receivedValue.equals("Multi-Animal Show")) {
                        LatLng destination = new LatLng(3.20882, 101.76029);
                        drawRoute(userLocation, destination);
                    } else if (receivedValue != null && receivedValue.equals("Animal Feeding Session")) {
                        LatLng destination = new LatLng(3.20998, 101.75978);
                        drawRoute(userLocation, destination);
                    }

                    // Calculate distance and estimated time for the drawn route
                    calculateDistanceAndTime(userLocation);
                }
            }
        });
    }

    private void drawRoute(LatLng origin, LatLng destination) {
        String url = getDirectionsUrl(origin, destination);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private void calculateDistanceAndTime(LatLng origin) {
        // Calculate total distance and estimated time
        float totalDistance = 0;
        long totalDuration = 0;

        for (MarkerOptions mark : markerOptions) {
            LatLng destination = mark.getPosition();
            float[] results = new float[1];
            Location.distanceBetween(origin.latitude, origin.longitude, destination.latitude, destination.longitude, results);
            float distance = results[0];
            totalDistance += distance;

            // Assuming average walking speed of 1.4 meters per second
            long duration = (long) (distance / 1.4f);
            totalDuration += duration;
        }

        // Convert duration to minutes and seconds
        long minutes = totalDuration / 60;
        long seconds = totalDuration % 60;

        // Print the distance and estimated time
        Log.d("Distance", "Total Distance: " + totalDistance + " meters");
        Log.d("Time", "Estimated Time: " + minutes + " minutes, " + seconds + " seconds");
        float finalTotalDistance = totalDistance;
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the AlertDialog here
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Route Information");
                builder.setMessage("Total Distance: " + finalTotalDistance + " meters\n" +
                        "Estimated Time: " + minutes + " minutes, " + seconds + " seconds");
                builder.setPositiveButton("OK", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    private String getDirectionsUrl(LatLng origin, LatLng destination) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
        String mode = "mode=walking";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "INSERT_GOOGLE_MAP_API_KEY";
        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
            }
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
        }
    }

    public class DirectionsJSONParser {

        public List<List<HashMap<String, String>>> parse(JSONObject jsonObject) {
            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            JSONArray jsonRoutes = null;
            JSONArray jsonLegs = null;
            JSONArray jsonSteps = null;

            try {
                jsonRoutes = jsonObject.getJSONArray("routes");

                // Traverse all routes
                for (int i = 0; i < jsonRoutes.length(); i++) {
                    jsonLegs = ((JSONObject) jsonRoutes.get(i)).getJSONArray("legs");
                    List<HashMap<String, String>> path = new ArrayList<>();

                    // Traverse all legs
                    for (int j = 0; j < jsonLegs.length(); j++) {
                        jsonSteps = ((JSONObject) jsonLegs.get(j)).getJSONArray("steps");

                        // Traverse all steps
                        for (int k = 0; k < jsonSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jsonSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePolyline(polyline);

                            // Traverse all points in the polyline
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString((list.get(l)).latitude));
                                hm.put("lng", Double.toString((list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        private List<LatLng> decodePolyline(String encoded) {
            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
        }
    }
}
