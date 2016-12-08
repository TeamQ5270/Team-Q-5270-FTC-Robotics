package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Demonstrates empty OpMode
 */
@Autonomous(name = "MotorVaryingSpeedTest", group = "Concept")
public class MotorVaryingSpeedTest extends LinearOpMode {

  private ElapsedTime runtime = new ElapsedTime();

  DcMotor motor = null;

  boolean run;

  /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */

  /*
   * This method will be called ONCE when start is pressed
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void runOpMode() throws InterruptedException {
    motor = hardwareMap.dcMotor.get("Testing Motor");
    telemetry.addData("Status", "Initialized");

    for (float speed = 0; speed < 1; speed += 0.2) {
      motor.setPower(speed);
      try {
        idle();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    run = false;
    motor.setPower(0);

    while (opModeIsActive()) {
      telemetry.addData("Status", "Run Time: " + runtime.toString());
      idle();
    }
  }
}
