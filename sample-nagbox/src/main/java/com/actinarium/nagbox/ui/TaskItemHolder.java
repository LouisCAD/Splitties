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

package com.actinarium.nagbox.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import com.actinarium.nagbox.R;
import com.actinarium.nagbox.database.Projection;
import com.actinarium.nagbox.databinding.TaskItemBinding;
import com.actinarium.nagbox.model.Task;

/**
 * View holder for a single task item
 *
 * @author Paul Danyliuk
 */
public class TaskItemHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private final TaskItemBinding mBinding;
    private final Task mTask;
    private final Context mContext;
    private final Host mHost;

    public TaskItemHolder(TaskItemBinding binding, Host host) {
        super(binding.getRoot());
        mBinding = binding;
        mContext = binding.getRoot().getContext();
        mBinding.setController(this);
        mHost = host;

        // Instead of creating new instances of Tasks each time, we will mutate one object
        mTask = new Task();
    }

    /**
     * Bind this view holder to the data from the cursor at given position using provided projection mapping.
     *
     * @param cursor     Cursor to bind data from
     * @param projection Projection object to map the cursor to the model object
     */
    public void bind(Cursor cursor, Projection<Task> projection) {
        projection.mapCursorToModel(cursor, mTask);
        mBinding.setTask(mTask);
        // Don't wait till the next frame. Without this you'll see switch animation when the app is started.
        mBinding.executePendingBindings();
    }

    @SuppressWarnings("unused")
    public void onClick(View v) {
        // Clicking the tile will toggle the switch. Then the switch listener will request model update
        mBinding.taskToggle.toggle();
    }

    @SuppressWarnings("unused")
    public void onTaskStatusChanged(boolean isActive) {
        // Tell the controller to set task status (idle/running) and schedule it for alarm
        mHost.onSetTaskStatus(new Task(mTask), isActive);
    }

    @SuppressWarnings("unused")
    public void onMenuClick(View actionMenuBtn) {
        // The action menu icon is the view passed here, so anchor to it
        PopupMenu menu = new PopupMenu(mContext, actionMenuBtn);
        menu.inflate(R.menu.menu_item_actions);
        menu.setOnMenuItemClickListener(this);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_edit:
                // Passing the copy of this mutable task to be on the safe side
                mHost.onEditTask(new Task(mTask));
                return true;
            case R.id.action_delete:
                // Since we have a Context here, we could call the service directly. But let's keep things consistent.
                mHost.onDeleteTask(new Task(mTask));
                return true;
        }
        return false;
    }

    /**
     * Callbacks to the host (i.e. activity) to handle things triggered from this view item
     */
    public interface Host {
        void onSetTaskStatus(Task task, boolean isActive);
        void onEditTask(Task task);
        void onDeleteTask(Task task);
    }
}
