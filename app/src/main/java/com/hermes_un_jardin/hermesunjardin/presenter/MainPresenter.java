package com.hermes_un_jardin.hermesunjardin.presenter;

/**
 * Created by song on 2015/5/31.
 */
public interface MainPresenter {

    /**
     * @param show Current state
     */
    void onNavigation(boolean show);

    void onDrawerSelect(int position);

    void onMenuEdit();

    void onMenuSave();

    void onEditPicFromCamera(int detailId);

}
