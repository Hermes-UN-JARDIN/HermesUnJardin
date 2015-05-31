package com.hermes_un_jardin.hermesunjardin.presenter;

import com.hermes_un_jardin.hermesunjardin.model.Idea;
import com.hermes_un_jardin.hermesunjardin.view.MainActivityView;

/**
 * Created by song on 2015/5/31.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainActivityView mView;
    private Idea mModel;

    public MainPresenterImpl(MainActivityView view, Idea model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onDrawerSelect(int position) {
        String ideaName = "";       // TODO
        Idea idea = Idea.readFrom(ideaName);
        mView.selectIdea(idea);
    }

    @Override
    public void onNavigation(boolean show) {
        mView.showDrawer(!show);
    }

    @Override
    public void onMenuEdit() {
        mView.setEditLayout();
    }

    @Override
    public void onMenuSave() {
        mView.setViewLayout();
    }

    @Override
    public void onEditPicFromCamera(int detailId) {

    }
}
