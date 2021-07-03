/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.provider.Property

infix fun <T> Property<T>.by(value: T) {
    set(value)
}
