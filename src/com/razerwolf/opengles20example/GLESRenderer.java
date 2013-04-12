package com.razerwolf.opengles20example;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class GLESRenderer implements Renderer {
	SimpleShader shader;
	Model sphere;
	Context context;
	float rotation = 0;
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		Matrix.setIdentityM(shader.modelMatrix, 0);
		rotation += 2;
		Matrix.translateM(shader.modelMatrix, 0, 0, 0, -5);
		Matrix.rotateM(shader.modelMatrix, 0, rotation, 0, 1, 0);
		Matrix.translateM(shader.modelMatrix, 0, 0, 0, 5);
		
		shader.draw(sphere);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float aspect = (float)width / (float)height;
		float fW, fH;
		float fov = 45.0f;
		float zNear = 1, zFar = 500;
		fH = (float)(Math.tan(Math.toRadians(45)*zNear));
		fW = aspect * fH;
		Matrix.frustumM(shader.projectionMatrix, 0, -fW, fW, -fH, fH, zNear, zFar);
		//Matrix.perspectiveM(shader.projectionMatrix, 0, 45.0f,aspect, 1, 500);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		shader = new SimpleShader();
		GLES20.glClearColor(0, 0, 0, 1);
		Matrix.setLookAtM(shader.viewMatrix, 0, 0, 0, -5, 
												0, 0,  0, 
												0, 1,  0);
		sphere = new GLSphere(50,50,2);
	}
	public GLESRenderer(Context c){
		context = c;
	}

}
