package Main;

import lejos.hardware.sensor.*;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.TouchAdapter;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.*;
import lejos.utility.Delay;

public class Self_driving_tank implements KeyListener {
	public static EV3 brick = (EV3) BrickFinder.getDefault();
	public static RegulatedMotor left = new EV3LargeRegulatedMotor(brick.getPort(("A")));
	public static RegulatedMotor right = new EV3LargeRegulatedMotor(brick.getPort(("B")));
	private static EV3TouchSensor ts2 = new EV3TouchSensor(brick.getPort("S2"));
	private static EV3TouchSensor ts3 = new EV3TouchSensor(brick.getPort("S3"));
	public static boolean running = true;

	public static void main(String[] args) {

		System.out.println("Drive Forward\nand Stop\n");
		System.out.println("Press any key to start");

		Button.LEDPattern(4); // flash green led and
		Sound.beepSequenceUp(); // make sound when ready.

		Button.waitForAnyPress();
		
		TouchAdapter leftTouch = new TouchAdapter(ts2);
		TouchAdapter rightTouch = new TouchAdapter(ts3);

		// Button.UP.addKeyListener(new KeyListener() {
		// @Override
		// public void keyReleased(Key k) {
		// running = false;
		// left.stop();
		// right.stop();
		// System.exit(0);
		// }
		//
		// @Override
		// public void keyPressed(Key k) {
		// running = false;
		// left.stop();
		// right.stop();
		// System.exit(0);
		//
		// }
		// });
		
		while (running) {
			left.forward();
			right.forward();
				
			if (leftTouch.isPressed() || rightTouch.isPressed()) {
				
				Delay.msDelay(200);
				System.out.println("left "+left.getSpeed());
				System.out.println("right "+right.getSpeed());
				System.out.println("max "+right.getMaxSpeed());
				
				if (leftTouch.isPressed() && rightTouch.isPressed()) {
					left.backward();
					right.backward();
					Delay.msDelay(500);
					left.forward();
					Delay.msDelay(500);
					
				}
			}
			if (leftTouch.isPressed()) {
				left.forward();
				right.backward();
				Delay.msDelay(500);
			} else if (rightTouch.isPressed()) {
				right.forward();
				left.backward();
				Delay.msDelay(500);
			}
			
//			int button = 0;
//			button = Button.waitForAnyPress();
//			if(button == Button.ID_ENTER)onEnterPress();
//			if(button == Button.ID_LEFT)robotGoLeft();
//			if(button == Button.ID_RIGHT)robotGoRight();
			

		}

	}

	@Override
	public void keyPressed(Key k) {
		System.out.println("button pressed");
		running = false;
		left.stop();
		right.stop();
		System.exit(0);

	}

	@Override
	public void keyReleased(Key k) {
		// TODO Auto-generated method stub

	}

}
