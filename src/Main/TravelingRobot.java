package Main;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
//import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;

public class TravelingRobot {
	private static int WHEELOFFSET = 5;
	private static float DELTA_WHEEL;
	public static void main(String[] args) {
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, MovePilot.WHEEL_SIZE_EV3).offset(-WHEELOFFSET);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.B, MovePilot.WHEEL_SIZE_EV3).offset(WHEELOFFSET);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		OdometryPoseProvider placement = new OdometryPoseProvider(pilot);
		pilot.setLinearSpeed(10); // cm per second
		Navigator nav = new Navigator(pilot);
		nav.goTo(100, -100, 180);
		while(nav.isMoving())Thread.yield();
		System.out.println("("+ placement.getPose().getX()+","+placement.getPose().getY()+") Heading: " +placement.getPose().getHeading());

//		pilot.travel(100); // cm
//		pilot.rotate(-90); // degree clockwise
//		pilot.travel(100,true); // move backward for 50 cm
//		while(pilot.isMoving())Thread.yield();
//		pilot.rotate(-90);
//		System.out.println("("+ placement.getPose().getX()+","+placement.getPose().getY()+") Heading: " +placement.getPose().getHeading());
//		
//		pilot.stop();
//		DifferentialPilot pilot = new DifferentialPilot(3.1, 17.5, Motor.A, Motor.B);
//		pilot.travel(20, true);
//		pilot.rotate(90);
//		while(pilot.isMoving()) {
//			if(Button.ESCAPE.isDown())
//				pilot.stop();
//			Button.waitForAnyPress();
//		}
	}

}
