package org.ija.imaps.camera;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class CameraReader {

	boolean decode = false;
	Webcam webcam = Webcam.getDefault();
	
	public CameraReader() {
		
	}
	
	public String decode() {

		Boolean decode = false;

		Webcam webcam = Webcam.getDefault(); // non-default (e.g. USB) webcam
												// can be used too
		webcam.open();

		Result result = null;
		BufferedImage image = null;

		while (webcam.isOpen() && !decode) {
			if ((image = webcam.getImage()) == null) {
				continue;
			}

			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			try {
				result = new MultiFormatReader().decode(bitmap);
				decode = true;
			} catch (NotFoundException e) {
				System.out.println("Tentative échouée");
			}
		}
		webcam.close();

		if (result != null) {
			return result.getText();
		}
		return null;
	}
}
