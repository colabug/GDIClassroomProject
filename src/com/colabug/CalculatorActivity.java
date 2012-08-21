package com.colabug;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends Activity
{
    private TextView display;

    // Calculations
    protected int       storedValue = 0;
    protected Operation operation   = Operation.NONE;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        // Configure view
        setContentView( R.layout.main );
        configureDisplay();
        configureNumberKeys();
        configureMathOperationKeys();
        configureEqualsKey();
        configureClearKey();
    }

    private void configureDisplay()
    {
        display = (TextView) findViewById( R.id.display );
        clearDisplayedValue();
    }

    private void clearDisplayedValue()
    {
        setDisplay( getResources().getString( R.string.EMPTY_STRING ) );
    }

    private void configureNumberKeys()
    {
        View key1 = findViewById( R.id.key1 );
        key1.setOnClickListener( createNumberOnClickListener() );

        View key2 = findViewById( R.id.key2 );
        key2.setOnClickListener( createNumberOnClickListener() );

        View key3 = findViewById( R.id.key3 );
        key3.setOnClickListener( createNumberOnClickListener() );

        View key4 = findViewById( R.id.key4 );
        key4.setOnClickListener( createNumberOnClickListener() );

        View key5 = findViewById( R.id.key5 );
        key5.setOnClickListener( createNumberOnClickListener() );

        View key6 = findViewById( R.id.key6 );
        key6.setOnClickListener( createNumberOnClickListener() );

        View key7 = findViewById( R.id.key7 );
        key7.setOnClickListener( createNumberOnClickListener() );

        View key8 = findViewById( R.id.key8 );
        key8.setOnClickListener( createNumberOnClickListener() );

        View key9 = findViewById( R.id.key9 );
        key9.setOnClickListener( createNumberOnClickListener() );

        View key0 = findViewById( R.id.key0 );
        key0.setOnClickListener( createNumberOnClickListener() );
    }

    private View.OnClickListener createNumberOnClickListener()
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

                // If the last operation was none or equal,
                // reset the display to only this number
                if ( operation == Operation.NONE ||
                     operation == Operation.EQUAL )
                {
                    setDisplay( number );
                    operation = Operation.NUMBER;
                }
                // If we're showing an operation, update the view with
                // the new operation type
                else if ( isDisplayingOperation() )
                {
                    setDisplay( number );
                }
                // A number is already showing, append the number
                else
                {
                    appendNumber( number );
                }
            }
        };
    }

    private boolean isDisplayingOperation()
    {
        String currentDisplay = getCurrentDisplayString();
        return currentDisplay.equals( getResources().getString( R.string.plus ) ) ||
               currentDisplay.equals( getResources().getString( R.string.minus ) ) ||
               currentDisplay.equals( getResources().getString( R.string.multiply ) ) ||
               currentDisplay.equals( getResources().getString( R.string.divide ) ) ||
               currentDisplay.equals( getResources().getString( R.string.modulo ) );
    }

    private void appendNumber( String number )
    {
        setDisplay( getCurrentDisplayString() + number );
    }

    private String getCurrentDisplayString()
    {
        return display.getText().toString();
    }

    private void configureMathOperationKeys()
    {
        configurePlusKey();
        configureMinusKey();
        configureMultiplyKey();
        configureDivideKey();
        configureModuloKey();
    }

    private void configurePlusKey()
    {
        View plus = findViewById( R.id.plus );
        plus.setOnClickListener( createOperationClickListener( Operation.PLUS ) );
    }

    private void configureMinusKey()
    {
        View minus = findViewById( R.id.minus );
        minus.setOnClickListener( createOperationClickListener( Operation.MINUS ) );
    }

    private void configureMultiplyKey()
    {
        View multiply = findViewById( R.id.multiply );
        multiply.setOnClickListener( createOperationClickListener( Operation.MULTIPLY ) );
    }

    private void configureDivideKey()
    {
        View divide = findViewById( R.id.divide );
        divide.setOnClickListener( createOperationClickListener( Operation.DIVIDE ) );
    }

    private void configureModuloKey()
    {
        View modulo = findViewById( R.id.modulo );
        modulo.setOnClickListener( createOperationClickListener( Operation.MODULO ) );
    }

    private View.OnClickListener createOperationClickListener( final Operation op )
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                // Ignore clicks when no number entered, or when no operation
                // has been set
                if ( operation == Operation.NONE ||
                     TextUtils.isEmpty( display.getText() ) )
                {
                    return;
                }

                // Store value
                if ( !isDisplayingOperation() )
                {
                    storeDisplayedValue();
                }

                // Store operation & update display. Having the operator
                // update outside of the above condition means that
                // the user can change their mind.
                operation = op;
                setDisplay( ( (Button) view ).getText() );
            }
        };
    }

    private void storeDisplayedValue()
    {
        try
        {
            storedValue = Integer.parseInt( getCurrentDisplayString() );
        }
        catch ( NumberFormatException ignored )
        {
        }
    }

    private void configureEqualsKey()
    {
        View equal = findViewById( R.id.equal );
        equal.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                // They must have entered a number
                if ( shouldPerformCalculation() )
                {
                    performCalculation();
                }
            }
        } );
    }

    private boolean shouldPerformCalculation()
    {
        return !isDisplayingOperation() &&
               operation != Operation.NONE &&
               !TextUtils.isEmpty( getCurrentDisplayString() );
    }

    private void performCalculation()
    {
        // Attempt to obtain integer from current display
        int value;
        try
        {
            value = Integer.parseInt( getCurrentDisplayString() );
        }
        catch ( NumberFormatException e )
        {
            return;
        }

        // Perform the operation
        switch ( operation )
        {
            case PLUS:
                setDisplay( storedValue + value );
                break;

            case MINUS:
                setDisplay( storedValue - value );
                break;

            case MULTIPLY:
                setDisplay( storedValue * value );
                break;

            case DIVIDE:
                setDisplay( storedValue / value );
                break;

            case MODULO:
                setDisplay( storedValue % value );
                break;
        }

        storedValue = 0;
        operation = Operation.EQUAL;
    }

    private void setDisplay( Object result )
    {
        display.setText( String.valueOf( result ) );
    }

    private void configureClearKey()
    {
        View clear = findViewById( R.id.clear );
        clear.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                clearDisplayedValue();
                storedValue = 0;
                operation = Operation.NONE;
            }
        } );
    }
}
