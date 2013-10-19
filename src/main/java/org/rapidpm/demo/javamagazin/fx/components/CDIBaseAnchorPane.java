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

package org.rapidpm.demo.javamagazin.fx.components;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.rapidpm.demo.javamagazin.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.javamagazin.fx.FXMLLoaderSingleton;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 07:03
 */
public abstract class CDIBaseAnchorPane<T, C extends CDIJavaFxBaseController> extends AnchorPane implements CDIBaseFxComponent<T> {

    public CDIBaseAnchorPane() {

    }

    public
    @Inject
    FXMLLoaderSingleton fxmlLoaderSingleton;
    public C controller;


    @PostConstruct
    public void init() {
        final FXMLLoader fxmlLoader = fxmlLoaderSingleton.getFXMLLoader(getPaneClass());
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public C getController() {
        return controller;
    }

    public void setController(C controller) {
        this.controller = controller;
    }
}
