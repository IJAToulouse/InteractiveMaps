package org.ija.proto.qrcode;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class SimpleReader {

	public static void main(String[] args) throws IOException {

		// Webcam webcam = Webcam.getDefault(); // non-default (e.g. USB) webcam
		// can be used too
		// webcam.open();

		Result result = null;
		
		Image image = ImageIO.read(new File("test.png"));
		BufferedImage buffered = (BufferedImage) image;

		// if (webcam.isOpen()) {
		// if ((image = webcam.getImage()) == null) {
		// continue;
		// }

		LuminanceSource source = new BufferedImageLuminanceSource(buffered);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		try {
			result = new MultiFormatReader().decode(bitmap);
		} catch (NotFoundException e) {
			// fall thru, it means there is no QR code in image
		}
		// }

		if (result != null) {
			System.out.println("QR code data is: " + result.getText());
		}

	}

}
