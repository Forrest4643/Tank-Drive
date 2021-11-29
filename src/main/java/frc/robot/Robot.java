// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  //com port 1 = xbox controller 
  private final XboxController controller = new XboxController(1);

  //defining motor names
  private final CANSparkMax leftFront = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax leftRear = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax rightRear = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax rightFront = new CANSparkMax(4, MotorType.kBrushless);

  //setting speed controller groups
  private final SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftFront, leftRear);
  private final SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightFront, rightRear);

  public final DifferentialDrive robotDrive = new DifferentialDrive(leftDrive, rightDrive);
  
  //defining leftbumper
  public JoystickButton leftBumper = new JoystickButton(controller, 5);  

   //defining navx
   AHRS ahrs;
  


  /** 
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    leftFront.setInverted(false);
    leftRear.setInverted(false);
    rightFront.setInverted(false);
    rightRear.setInverted(false);

    leftDrive.setInverted(false);
    rightDrive.setInverted(false);

    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();

    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    boolean quickTurn = leftBumper.get();

    //cheesydrive
    //axis 1 = left stick y, axis 2 = right stick x
    //if right stick x centered
    if (controller.getRawAxis(4) > -0.04 &&  controller.getRawAxis(4) < 0.04){
      
    }


    robotDrive.curvatureDrive(controller.getRawAxis(1), controller.getRawAxis(4), quickTurn);

       //prints right stick x value
       SmartDashboard.putNumber("RS_X", controller.getRawAxis(4));

       //prints left stick y value
       SmartDashboard.putNumber("LS_Y", controller.getRawAxis(1));
   
       //prints Navx yaw value
      //  SmartDashboard.putNumber("navX yaw", ahrs.getYaw());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
 
  
  }
}
