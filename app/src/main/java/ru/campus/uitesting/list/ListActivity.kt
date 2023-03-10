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
package ru.campus.uitesting.list

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_list.*
import ru.campus.uitesting.R
import java.util.*

/**
 * Shows a list using a RecyclerView.
 */
class ListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        // Create a RecyclerView, a LayoutManager, a data Adapter and wire everything up.
        val dataSet: MutableList<String> =
            ArrayList(DATASET_COUNT)
        for (i in 0 until DATASET_COUNT) {
            dataSet.add(getString(R.string.item_element_text) + i)
        }
        val adapter = CustomAdapter(dataSet)
        recyclerView.adapter = adapter
    }

    companion object {
        private const val DATASET_COUNT = 50
    }
}