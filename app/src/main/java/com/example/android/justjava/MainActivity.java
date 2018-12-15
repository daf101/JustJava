
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.duration;
import static android.os.Build.VERSION_CODES.M;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
        else{
            Toast tooMuch = Toast.makeText(this, getString(R.string.strMax100PerOrder), Toast.LENGTH_SHORT);
            tooMuch.show();
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
        else {
            // You cannot have less than one coffee
            Toast tooLittle = Toast.makeText(this, getString(R.string.strYouCannotOrderLessThan1Coffee), Toast.LENGTH_SHORT);
            tooLittle.show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate);

        createOrderSummary(price,hasWhippedCream,hasChocolate,name);
        // Display the order summary on the screen
//        String message = createOrderSummary(price, hasWhippedCream, hasChocolate,name);
//        displayMessage(message);
    }

    /**
     * Calculates the price of the order.
     * @param hasWhippedCream will add $1 to the total price per cup when selected
     * @param hasChochlate will add $2 to the total price per cup when selected
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChochlate) {

        int perCupPrice = 5;

        if (hasWhippedCream == false || hasChochlate == false) {
            perCupPrice = 5;
        }
        if (hasChochlate) {
            perCupPrice += 2;
        }
        if (hasWhippedCream) {
            perCupPrice += 1;
        }
        return perCupPrice * quantity;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     *
     */
    private void createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.strName) + ": " + name;
        priceMessage += "\n" + getString(R.string.strAdd) + " " + getString(R.string.strWhippedCream) + "? " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.strAdd) + " " + getString(R.string.strChocolate) + "? " + addChocolate;
        priceMessage += "\n" + getString(R.string.strQty) + " " + quantity;
        priceMessage += "\n" + getString(R.string.strTotal) + ": " + getString(R.string.strDollarSign) + price;
        priceMessage += "\n" + getString(R.string.strThankYou) + "!";
        String subject = getString(R.string.app_name) + " " + getString(R.string.strCoffee) + " " + getString(R.string.strOrder) + " " + getString(R.string.strFor) + " " + name;
        //String subject = "Just Java Coffee Order for " + name;

        Intent emailOrder = new Intent(Intent.ACTION_SENDTO);
        emailOrder.setData(Uri.parse("mailto:jonesmatt1991@gmail.com"));
        emailOrder.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailOrder.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailOrder.resolveActivity(getPackageManager()) != null) {
            startActivity(emailOrder);
        }



    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

}