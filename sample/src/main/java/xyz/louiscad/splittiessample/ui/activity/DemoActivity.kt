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

package xyz.louiscad.splittiessample.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_INDEFINITE
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_demo.*
import xyz.louiscad.splittiessample.R
import xyz.louiscad.splittiessample.ui.adapter.DemoAdapter
import xyz.louiscad.splittiessample.ui.model.DemoItem
import java.lang.Integer.MAX_VALUE

class DemoActivity : AppCompatActivity(), DemoAdapter.DemoViewHolder.Host {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DemoActivity)
            adapter = DemoAdapter(this@DemoActivity)
        }
        fab.setOnClickListener { v ->
            Snackbar.make(v, R.string.title_feature_not_available, LENGTH_SHORT)
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(snackbar: Snackbar?, event: Int) {
                            Snackbar.make(v, R.string.msg_go_to_pc_manually, LENGTH_INDEFINITE).show()
                        }
                    }).show()
        }
    }

    override fun onDemoItemClicked(demoItem: DemoItem) {
        Snackbar.make(coordinator, R.string.msg_marketing_guy_invents_new_feature, LENGTH_INDEFINITE)
                .setAction(R.string.scroll_to_the_end) { recyclerView.scrollToPosition(MAX_VALUE - 1) }
                .show()
    }
}
