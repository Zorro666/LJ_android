package com.example.android.jakestest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class Graph 
{
	public Graph()
	{
        int one = 0x10000;
        int vertices[] = {
                0, 0, 0,
                0,  one, 0,
                one, 0, 0,
                one, one, 0,
        };
        int vLine[] = {
                0, 0, 0,
                0,  one, 0 
                	   };
        int hLine[] = {
                0, 0, 0,
                one, 0, 0 
                	   };
        
        byte indices[] = { 0, 1, 2, 3 };
        byte lineIndices[] = { 0, 1, 3, 2, 0 };

		mMin = 2;
		mMax = 12;
		
		mX0 = -0.7f;
		mY0 = -0.9f;
		
		Resize();
		
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        
        vbb = ByteBuffer.allocateDirect(vLine.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVline = vbb.asIntBuffer();
        mVline.put(vLine);
        mVline.position(0);
        
        vbb = ByteBuffer.allocateDirect(hLine.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mHline = vbb.asIntBuffer();
        mHline.put(hLine);
        mHline.position(0);
        
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);

        mLineBuffer = ByteBuffer.allocateDirect(lineIndices.length);
        mLineBuffer.put(lineIndices);
        mLineBuffer.position(0);
	}
	public void SetMin( Integer min )
	{
		mMin = min;
		Resize();
	}
	public void SetMax( Integer max )
	{
		mMax = max;
		Resize();
	}
	public void Render( GL10 gl )
	{
		gl.glPushMatrix();
        gl.glFrontFace(gl.GL_CW);
        
		Integer size = mBars.size();
		gl.glTranslatef( mX0, mY0, 0.0f );
		gl.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
		
		gl.glPushMatrix();
		gl.glScalef( 1.5f, 1.0f, 1.0f );
        gl.glVertexPointer(3, gl.GL_FIXED, 0, mHline);
		gl.glDrawElements(gl.GL_LINE_STRIP, 2, gl.GL_UNSIGNED_BYTE, mLineBuffer );
		
		gl.glScalef( 1.0f, 1.5f, 1.0f );
        gl.glVertexPointer(3, gl.GL_FIXED, 0, mVline);
		gl.glDrawElements(gl.GL_LINE_STRIP, 2, gl.GL_UNSIGNED_BYTE, mLineBuffer );
		
		gl.glPopMatrix();
		gl.glTranslatef( 0.06f, 0.06f, 0.0f );
		float scale = 1.0f / size;
		gl.glScalef( scale, 1.0f, 1.0f );
        gl.glVertexPointer(3, gl.GL_FIXED, 0, mVertexBuffer);
		for ( Integer i = 0; i < size; ++i )
		{
			Integer value = mMin + i;
			Integer count = mBars.get( i );
			float heightScale = count / 10.0f + 0.005f;
			gl.glColor4f(1.0f, 0.6f, 0.8f, 1.0f );
			gl.glPushMatrix();
			gl.glScalef( 1.0f, heightScale, 1.0f );
			gl.glDrawElements(gl.GL_TRIANGLE_STRIP, 4, gl.GL_UNSIGNED_BYTE, mIndexBuffer);
			gl.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			gl.glDrawElements(gl.GL_LINE_STRIP, 5, gl.GL_UNSIGNED_BYTE, mLineBuffer );
			gl.glPopMatrix();
			gl.glTranslatef( 1.2f, 0.0f, 0.0f );
		}
		gl.glPopMatrix();
	}
	private void Resize()
	{
		Integer size = mMax - mMin + 1;
		if ( size < 0 )
		{
			return;
		}
		mBars.clear();
		mBars.ensureCapacity(size);
		for ( Integer i = 0; i < size; ++i )
		{
			mBars.add( 0 );
		}
		Zero();
	}
	private void Zero()
	{
		Integer size = mBars.size();
		for ( Integer i = 0; i < size; ++i )
		{
			mBars.set( i, i );
		}
	}
	private ArrayList<Integer>		mBars = new ArrayList<Integer>();
	private Integer					mMin = 2;
	private Integer					mMax = 12;
    private IntBuffer   mVertexBuffer;
    private IntBuffer   mHline;
    private IntBuffer   mVline;
    private ByteBuffer  mIndexBuffer;
    private ByteBuffer  mLineBuffer;
    private float mX0;
    private float mY0;
}
