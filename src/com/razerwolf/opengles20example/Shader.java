package com.razerwolf.opengles20example;
import android.opengl.GLES20;
import android.util.Log;


public abstract class Shader {
	int program;
	final String TAG = "Shader";
	protected int loadProgram(String vertexSource, String fragmentSource){
		int vertexShader = loadShader(vertexSource, GLES20.GL_VERTEX_SHADER);
		if(vertexShader == 0){
			return 0;
		}
		int fragmentShader = loadShader(fragmentSource, GLES20.GL_FRAGMENT_SHADER);
		if(fragmentShader == 0){
			return 0;
		}
		
		int program = GLES20.glCreateProgram();
		
		if(program != 0){
			GLES20.glAttachShader(program, vertexShader);
			GLES20.glAttachShader(program, fragmentShader);
			
			GLES20.glLinkProgram(program);
			
			int []linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			
			if(linkStatus[0] == GLES20.GL_FALSE){
				Log.e(TAG, "Couldn't link program!");
				Log.e(TAG, GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}
	
	protected int loadShader(String source, int type){
		int shader = GLES20.glCreateShader(type);
		if(shader != 0){
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			
			int compiled[] = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled,0);
			
			if(compiled[0] == 0){
				Log.e(TAG, "Couldn't compile the shader!");
				Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
			return shader;
		}
			
		return 0;
	}
}
