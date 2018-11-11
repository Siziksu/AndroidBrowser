package com.siziksu.browser.ui.view.main.menu.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.model.OverflowMenuItem;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OverflowMenuDialogFragment extends DialogFragment {

    @BindView(R.id.actionHome)
    ImageView actionHome;
    @BindView(R.id.actionForward)
    ImageView actionForward;
    @BindView(R.id.actionBookmark)
    ImageView actionBookmark;
    @BindView(R.id.overflowMenuRecycler)
    RecyclerView recyclerView;

    private View source;
    private Consumer<Integer> listener;
    private boolean isHome;
    private boolean canGoForward;
    private boolean isBookmarked;

    public OverflowMenuDialogFragment() {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dialog_fragment_menu, container);
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

    @OnClick({R.id.actionHome, R.id.actionForward, R.id.actionBookmark, R.id.actionReload})
    public void onItemClick(View view) {
        if (listener == null) { return; }
        listener.accept(view.getId());
        dismiss();
    }

    public void setSource(View source) {
        this.source = source;
    }

    public void setListener(Consumer<Integer> listener) {
        this.listener = listener;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    public void setCanGoForward(boolean canGoForward) {
        this.canGoForward = canGoForward;
    }

    public void setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    private void initializeViews(View inflatedView) {
        ButterKnife.bind(this, inflatedView);
        actionHome.setEnabled(!isHome);
        actionForward.setEnabled(canGoForward);
        actionBookmark.setImageResource(isBookmarked ? R.drawable.ic_action_bookmarked : R.drawable.ic_action_bookmark);
        OverflowMenuAdapterContract adapter = new OverflowMenuAdapter(getActivity(), new OverflowMenuItemManager());
        adapter.init(
                resource -> {
                    if (listener != null) {
                        listener.accept(resource);
                        dismiss();
                    }
                }
        );
        adapter.addItems(Arrays.asList(
                new OverflowMenuItem(R.id.actionBookmarks, "Bookmarks"),
                new OverflowMenuItem(R.id.actionDesktopSite, "Toggle desktop site"),
                new OverflowMenuItem(R.id.actionHtml5, "Html5 Test"))
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter.getAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(adapter.getLayoutManager());
    }

    private void setDialogPosition() {
        if (source != null) {
            int[] location = new int[2];
            source.getLocationOnScreen(location);
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
    }

    private int dpToPx(float valueInDp) {
        if (getActivity() == null) { return 0; }
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        return metrics != null ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics) : 0;
    }
}
