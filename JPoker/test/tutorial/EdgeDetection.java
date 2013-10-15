package tutorial;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class EdgeDetection {

	public static void main(String[] args) {
		float[] elements = { 0.0f, -1.0f, 0.0f,
				-1.0f, 4.f, -1.0f,
				0.0f, -1.0f, 0.0f};
//		BufferedImage bimg = new 
//				BufferedImage(bw,bh,BufferedImage.TYPE_INT_RGB);
		Kernel kernel = new Kernel(3, 3, elements);
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
				null);
//		cop.filter(bi,bimg);
	}
}
