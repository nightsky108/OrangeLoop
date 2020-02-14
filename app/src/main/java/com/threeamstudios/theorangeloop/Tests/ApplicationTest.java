package com.threeamstudios.theorangeloop.Tests;
/*import static org.junit.Assert.*;
import org.junit.Test;*/
import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
//@RunWith(AndroidJUnit4.class)
import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

    }

   /* @SmallTest
    public void UnitTest(Context context){

        DatabaseHandler x = DatabaseHandler.getInstance(context);
        assertNotNull(x);
    }*/


}