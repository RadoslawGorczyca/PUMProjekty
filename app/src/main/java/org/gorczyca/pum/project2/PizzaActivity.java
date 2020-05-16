package org.gorczyca.pum.project2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;

import java.util.ArrayList;

public class PizzaActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroupPieThickness;
    private RadioButton radioPieThick;
    private RadioButton radioPieThin;
    private RadioGroup radioGroupPieSize;
    private RadioButton radioPieSmall;
    private RadioButton radioPieMedium;
    private RadioButton radioPieBig;
    private CheckBox checkMainIngredientHam;
    private CheckBox checkMainIngredientCheese;
    private CheckBox checkMainIngredientMushrooms;
    private CheckBox checkMainIngredientOlives;
    private CheckBox checkMainIngredientBacon;
    private CheckBox checkMainIngredientChicken;
    private CheckBox checkMainIngredientOnion;
    private CheckBox checkOptionalIngredientGarlic;
    private CheckBox checkOptionalIngredientSalami;
    private CheckBox checkOptionalIngredientShrimps;
    private CheckBox checkOptionalIngredientCapers;
    private CheckBox checkOptionalIngredientTuna;
    private CheckBox checkOptionalIngredientTomatoSauce;
    private CheckBox checkOptionalIngredientGarlicSauce;
    private CheckBox checkOptionalIngredientOregano;
    private Button buttonOrder;
    private PIE_THICKNESS orderedPieThickness;
    private PIE_SIZE orderedPieSize;
    private ArrayList<String> mainIngredientsArrayList;
    private ArrayList<String> optionalIngredientsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_2_pizza);
        setTitle(R.string.ordering_pizza);
        bindViewIds();
        bindOnClickListeners();
    }

    private void bindViewIds() {
        radioGroupPieThickness = findViewById(R.id.radio_group_pie_thickness);
        radioPieThick = findViewById(R.id.radio_pie_thick);
        radioPieThin = findViewById(R.id.radio_pie_thin);
        radioGroupPieSize = findViewById(R.id.radio_group_pie_size);
        radioPieSmall = findViewById(R.id.radio_pie_small);
        radioPieMedium = findViewById(R.id.radio_pie_medium);
        radioPieBig = findViewById(R.id.radio_pie_big);
        checkMainIngredientHam = findViewById(R.id.ingredient_ham);
        checkMainIngredientCheese = findViewById(R.id.ingredient_cheese);
        checkMainIngredientMushrooms = findViewById(R.id.ingredient_mushrooms);
        checkMainIngredientOlives = findViewById(R.id.ingredient_olives);
        checkMainIngredientBacon = findViewById(R.id.ingredient_bacon);
        checkMainIngredientChicken = findViewById(R.id.ingredient_chicken);
        checkMainIngredientOnion = findViewById(R.id.ingredient_onion);
        checkOptionalIngredientGarlic = findViewById(R.id.ingredient_garlic);
        checkOptionalIngredientSalami = findViewById(R.id.ingredient_salami);
        checkOptionalIngredientShrimps = findViewById(R.id.ingredient_shrimps);
        checkOptionalIngredientCapers = findViewById(R.id.ingredient_capers);
        checkOptionalIngredientTuna = findViewById(R.id.ingredient_tuna);
        checkOptionalIngredientTomatoSauce = findViewById(R.id.ingredient_tomato_sauce);
        checkOptionalIngredientGarlicSauce = findViewById(R.id.ingredient_garlic_sauce);
        checkOptionalIngredientOregano = findViewById(R.id.ingredient_oregano);
        buttonOrder = findViewById(R.id.button_order);
    }

    private void bindOnClickListeners() {
        buttonOrder.setOnClickListener(this);
    }

    private boolean isOrderCorrect() {
        if (radioGroupPieThickness.getCheckedRadioButtonId() == radioPieThick.getId()) {
            orderedPieThickness = PIE_THICKNESS.THICK;
        } else if (radioGroupPieThickness.getCheckedRadioButtonId() == radioPieThin.getId()) {
            orderedPieThickness = PIE_THICKNESS.THIN;
        } else {
            return false;
        }

        if (radioGroupPieSize.getCheckedRadioButtonId() == radioPieSmall.getId()) {
            orderedPieSize = PIE_SIZE.SMALL;
        } else if (radioGroupPieSize.getCheckedRadioButtonId() == radioPieMedium.getId()) {
            orderedPieSize = PIE_SIZE.MEDIUM;
        } else if (radioGroupPieSize.getCheckedRadioButtonId() == radioPieBig.getId()) {
            orderedPieSize = PIE_SIZE.BIG;
        } else {
            return false;
        }

        mainIngredientsArrayList = new ArrayList<>();
        optionalIngredientsArrayList = new ArrayList<>();

        if (checkMainIngredientHam.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_ham));
        if (checkMainIngredientCheese.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_cheese));
        if (checkMainIngredientMushrooms.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_mushrooms));
        if (checkMainIngredientOlives.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_olives));
        if (checkMainIngredientBacon.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_bacon));
        if (checkMainIngredientChicken.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_chicken));
        if (checkMainIngredientOnion.isChecked())
            mainIngredientsArrayList.add(getString(R.string.order_ingredient_onion));
        if (checkOptionalIngredientGarlic.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_garlic));
        if (checkOptionalIngredientSalami.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_salami));
        if (checkOptionalIngredientShrimps.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_shrimps));
        if (checkOptionalIngredientCapers.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_capers));
        if (checkOptionalIngredientTuna.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_tuna));
        if (checkOptionalIngredientTomatoSauce.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_tomato_sauce));
        if (checkOptionalIngredientGarlicSauce.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_garlic_sauce));
        if (checkOptionalIngredientOregano.isChecked())
            optionalIngredientsArrayList.add(getString(R.string.order_ingredient_oregano));

        if (mainIngredientsArrayList.size() < 3) return false;
        return optionalIngredientsArrayList.size() <= 2;
    }

    private void orderPizza() {
        int pizzaPrice = 0;
        StringBuilder orderMessageBuilder = new StringBuilder();
        orderMessageBuilder.append(getString(R.string.ordered));
        switch (orderedPieSize) {
            case SMALL:
                orderMessageBuilder.append(getString(R.string.order_thickness_small));
                pizzaPrice += 10;
                break;
            case MEDIUM:
                orderMessageBuilder.append(getString(R.string.order_thickness_medium));
                pizzaPrice += 20;
                break;
            case BIG:
                orderMessageBuilder.append(getString(R.string.order_thickness_big));
                pizzaPrice += 30;
                break;
        }
        orderMessageBuilder.append(getString(R.string.pizza_on));
        switch (orderedPieThickness) {
            case THIN:
                orderMessageBuilder.append(getString(R.string.order_thickness_thin));
                break;
            case THICK:
                orderMessageBuilder.append(getString(R.string.order_thickness_thick));
                pizzaPrice += 2;
                break;
        }

        orderMessageBuilder.append(getString(R.string.dough_containing));
        for (int i = 0; i < mainIngredientsArrayList.size(); i++) {
            orderMessageBuilder.append(mainIngredientsArrayList.get(i));
            if (i < mainIngredientsArrayList.size() - 1)
                orderMessageBuilder.append(", ");
        }

        if(optionalIngredientsArrayList.size() > 0){
            orderMessageBuilder.append(", ");
        }

        for (int i = 0; i < optionalIngredientsArrayList.size(); i++) {
            orderMessageBuilder.append(optionalIngredientsArrayList.get(i));
            if (i < optionalIngredientsArrayList.size() - 1)
                orderMessageBuilder.append(", ");
        }
        orderMessageBuilder.append(". ");

        pizzaPrice += 2 * mainIngredientsArrayList.size() + 2 * optionalIngredientsArrayList.size();

        orderMessageBuilder.append(getString(R.string.pizza_price_will_be)).append(pizzaPrice).append(getString(R.string.pln));
        String orderMessage = orderMessageBuilder.toString();

        new AlertDialog.Builder(this)
                .setTitle(R.string.order_info)
                .setMessage(orderMessage)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonOrder.getId()) {
            if (isOrderCorrect()) {
                orderPizza();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.error)
                        .setMessage(R.string.error_wrong_order)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        }
    }

    enum PIE_THICKNESS {
        THIN, THICK
    }

    enum PIE_SIZE {
        SMALL, MEDIUM, BIG
    }
}
