package Main;
import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Twirler implements KeyListener {

	public static void main(String[] args) throws Exception {
		RegulatedMotor left = null;
		RegulatedMotor right = null;
		try {
			EV3 brick = (EV3) BrickFinder.getDefault();
			left = new EV3LargeRegulatedMotor(brick.getPort(("B")));
			right = new EV3LargeRegulatedMotor(brick.getPort(("C")));
			left.setAcceleration(500);
			right.setAcceleration(500);
			left.forward();
			while (true) {
				int speed1 = (int) (Math.random() * 800);
				int speed2 = (int) (Math.random() * 800);
				left.setSpeed(speed1);
				right.setSpeed(speed2);
				if (Math.random() < 0.5) {
					right.forward();
				} else {
					right.forward();
				}
				Delay.msDelay(5000);
			}
			
			
			
		} finally {
			left.close();
			right.close();
		}
		
		
	}

	@Override
	public void keyPressed(Key k) {
		System.out.println("key pressed");
		System.exit(0);
		
	}

	@Override
	public void keyReleased(Key k) {
		System.out.println("keyreleased");
		
	}
	

}
