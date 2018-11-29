package com.siziksu.browser.ui.view.main.menu.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class LinkMenuDialogFragment extends DialogFragment {

    private int x;
    private int y;
    private Consumer<Integer> listener;

    public LinkMenuDialogFragment() {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dialog_fragment_menu_link, container);
        initializeViews(inflatedView);
        setDialogPosition();
        return inflatedView;
    }

    @Override
    public int getTheme() {
        return R.style.AppTheme_AppCompatDialogStyle_NoTitle;
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @OnClick({R.id.actionVisitUrl, R.id.actionCopyUrl})
    public void onItemClick(View view) {
        if (listener != null) {
            listener.accept(view.getId());
        }
        dismiss();
    }

    public void setTouchPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setListener(Consumer<Integer> listener) {
        this.listener = listener;
    }

    private void initializeViews(View inflatedView) {
        ButterKnife.bind(this, inflatedView);
    }

    private void setDialogPosition() {
        int[] location = {x, y};
        int sourceX = location[0];
        int sourceY = location[1];
        Window window = getDialog().getWindow();
        if (window == null) { return; }
        window.setGravity(Gravity.TOP | Gravity.START);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = sourceX - dpToPx(60);
        params.y = sourceY - dpToPx(50);
        window.setAttributes(params);
    }

    private int dpToPx(float valueInDp) {
        if (getActivity() == null) { return 0; }
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        return metrics != null ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics) : 0;
    }
}
