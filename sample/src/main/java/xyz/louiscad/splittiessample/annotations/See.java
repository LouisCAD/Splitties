/*
 * Copyright (c) 2016. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.louiscad.splittiessample.annotations;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Add this annotation for readable documentation purposes.
 */

@Documented
@Retention(SOURCE)
@Target(ElementType.TYPE)
public @interface See {
    /**
     * Is <code>true</code> if the annotated class should not be used outside of the provided
     * {@link #layoutRes()}. <br/>
     * <b>Use only on a layout related class.</b>
     */
    boolean layoutResUseOnly() default false;
    /**
     * The layout resource id, if used in a layout related class. <br/>
     * Enables cmd/ctrl+click on the value to jump to the xml file.
     */
    @LayoutRes int layoutRes() default 0;
}
