package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder (View view) {

        CheckBox whipped_cream_checkBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate_checkBox = findViewById(R.id.chocolate_checkbox);
        EditText name_edit_text = findViewById(R.id.name_edit_text);

        final Boolean hasWhippedCream = whipped_cream_checkBox.isChecked();
        final Boolean hasChocolate = chocolate_checkBox.isChecked();
        final String  name = name_edit_text.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String orderBody = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        composeIntent(name, orderBody);

//        displayMessage();


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView =  findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method is called when increment button is clicked
     */

    public void increment(View view) {

        if(quantity > 99) {
            Toast.makeText(this, getString(R.string.max_limit_cups), Toast.LENGTH_SHORT).show();
        }
        else {
            quantity = quantity + 1;
            incrementQuantity(quantity);
        }
    }


    /**
     * This method increments the quantity of coffee cups on the screen.
     */

    private void incrementQuantity(int number) {
        display(number);
    }


    /**
     * This method is called when decrement button is clicked
     */

    public void decrement(View view) {
        if(quantity < 2) {
            Toast.makeText(this, getString(R.string.min_limit_cup), Toast.LENGTH_SHORT).show();
        }
        else {
            quantity = quantity - 1;
            decrementQuantity(quantity);
        }
    }


    /**
     * This method decrements the quantity of coffee cups on the screen.
     */

    private void decrementQuantity(int number) {
        display(number);
    }

    /**
     * This method returns price of total cups of coffee.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if(hasWhippedCream) {
            basePrice += 1;
        }
        if(hasChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method returns body of order summary.
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method compose Intent.
     */
    public void composeIntent(String name, String orderBody) {
        /* Your email address here */
        String email = "Joyontasaha8@gmail.com";
        /* Your subject here */
        String subject = getString(R.string.order_summary_email_subject, name);
        /* Your body here */
        String body = orderBody;


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + email)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//
//
//        /* Your chooser title here */
////        String chooserTitle = "Just Java Coffee";
////        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
//
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
////emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
//
//        startActivity(Intent.createChooser(emailIntent, chooserTitle));
//    }


}


