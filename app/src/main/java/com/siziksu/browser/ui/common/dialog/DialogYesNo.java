package com.siziksu.browser.ui.common.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Action;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class DialogYesNo extends DialogFragment {

    @BindView(R.id.dialogMessage)
    TextView textView;

    private Action listener;
    private String message;

    public DialogYesNo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_yes_no, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getTheme() {
        return R.style.AppTheme_AppCompatDialogStyle_NoTitle;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setText(TextUtils.isEmpty(message) ? Constants.TAG : message);
    }

    public DialogYesNo setAcceptCallback(Action listener) {
        this.listener = listener;
        return this;
    }

    public DialogYesNo setMessage(String message) {
        this.message = message;
        return this;
    }

    @OnClick(R.id.dialogCancel)
    public void onCancelButtonClick() {
        dismiss();
    }

    @OnClick(R.id.dialogAccept)
    public void onSubmitButtonClick() {
        if (listener != null) {
            listener.execute();
        }
        dismiss();
    }
}
