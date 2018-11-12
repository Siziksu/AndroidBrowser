package com.siziksu.browser.ui.view.main;

import android.content.Intent;

public interface BrowserFragmentContract {

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
