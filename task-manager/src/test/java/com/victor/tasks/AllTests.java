package com.victor.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ com.victor.tasks.App.class })
public class AllTests {

    @Test
    public void testApp() {
        assertTrue(true);
    }
}