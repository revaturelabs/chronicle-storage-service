package com.revature.chronicle.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NoteControllerTests.class, S3ControllerTest.class, VideoControllerTests.class })
public class ControllerTestSuite {

}
