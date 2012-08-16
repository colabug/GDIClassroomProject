package com.colabug.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View view = findViewById( R.id.button_1 );
        view.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Toast.makeText( MyActivity.this,
                                getString( R.string.clicked_text ),
                                Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
