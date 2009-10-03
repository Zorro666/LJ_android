package com.example.android.jakestest;

import android.app.Activity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

public class JakesTest extends Activity implements OnTouchListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mMyRenderer = new JakeRenderer(true);
        mGLSurfaceView = new GLSurfaceView( this );
        mGLSurfaceView.setRenderer(mMyRenderer);
        mGLSurfaceView.setOnTouchListener( this );
        
        setContentView( R.layout.main );
        ViewGroup absLayout = (ViewGroup)findViewById( R.id.AbsoluteLayout01 );
        absLayout.addView( mGLSurfaceView );
        
        mResetButton = (Button)findViewById( R.id.ButtonReset );
        mResetButton.setOnTouchListener(this);
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

    public boolean onTouch( View v, MotionEvent event )
    {
    	if ( v == mResetButton )
    	{
    		mMyRenderer.resetGraph();
    		return true;
    	}
    	else if ( v == mGLSurfaceView )
    	{
    		return mMyRenderer.onTouch( v, event );
    	}
    	return false;
    }
    private GLSurfaceView mGLSurfaceView;
    private JakeRenderer mMyRenderer;
    private View mResetButton;
}