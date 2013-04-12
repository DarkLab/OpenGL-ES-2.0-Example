package com.razerwolf.opengles20example;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLESSurfaceView extends GLSurfaceView {

	public GLESSurfaceView(Context context) {
		super(context);
		this.setEGLContextClientVersion(2);
		this.setRenderer(new GLESRenderer(context));
		
	}

}
