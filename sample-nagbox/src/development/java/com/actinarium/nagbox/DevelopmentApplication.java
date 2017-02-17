/*
 * Copyright (C) 2016 Actinarium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actinarium.nagbox;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Enable Stetho during development
 *
 * @author Paul Danyliuk
 */
public class DevelopmentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Love Stetho. Learn more here: https://facebook.github.io/stetho/
        Stetho.initializeWithDefaults(this);
    }

}
