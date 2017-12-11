package com.example.android.architecture.blueprints.todoapp.root.new_task;

import android.support.annotation.Nullable;

import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Coordinates Business Logic for {@link NewTaskBuilder.NewTaskScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class NewTaskInteractor
        extends Interactor<NewTaskInteractor.NewTaskPresenter, NewTaskRouter> {

    @Inject NewTaskPresenter presenter;
    @Inject Listener listener;

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);
        presenter.clicks()
            .subscribe(o -> listener.addTask());
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }


    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface NewTaskPresenter {
        Observable<Object> clicks();
    }

    public interface Listener {
        void addTask();
    }
}
