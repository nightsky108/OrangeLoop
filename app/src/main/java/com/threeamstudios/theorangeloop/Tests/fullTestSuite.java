package com.threeamstudios.theorangeloop.Tests;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by Kevin on 4/28/2016.
 */
public class fullTestSuite extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(fullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public fullTestSuite() {
        super();

    }
}
