/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.libs.vs.common.utils;

/**
 * Utility class providing static methods handle classes not specified in the source code
 * @author Pietro G. Giardina
 */
public class DynamicClassManager {

    /**
     * Instantiates an object whose type is specified by a Class Name as a String
     * @param className Type (name of the class) of the object to be instantiated
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object instantiateFromString(String className) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        return Class.forName(className).newInstance(); //TODO check security implications

    }

    /**
     * Check if an object is an instance of a given class (or its subclass)
     * @param o Object to be checked
     * @param type Class
     * @return
     * @throws ClassNotFoundException
     */
    public static boolean checkObjectType(Object o, Class<?> type) throws ClassNotFoundException{
        return  Class.forName(type.getName()).isInstance(o);
    }

    /**
     * Check if a class (loaded by its name in String) is a subclass of another one
     * @param classToBeChecked Name of the class to be checked (String)
     * @param type Matching class
     * @return
     * @throws ClassNotFoundException
     */
    public static boolean checkClassType(String classToBeChecked, Class<?> type) throws ClassNotFoundException {
        return type.isAssignableFrom(Class.forName(classToBeChecked)); //Class.forName(classToBeChecked).isAssignableFrom(type);

    }
}
