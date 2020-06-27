package org.gorczyca.pum.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.gorczyca.pum.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public class InterestingPlacesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLalamido;
    private Button buttonWNSiT;
    private Button buttonDS2;
    private Button buttonRemedium;
    private Button buttonAdditionalInfo;
    private Button buttonShowOnMap;
    private WebView webViewInterestingPlaces;
    private TextView textAdditionalInfo;

    private List<PlaceItem> placeItemList = new ArrayList<>();
    private PlaceItem selectedPlaceItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_4_interesting_places);
        setTitle(R.string.interesting_places);
        bindIds();
        setListeners();
        setUpWebView();
        setUpPlaces();
    }

    private void bindIds() {
        buttonLalamido = findViewById(R.id.button_place1);
        buttonWNSiT = findViewById(R.id.button_place2);
        buttonDS2 = findViewById(R.id.button_place3);
        buttonRemedium = findViewById(R.id.button_place4);
        buttonAdditionalInfo = findViewById(R.id.button_additional_info);
        buttonShowOnMap = findViewById(R.id.button_show_on_map);
        webViewInterestingPlaces = findViewById(R.id.web_interesting_places);
        textAdditionalInfo = findViewById(R.id.text_additional_info);
    }

    private void setListeners() {
        buttonLalamido.setOnClickListener(this);
        buttonWNSiT.setOnClickListener(this);
        buttonDS2.setOnClickListener(this);
        buttonRemedium.setOnClickListener(this);
        buttonAdditionalInfo.setOnClickListener(this);
        buttonShowOnMap.setOnClickListener(this);
    }

    private void setUpWebView() {
        webViewInterestingPlaces.getSettings().setJavaScriptEnabled(true);
        webViewInterestingPlaces.setWebViewClient(new WebViewClient());
    }

    private void setUpPlaces() {
        placeItemList.add(new PlaceItem("Lalamido", 50.2953425, 19.1331337, "Tani bufet blisko akademików. Często uczęszczany lokal przez studentów."));
        placeItemList.add(new PlaceItem("WNŚiT", 50.29738893652919, 19.134989993805988, "Insytut Informatyki Uniwersytetu Śląskiego, gdzie swoje zajęcia odbywają między innymi studenci Informatyki."));
        placeItemList.add(new PlaceItem("DS2", 50.29649111303177, 19.13273126805959, "Dom Studencki nr 2, czyli najlepszy akademik w Sosnowcu! :)"));
        placeItemList.add(new PlaceItem("Remedium", 50.29746429393786, 19.133273124479317, "Najlepszy klub studencki pod Słońcem!"));
    }

    private PlaceItem getPlaceByName(String name){
        return placeItemList.stream().filter(place -> place.getName().equals(name)).findFirst().orElse(null);
    }

    private void showPlacesButtons(){
        buttonShowOnMap.setVisibility(View.VISIBLE);
        buttonAdditionalInfo.setVisibility(View.VISIBLE);
    }

    private void hidePlacesInfoAndMap(){
        webViewInterestingPlaces.setVisibility(View.GONE);
        textAdditionalInfo.setVisibility(View.GONE);
    }

    private void showOnMap() {
        textAdditionalInfo.setVisibility(View.GONE);
        webViewInterestingPlaces.setVisibility(View.VISIBLE);
        webViewInterestingPlaces.loadUrl("https://maps.google.com/maps?q=" + selectedPlaceItem.getLat() + "," + selectedPlaceItem.getLng() + "&t=m&z=13");
    }

    private void showInfo() {
        webViewInterestingPlaces.setVisibility(View.GONE);
        textAdditionalInfo.setVisibility(View.VISIBLE);
        textAdditionalInfo.setText(selectedPlaceItem.getAdditionalInfo());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonLalamido.getId()){
            hidePlacesInfoAndMap();
            selectedPlaceItem = getPlaceByName("Lalamido");
            showPlacesButtons();
        } else if (v.getId() == buttonWNSiT.getId()){
            hidePlacesInfoAndMap();
            selectedPlaceItem = getPlaceByName("WNŚiT");
            showPlacesButtons();
        } else if (v.getId() == buttonDS2.getId()){
            hidePlacesInfoAndMap();
            selectedPlaceItem = getPlaceByName("DS2");
            showPlacesButtons();
        } else if (v.getId() == buttonRemedium.getId()){
            hidePlacesInfoAndMap();
            selectedPlaceItem = getPlaceByName("Remedium");
            showPlacesButtons();
        } else if(v.getId() == buttonAdditionalInfo.getId()){
            showInfo();
        } else if(v.getId() == buttonShowOnMap.getId()){
            showOnMap();
        }
    }
}
