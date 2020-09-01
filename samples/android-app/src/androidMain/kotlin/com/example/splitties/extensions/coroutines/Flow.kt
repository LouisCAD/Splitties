/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import splitties.coroutines.repeatWhileActive

@ExperimentalCoroutinesApi
fun <E> infiniteFlow(generateValue: suspend () -> E): Flow<E> = flow {
    repeatWhileActive {
        emit(generateValue())
    }
}
