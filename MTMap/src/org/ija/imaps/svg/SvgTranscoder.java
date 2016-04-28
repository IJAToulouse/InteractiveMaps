package org.ija.imaps.svg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;

public class SvgTranscoder {

	public static void transcode(File input, String mime) throws TranscoderException, IOException {

		Transcoder transcoder = getTranscoder(mime);

		TranscoderInput tInput = new TranscoderInput(input.toURI().toString());

		// Create the transcoder output.
		OutputStream ostream = new FileOutputStream(input.getPath().replace(
				".svg", "." + mime));
		TranscoderOutput tOutput = new TranscoderOutput(ostream);

		// Save the image.
		transcoder.transcode(tInput, tOutput);

		// Flush and close the stream.
		ostream.flush();
		ostream.close();

	}

	private static Transcoder getTranscoder(String mime) {
		if (mime.equals("png"))
			return new PNGTranscoder();
		else if (mime.equals("pdf"))
			return new PDFTranscoder();
		return null;
	}

	public static void main(String[] args) throws Exception {

		SvgTranscoder.transcode(new File(
				"C:\\Users\\g.denis.CESDV\\Downloads\\batik-1.7.1\\batik-1.7.1\\samples\\london.svg"), "png");
		
		SvgTranscoder.transcode(new File(
				"C:\\Users\\g.denis.CESDV\\Downloads\\batik-1.7.1\\batik-1.7.1\\samples\\london.svg"), "pdf");

		System.exit(0);
	}

}
