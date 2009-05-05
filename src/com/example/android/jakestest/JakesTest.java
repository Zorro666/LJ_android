package com.example.android.jakestest;

import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.TextView;
import android.opengl.GLSurfaceView;

public class JakesTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBaseView = new LinearLayout( this );
        myBaseView.setOrientation( LinearLayout.VERTICAL );
        TextView tv = new TextView(this);
        tv.setText("Hello, Android");
//y        myBaseView.addView(tv);

        // Create our Preview view and set it as the content of our
        // Activity
        
        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setRenderer(new JakeRenderer(true));
//        myBaseView.addView(mGLSurfaceView);
//        setContentView( myBaseView );
        setContentView( mGLSurfaceView);

    }
    @Override
    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private GLSurfaceView mGLSurfaceView;
    private LinearLayout myBaseView;
}