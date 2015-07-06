package code.algorithm;

import org.ija.tools.tts.SAPI5Player;
import org.mt4j.util.math.Vector3D;

import code.model.ApplicationContext;

public class XYGuidanceAlgorithm extends Thread {

	private boolean isRunning = false;


	private Vector3D target;

	public void setTarget(Vector3D target) {
		this.target = target;
	}

	private Vector3D previousFinger;

	public XYGuidanceAlgorithm() {
		super();
	}

	public void run() {

		while (true) {

			try {

				if (isRunning) {

					Vector3D finger = ApplicationContext
							.getCurrentFingerPosition();
					System.out.println("Finger " + finger);
					System.out.println("PreviousFinger " + previousFinger);
					if (finger != null && (previousFinger == null || !finger.equalsVector(previousFinger))) {

						// check X
						if (Math.abs(finger.x - target.x) > 40) {
							if ((finger.x - target.x) > 0) {
								SAPI5Player.getInstance()
										.speakIfReady("Gauche");
							} else {
								SAPI5Player.getInstance()
										.speakIfReady("Droite");
							}
						}

						// check Y
						else if (Math.abs(finger.y - target.y) > 40) {
							if ((finger.y - target.y) > 0) {
								SAPI5Player.getInstance().speakIfReady("Haut");
							} else {
								SAPI5Player.getInstance().speakIfReady("Bas");
							}
						}

						else {
							SAPI5Player.getInstance().play(
									"Bravo vous avez trouvé!");
							isRunning = false;
						}
						previousFinger = finger;
					}
				}
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}

		}
	}
	
	public void startRunning() {
		isRunning = true;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void stopRunning() {
		isRunning = false;
	}
}
