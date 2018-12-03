package com.siziksu.browser.ui.view.main;

import android.content.Intent;

public interface BrowserFragmentContract {

    void onBackPressed();

    void onActionMoreClick();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
