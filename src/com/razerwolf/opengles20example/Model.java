package com.razerwolf.opengles20example;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public abstract class Model {
	FloatBuffer vertexBuffer;
	FloatBuffer normalBuffer;
	ShortBuffer indexBuffer;
	FloatBuffer texCoordBuffer;
	int num_indices;
}
