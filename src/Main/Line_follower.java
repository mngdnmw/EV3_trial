package Main;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;

public class Line_follower implements KeyListener {
	private boolean wantToExit = false;
	private BaseRegulatedMotor innerWheel = null;
	private BaseRegulatedMotor outerWheel = null;
	private BaseRegulatedMotor innerHand = null;
	private BaseRegulatedMotor outerHand = null;
	private EV3ColorSensor sensorColor = null;
	private static final int SPEED = 200;
	private int light = 0;

	public static void main(String[] args) {
		Line_follower lineFollow = new Line_follower();
		lineFollow.go();
	}

	public Line_follower() {
		System.out.println("Press any key to get started!");
		Button.waitForAnyPress();
	}

	private void go() {

		EV3 brick = LocalEV3.get();

		try (EV3LargeRegulatedMotor leftHand = new EV3LargeRegulatedMotor(brick.getPort("C"));
				EV3LargeRegulatedMotor rightHand = new EV3LargeRegulatedMotor(brick.getPort("D"));
				EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(brick.getPort("A"));
				EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(brick.getPort("B"));
				EV3ColorSensor colorSensor = new EV3ColorSensor(brick.getPort("S4"))) {
			innerWheel = leftMotor;
			outerWheel = rightMotor;
			innerHand = leftHand;
			outerHand = rightHand;
			sensorColor = colorSensor;
			startThread();
			while (!wantToExit) {
				rightHand.rotate(45);
				rightHand.rotate(-45);
				leftHand.rotate(-45);
				leftHand.rotate(45);
				followLine();
			}
			

		} catch (Exception e) {
			System.out.println("Not all ports found/initialised!!");
		}

	}

	private void rightTurn() {
		outerWheel.rotate(1);
	}

	private void leftTurn() {
		innerWheel.rotate(1);
	}

	private void followLine() {
		innerWheel.synchronizeWith(new BaseRegulatedMotor[] { outerWheel });
		logic();
	}

	private void startThread() {
		Thread thread = new Thread() {
			public void run() {
				while (1 < 2) {
					float[] sample = new float[sensorColor.getRedMode().sampleSize()];
					sensorColor.getRedMode().fetchSample(sample, 0);
					light = (int) (100 * sample[0]);
				}
			}
		};
		thread.start();
	}

	private void goForward() {

		innerWheel.startSynchronization();
		innerWheel.setSpeed(SPEED);
		outerWheel.setSpeed(SPEED-10);
		innerWheel.forward();
		outerWheel.forward();
		innerWheel.endSynchronization();
		

	}
	public void logic() {

		goForward();
		while(light > 20) {
			leftTurn();
			System.out.println("Intensity: " + light);
		}
		

	}

	@Override
	public void keyPressed(Key k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(Key k) {
		System.out.println("Stopping...");
		wantToExit = true;
	}

}
