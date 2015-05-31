package com.hermes_un_jardin.hermesunjardin.presenter;

import com.hermes_un_jardin.hermesunjardin.model.Idea;

/**
 * Created by song on 2015/5/31.
 */
public interface MainPresenter {

    /**
     * @param show Current state
     */
    void onNavigation(boolean show);

    void onDrawerSelect(Idea idea);

    void onMenuEdit();

    void onMenuSave();

    void onEditPicFromCamera(int detailId);

}
