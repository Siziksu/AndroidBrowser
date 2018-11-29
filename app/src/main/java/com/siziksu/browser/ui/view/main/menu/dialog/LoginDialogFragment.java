package com.siziksu.browser.ui.view.main.menu.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.Credentials;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class LoginDialogFragment extends DialogFragment {

    @BindView(R.id.user)
    EditText user;
    @BindView(R.id.password)
    EditText password;

    private Consumer<Credentials> listener;
    private Action dismissListener;

    public LoginDialogFragment() {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dialog_login, container);
        initializeViews(inflatedView);
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

    @OnClick({R.id.dialogAccept, R.id.dialogCancel})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.dialogAccept:
                if (listener != null) {
                    Credentials credentials = new Credentials();
                    credentials.user = user.getText().toString();
                    credentials.password = password.getText().toString();
                    listener.accept(credentials);
                }
                dismiss();
                break;
            case R.id.dialogCancel:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dismissListener.execute();
    }

    public void setListener(Consumer<Credentials> listener) {
        this.listener = listener;
    }

    public void setDismissListener(Action dismissListener) {
        this.dismissListener = dismissListener;
    }

    private void initializeViews(View inflatedView) {
        ButterKnife.bind(this, inflatedView);
    }
}
