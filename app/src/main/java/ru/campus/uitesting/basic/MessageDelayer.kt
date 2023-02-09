/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.campus.uitesting.basic

import android.os.Handler
import ru.campus.uitesting.idlingresource.SimpleIdlingResource

/**
 * Takes a String and returns it after a while via a callback.
 *
 *
 * This executes a long-running operation on a different thread that results in problems with
 * Espresso if an [IdlingResource] is not implemented and registered.
 */
internal object MessageDelayer {
    private const val DELAY_MILLIS = 3000L

    /**
     * Takes a String and returns it after [.DELAY_MILLIS] via a [DelayerCallback].
     * @param message the String that will be returned via the callback
     * @param callback used to notify the caller asynchronously
     */
    fun processMessage(
        message: String,
        idlingResource: SimpleIdlingResource?,
        onDone: (text: String) -> Unit
    ) { // The IdlingResource is null in production.
        idlingResource?.setIdleState(false)
        // Delay the execution, return message via callback.
        val handler = Handler()
        handler.postDelayed({
            onDone(message)
            idlingResource?.setIdleState(true)
        }, DELAY_MILLIS)
    }
}
