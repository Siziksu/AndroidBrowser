package com.siziksu.browser.ui.view.main;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public interface BrowserActivityContract {

    AppCompatActivity getActivity();

    void onAttach(BrowserFragment fragment);

    void clearEditText();

    EditText getEditText();

    View getActionMoreView();

    void onMenuShow();

    void onMenuDismiss();

    void keyboardGoClicked();

    void finish();

    void superOnBackPressed();
}
