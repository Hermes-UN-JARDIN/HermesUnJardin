package com.hermes_un_jardin.hermesunjardin.view;

import com.hermes_un_jardin.hermesunjardin.model.Idea;

/**
 * Created by song on 2015/5/31.
 */
public interface MainActivityView {
    //
    void showDrawer(boolean show);

    void selectIdea(Idea idea);

    // State
    void setViewLayout();

    void setEditLayout();
}
