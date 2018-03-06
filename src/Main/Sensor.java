package Main;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.utility.Delay;

public class Sensor {

	private static EV3 brick = (EV3) BrickFinder.getDefault();

	private BaseRegulatedMotor innerWheel = null;
	private BaseRegulatedMotor outerWheel = null;
	private BaseRegulatedMotor neck = null;
	private EV3UltrasonicSensor ultra = null;
	private EV3TouchSensor innerTouch = null;
	private EV3TouchSensor outerTouch = null;
	private float leftDist;
	private float rightDist;

	public static void main(String[] args) {

		System.out.println("UltraSonic Demo");
		System.out.println("Press any key to start");

		Button.LEDPattern(2); // flash green led and
		Sound.beepSequenceUp(); // make sound when ready.

		Button.waitForAnyPress();
		Sensor sensor = new Sensor();
		sensor.go();

	}

	private void go() {

		try (

				BaseRegulatedMotor leftWheel = new EV3LargeRegulatedMotor(brick.getPort("A"));
				BaseRegulatedMotor rightWheel = new EV3LargeRegulatedMotor(brick.getPort("B"));
				BaseRegulatedMotor neckMotor = new EV3LargeRegulatedMotor(brick.getPort("C"));
				EV3TouchSensor touchLeft = new EV3TouchSensor(brick.getPort("S2"));
				EV3TouchSensor touchRight = new EV3TouchSensor(brick.getPort("S3"));
				EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(brick.getPort("S4"));

		) {
			innerWheel = leftWheel;
			outerWheel = rightWheel;
			neck = neckMotor;
			innerTouch = touchLeft;
			outerTouch = touchRight;
			ultra = ultraSensor;
			RangeFinderAdapter rangeFinder = new RangeFinderAdapter(ultra);
			int frame = 0;
			while(true) {
			if(frame==10000) {
				System.out.println(""+rangeFinder.getRange());
				frame = 0;
				
			}
			if (rangeFinder.getRange() < 30) {
				stopMoving();
				turnHeadLeft(rangeFinder);
				turnHeadRight(rangeFinder);
				if(leftDist < 15 && rightDist < 15)
				{
					goBack(1000);
					turnHeadLeft(rangeFinder);
					turnHeadRight(rangeFinder);
					
				}
				if (leftDist > rightDist) {
					goLeft();
				} else if (leftDist < rightDist) {
					goRight();
				}

			} else {
				goStraight();
			}
			frame ++;
			}
		}

	}

	private void turnHeadLeft(RangeFinderAdapter rangeFinder) {

		neck.rotate(-90);
		leftDist = rangeFinder.getRange();
		System.out.println("range: " + leftDist);
		neck.rotate(90);

	}

	private void turnHeadRight(RangeFinderAdapter rangeFinder) {

		neck.rotate(90);
		rightDist = rangeFinder.getRange();
		System.out.println("range: " + rightDist);
		neck.rotate(-90);

	}
	private void stopMoving() {
		innerWheel.stop();
		outerWheel.stop();
	}

	private void goStraight() {
		innerWheel.forward();
		outerWheel.forward();
	}
	private void goBack(long msdelay) {
		innerWheel.backward();
		outerWheel.backward();
		Delay.msDelay(msdelay);
	}
	private void goLeft() {
		outerWheel.rotate(180);
	}

	private void goRight() {
		innerWheel.rotate(180);
		
	}
}
