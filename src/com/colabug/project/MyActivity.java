package com.colabug.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity
{
    private TextView result;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        result = (TextView) findViewById( R.id.result );

        View key1 = findViewById( R.id.button_1 );
        key1.setOnClickListener( createNumberClickListener() );

        View key2 = findViewById( R.id.button_2 );
        key2.setOnClickListener( createNumberClickListener() );

        View key3 = findViewById( R.id.button_3 );
        key3.setOnClickListener( createNumberClickListener() );
    }

    private View.OnClickListener createNumberClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                // Note: The button knows what is displayed on it, so we
                //       can grab that text instead of keeping track another way

                // Cast view to a Button so we can get the text shown (the number)
                Button keyPressed = (Button) view;

                // Get the string from the button pressed
                String number = keyPressed.getText().toString();

                // Get the current number displayed
                String currentDisplay = result.getText().toString();

                // Append the number to the display
                String newNumberToDisplay = currentDisplay + number;

                // Set the display to the new string
                result.setText( newNumberToDisplay );
            }
        };
    }
}
