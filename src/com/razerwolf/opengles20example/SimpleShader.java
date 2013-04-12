package com.razerwolf.opengles20example;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class SimpleShader extends Shader {
	float[] viewMatrix = new float[16];
	float[] projectionMatrix = new float[16];
	float[] modelMatrix = new float[16];
	
	final int uMVMatrixHandle;
	final int uPMatrixHandle;
	
	final int aVertexHandle;
	final int aNormalHandle;

	final String vertexShader =
			"attribute vec4 vertex;\n"+
			"attribute vec3 normal;\n"+
			"\n"+
			"uniform mat4 mvMatrix;\n"+
			"uniform mat4 pMatrix;\n"+
			"\n"+
			"varying vec4 color;\n"+
			"\n"+
			"void main(){\n" +
			"   vec3 lightPosition = vec3(3,0,-3);\n"+
			"   vec3 nLight = normalize(lightPosition);\n"+
			"   float diffuse = max(dot(nLight, normal), 0.1);\n"+
			"   color=vec4(1,0,0,1) * diffuse;\n"+
			"	gl_Position = pMatrix * mvMatrix * vertex;\n"+
			"}";
	final String fragmentShader = 
			"precision mediump float;\n"+
			"varying vec4 color;\n"+
			"void main(){" +
			"	gl_FragColor = color;\n"+
			"}";
	SimpleShader(){
		program = loadProgram(vertexShader, fragmentShader);
		uMVMatrixHandle = GLES20.glGetUniformLocation(program, "mvMatrix");
		if(uMVMatrixHandle==0) Log.d("WEEE", ""+uMVMatrixHandle);
		uPMatrixHandle = GLES20.glGetUniformLocation(program, "pMatrix");
		if(uPMatrixHandle==0) Log.d("WEEE", ""+uPMatrixHandle);
		
		aVertexHandle = GLES20.glGetAttribLocation(program, "vertex");
		if(aVertexHandle==0) Log.d("WEEE", ""+aVertexHandle);
		aNormalHandle = GLES20.glGetAttribLocation(program, "normal");
		if(aNormalHandle==0) Log.d("WEEE", ""+aNormalHandle);
		
	}
	void draw(Model m){
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		int oldProgram = GLES20.GL_CURRENT_PROGRAM;
		GLES20.glUseProgram(program);
		GLES20.glEnableVertexAttribArray(aVertexHandle);
		GLES20.glEnableVertexAttribArray(aNormalHandle);
		
		GLES20.glVertexAttribPointer(aVertexHandle, 3, GLES20.GL_FLOAT, false, 0, m.vertexBuffer);
		GLES20.glVertexAttribPointer(aNormalHandle, 3, GLES20.GL_FLOAT, false, 0, m.normalBuffer);
		float []modelViewMatrix = new float[16];
		Matrix.multiplyMM(modelViewMatrix, 0, modelMatrix, 0, viewMatrix, 0);
		GLES20.glUniformMatrix4fv(uMVMatrixHandle, 1, false, modelViewMatrix,0);
		GLES20.glUniformMatrix4fv(uPMatrixHandle, 1, false, projectionMatrix,0);
		
		//GLES20.glDrawArrays(mode, first, count)
		//if(m.indexBuffer != null)
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, m.num_indices, GLES20.GL_UNSIGNED_SHORT, m.indexBuffer);
		//else
		//	GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, m.num_indices);
		
		GLES20.glDisableVertexAttribArray(aNormalHandle);
		GLES20.glDisableVertexAttribArray(aVertexHandle);
		GLES20.glUseProgram(oldProgram);
	}
}
