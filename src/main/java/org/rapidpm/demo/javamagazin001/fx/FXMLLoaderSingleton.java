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

package org.rapidpm.demo.javamagazin001.fx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

/**
 * User: Sven Ruppert
 * Date: 08.07.13
 * Time: 16:38
 */
@Singleton
public class FXMLLoaderSingleton {


    private
    @Inject
    Instance<CDIJavaFxBaseController> instance;

    private final ClassLoader cachingClassLoader = new FXClassLoader(FXMLLoader.getDefaultClassLoader());
    private final Map<Class, FXMLLoader> class2LoaderMap = new HashMap<Class, FXMLLoader>();

    public FXMLLoader getFXMLLoader(Class clazz) {
        final Map<Class, FXMLLoader> loaderMap = class2LoaderMap;
        if (loaderMap.containsKey(clazz)) {
        } else {
            final String fxmlFileName = clazz.getSimpleName() + ".fxml";
            final URL resource = clazz.getResource(fxmlFileName);
            FXMLLoader loader = new FXMLLoader(resource);
            loader.setClassLoader(cachingClassLoader);
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    final Class<CDIJavaFxBaseController> p = (Class<CDIJavaFxBaseController>) param;
                    final CDIJavaFxBaseController controller = instance.select(p).get();
                    return controller;
                }
            });
            try {
                final Class<?> aClass = Class.forName(clazz.getName() + "Controller");
                final CDIJavaFxBaseController call = (CDIJavaFxBaseController) loader.getControllerFactory().call(aClass);
                loader.setController(call);
            } catch (ClassNotFoundException e) {
                //logger.error(e);
            }
            loaderMap.put(clazz, loader);
        }
        return loaderMap.get(clazz);
    }

    private FXMLLoaderSingleton() {
    }
}
