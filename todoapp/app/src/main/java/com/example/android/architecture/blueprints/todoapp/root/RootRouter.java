package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import android.view.MenuItem;
import com.example.android.architecture.blueprints.todoapp.ViewRouterExtension;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerRouter;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsBuilder;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowRouter;
import timber.log.Timber;

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class RootRouter extends ViewRouterExtension<RootView, RootInteractor, RootBuilder.Component> {

    private final TaskFlowBuilder taskFlowBuilder;
    private final MenuDrawerBuilder menuDrawerBuilder;
    private final StatisticsBuilder statisticsBuilder;
    @Nullable private StatisticsRouter statisticsRouter;
    @Nullable TaskFlowRouter taskFlowRouter;

    RootRouter(RootView view, RootInteractor interactor, RootBuilder.Component component, TaskFlowBuilder tasksBuilder,
        MenuDrawerBuilder menuDrawerBuilder, StatisticsBuilder statisticsBuilder) {
        super(view, interactor, component);
        this.taskFlowBuilder = tasksBuilder;
        this.menuDrawerBuilder = menuDrawerBuilder;
        this.statisticsBuilder = statisticsBuilder;
    }

    @Override
    public boolean handleOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getView().openMenu();
            return true;
        }
        return super.handleOptionsItemSelected(item);
    }

    void attachTasks() {
        Timber.d("attachTasks() called");
        taskFlowRouter = taskFlowBuilder.build();
        attachChild(taskFlowRouter);
    }

    void detachTasks() {
        Timber.d("detachTasks() called");
        if (taskFlowRouter != null) {
            detachChild(taskFlowRouter);
            getView().viewContainer().removeAllViews();
            taskFlowRouter = null;
        }
    }

    void attachMenuDrawer() {
        Timber.d("attachMenuDrawer() called");
        // The menu drawer is attached to the root view directly and not to the
        MenuDrawerRouter menuDrawerRouter = menuDrawerBuilder.build(getView());
        attachChild(menuDrawerRouter);
        getView().addView(menuDrawerRouter.getView());
    }

    void attachStatistics() {
        Timber.d("attachStatistics() called");
        statisticsRouter = statisticsBuilder.build(getView().viewContainer());
        attachChild(statisticsRouter);
        getView().viewContainer().addView(statisticsRouter.getView());
    }

    void detachStatistics() {
        Timber.d("detachStatistics() called");
        if (statisticsRouter != null) {
            detachChild(statisticsRouter);
            getView().viewContainer().removeAllViews();
            statisticsRouter = null;
        }
    }

    public final boolean handleHomeItemSelected() {
        getView().openMenu();
        return true;
    }

    boolean dispatchBackPress() {
        return taskFlowRouter != null && taskFlowRouter.getInteractor().handleBackPress();
    }
}
