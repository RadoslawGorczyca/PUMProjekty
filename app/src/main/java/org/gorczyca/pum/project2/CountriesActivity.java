package org.gorczyca.pum.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.Constants;

public class CountriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listCountriesShortcuts;

    private String[] countries;
    private String[] shortcuts;

    private boolean isUsingXmlArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_2_countries);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isUsingXmlArray = bundle.getBoolean(Constants.INTENT_KEY_USING_XML_ARRAYS);
        } else {
            finish();
        }
        setTitle(isUsingXmlArray ? R.string.countries_shorts_xml_arrays : R.string.countries_shorts);

        bindViewIds();
        setUI();
    }

    private void bindViewIds() {
        listCountriesShortcuts = findViewById(R.id.list_countries_shortcuts);
    }

    private void setUI() {

        if(isUsingXmlArray){
            countries = getResources().getStringArray(R.array.countries_array);
            shortcuts = getResources().getStringArray(R.array.shortcuts_array);
        } else {
            countries = new String[]{"Polska", "Anglia", "Niemcy", "Francja", "Austria", "Chorwacja", "Ukraina", "Węgry"};
            shortcuts = new String[]{"PL", "EN", "DE", "FR", "A", "HR", "UA", "H"};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, shortcuts);
        listCountriesShortcuts.setAdapter(adapter);
        listCountriesShortcuts.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, countries[position], Toast.LENGTH_SHORT).show();
    }
}
