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

package com.actinarium.nagbox.common;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Paul Danyliuk
 */
public final class ReaderUtils {

    private ReaderUtils() {}

    /**
     * Reads lines from input stream. Doesn’t close the stream &mdash; you have to close it yourself.
     *
     * @param inputStream Input stream
     * @return List of lines read from the stream
     * @see #readLines(Context, int)
     */
    public static List<String> readLines(InputStream inputStream) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
        ArrayList<String> readLines = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                readLines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readLines;
    }

    /**
     * Reads lines from given raw resource. Opens the file, reads the lines with {@link #readLines(InputStream)} and
     * closes the stream afterwards.
     *
     * @param context  Context to retrieve the resource
     * @param rawResId Raw resource ID, which is a name of a file in <code>/res/raw</code> folder without extension
     * @return List of lines read from the file
     */
    public static List<String> readLines(Context context, @RawRes int rawResId) {
        final InputStream inputStream = context.getResources().openRawResource(rawResId);
        try {
            return readLines(inputStream);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Fail quietly
            }
        }
    }

}
