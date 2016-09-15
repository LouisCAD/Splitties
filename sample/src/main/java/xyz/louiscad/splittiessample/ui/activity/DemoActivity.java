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

package xyz.louiscad.splittiessample.ui.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.splittiessample.R;
import xyz.louiscad.splittiessample.ui.adapter.DemoAdapter;
import xyz.louiscad.splittiessample.ui.model.DemoItem;
import xyz.louiscad.splittiessample.ui.widget.DemoListItem;

import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static java.lang.Integer.MAX_VALUE;

@EActivity(R.layout.activity_demo)
public class DemoActivity extends AppCompatActivity implements DemoListItem.Host {

    @ViewById CoordinatorLayout coordinator;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById
    public RecyclerView recyclerView;

    @AfterViews
    void initViews() {
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new DemoAdapter(this));
    }

    @Click
    void fabClicked(final View v) {
        Snackbar.make(v, R.string.title_feature_not_available, LENGTH_SHORT)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        Snackbar.make(v, R.string.msg_go_to_pc_manually, LENGTH_INDEFINITE).show();
                    }
                }).show();
    }

    @Override
    public void onDemoItemClicked(DemoItem demoItem) {
        Snackbar.make(coordinator, R.string.msg_marketing_guy_invents_new_feature, LENGTH_INDEFINITE)
                .setAction(R.string.scroll_to_the_end, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.scrollToPosition(MAX_VALUE - 1);
                    }
                }).show();
    }
}
