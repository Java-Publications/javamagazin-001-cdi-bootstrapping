/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fx;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.demo.javamagazin.fx.CDIStartupScene;
import org.rapidpm.demo.javamagazin.fx.FXMLLoaderSingleton;

/**
 * User: Sven Ruppert
 * Date: 24.07.13
 * Time: 11:37
 */
public abstract class JavaFXBaseTest {


    @Before
    public void beforeTest() {
    }

    @Test
    //@Ignore
    public void testGo() {
        Application.launch(JavaFXTestApplication.class, "Go Test Go");
    }

    protected abstract Class<? extends JavaFXBaseTest> getTestClass();

    @After
    public void afterTest() {
    }


    public static abstract class JavaFXBaseTestImpl {

        public abstract boolean isExitAfterTest();

        protected abstract Class<? extends JavaFXBaseTest> getParentTestClass();


        @Inject
        public FXMLLoaderSingleton fxmlLoaderSingleton;

        public void launchJavaFXApplication(@Observes @CDIStartupScene Stage stage) {
            testImpl(stage);
            if (isExitAfterTest()) {
                stage.close();
                Platform.exit();
            } else {
                stage.show();
            }
        }

        public abstract void testImpl(final Stage stage);
    }
}
