package com.razerwolf.opengles20example;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;
import java.util.Vector;



public class GLSphere extends Model {
	
	GLSphere(int latitudeBands, int longitudeBands, float radius){
		Vector<Float> vertexData = new Vector<Float>();
		Vector<Float> normalData = new Vector<Float>();
		Vector<Float> texCoordData = new Vector<Float>();
		Vector<Integer> indexData = new Vector<Integer>();
		
		for(int latNumber = 0; latNumber <= latitudeBands; latNumber++){
			float theta = (float)latNumber * (float)Math.PI/(float)latitudeBands;
			float sinTheta = (float) Math.sin(theta);
			float cosTheta = (float) Math.cos(theta);
			
			for(int longNumber = 0; longNumber <= longitudeBands; longNumber++){
				float phi = (float)longNumber * 2f * (float)Math.PI/(float)longitudeBands;
				
				float sinPhi = (float)Math.sin(phi);
				float cosPhi = (float)Math.cos(phi);
			
				float x = sinTheta * cosPhi;
				float y = cosTheta;
				float z = sinPhi * sinTheta;
				
				float u = 1 - ((float)longNumber / (float)longitudeBands);
				float v = 1- ((float)latNumber / (float)latitudeBands);
				
				normalData.add(x);
				normalData.add(y);
				normalData.add(z);
				texCoordData.add(u);
				texCoordData.add(v);
				vertexData.add(x * radius);
				vertexData.add(y * radius);
				vertexData.add(z * radius);
			}
			
		}
		

		for(int latNumber = 0; latNumber <= latitudeBands; latNumber++){
			for(int longNumber = 0; longNumber <= longitudeBands; longNumber++){
				int first = (latNumber * (longitudeBands+1))+longNumber;
				int second = first + longitudeBands + 1;
				
				indexData.add(first);
				indexData.add(second);
				indexData.add(first + 1);
				
				indexData.add(second);
				indexData.add(second + 1);
				indexData.add(first + 1);
				
			}
		}
		//Now, add them all to a bytebuffer and what not
		ByteBuffer bb = ByteBuffer.allocateDirect(vertexData.size()*Float.SIZE);
		bb.order(ByteOrder.nativeOrder());
		this.vertexBuffer = bb.asFloatBuffer();
		for(Float f : vertexData){
			this.vertexBuffer.put(f.floatValue());
		}
		this.vertexBuffer.position(0);
		
		bb = ByteBuffer.allocateDirect(texCoordData.size()*Float.SIZE);
		bb.order(ByteOrder.nativeOrder());
		this.texCoordBuffer = bb.asFloatBuffer();
		for(Float f : texCoordData){
			this.texCoordBuffer.put(f.floatValue());
		}
		this.texCoordBuffer.position(0);
		
		bb = ByteBuffer.allocateDirect(normalData.size()*Float.SIZE);
		bb.order(ByteOrder.nativeOrder());
		this.normalBuffer = bb.asFloatBuffer();
		for(Float f : normalData){
			this.normalBuffer.put(f.floatValue());
		}
		this.normalBuffer.position(0);

		bb = ByteBuffer.allocateDirect(indexData.size() * Short.SIZE);
		bb.order(ByteOrder.nativeOrder());
		this.indexBuffer = bb.asShortBuffer();
		for(Integer f : indexData){
		
			this.indexBuffer.put(f.shortValue());
		}
		this.indexBuffer.position(0);
		num_indices = indexData.size();
		//Log.d("WEEE", num_indices +" "+(indices.capacity()/2));
	}

}
