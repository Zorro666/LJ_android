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
                -one, -one, 0,
                -one,  one, 0,
                one, -one, 0,
                one,  one, 0,
        };
        
        //byte indices[] = { 0, 1, 2, 3 };
        byte indices[] = { 0, 1, 2, 3 };

		mMin = 2;
		mMax = 12;
		Resize();
		
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
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
        gl.glFrontFace(gl.GL_CW);
        gl.glVertexPointer(3, gl.GL_FIXED, 0, mVertexBuffer);
        
		Integer size = mBars.size();
		gl.glTranslatef( -0.9f, 0.0f, 0.0f );
		float scale = 1.0f / size;
		gl.glScalef( scale, 1.0f, 1.0f );
		gl.glScalef( 0.5f, 1.0f, 1.0f );
		for ( Integer i = 0; i <= size; ++i )
		{
			Integer value = mMin + i;
			gl.glColor4f(1.0f, 0.6f, 0.8f, 1.0f );
			gl.glTranslatef( 2.5f, 0.0f, 0.0f );
			gl.glDrawElements(gl.GL_TRIANGLE_STRIP, 4, gl.GL_UNSIGNED_BYTE, mIndexBuffer);
		}
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
			mBars.set( i, 0 );
		}
	}
	private ArrayList<Integer>		mBars = new ArrayList<Integer>();
	private Integer					mMin = 2;
	private Integer					mMax = 12;
    private IntBuffer   mVertexBuffer;
    private ByteBuffer  mIndexBuffer;
}
