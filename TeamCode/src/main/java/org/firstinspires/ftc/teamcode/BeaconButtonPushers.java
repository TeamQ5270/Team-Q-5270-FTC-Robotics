package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;


public class BeaconButtonPushers extends BasicTankDrive {

  //Variable declerations
  int position = 0; //This is the position of the servo of the beaconButtonPusher
  public boolean extended = false; //This is the current position that the beaconButtonPusher is in. Note that false is not extended, and true is extended
  Servo beaconButtonPusherServo = null; //This is the servo that is the beaconButtonPusher
  int minPosition = 0; //This is the minimum position of the servo - defaults to 0
  int maxPosition = 150; //This is the maximum position of the servo - defaults to 150


  //This is the decleration method for the beaconButtonPusher class
  public BeaconButtonPushers(String name, int inMin, int inMax) { //name of the servo: minimum position: maximum position
    beaconButtonPusherServo = hardwareMap.servo.get(name); //Set the class's servo object to be the servo that is specified
    minPosition = inMin;
    maxPosition = inMax;
  }


  //This will toggle the beaconButtonPusher's position from extended to not extended, and vice versa. It will also return the position of the beaconButtonPusher
  public boolean togglePosition() {

    //If the beaconButtonPusher is already extended then unextend it
    if (extended) {
      //This is the for loop that will unextend the pusher
      for (int i = position; i>minPosition; i--) {
        //Set the position of the servo to be that of the for loop
        beaconButtonPusherServo.setPosition(i);
      }
    }

    //If the beaconButtonPusher is not extended
    else {
      //This is the for loop that will extend the pusher
      for (int i = position; i<maxPosition; i++) {
        //Set the position of the servo to be that of the foor loop
        beaconButtonPusherServo.setPosition(i);
      }
    }
    return true;
  }


}
