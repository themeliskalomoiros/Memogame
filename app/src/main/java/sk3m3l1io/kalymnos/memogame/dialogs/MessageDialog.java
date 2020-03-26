package sk3m3l1io.kalymnos.memogame.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MessageDialog extends DialogFragment {
    public interface ResponseListener {
        void onDialogPositiveResponse(MessageDialog dialog);

        void onDialogNegativeResponse(MessageDialog dialog);
    }

    private String msg;
    private ResponseListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ResponseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement" + MessageDialog.ResponseListener.class.getSimpleName());
        }
    }

    public void setResponseListener(ResponseListener listener) {
        if (listener != null) this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, (d, i) -> listener.onDialogPositiveResponse(this))
                .setNegativeButton(android.R.string.no, (d, i) -> listener.onDialogNegativeResponse(this));
        return builder.create();
    }

    public static void showInstance(
            ResponseListener listener,
            FragmentManager fragmentManager,
            String msg,
            String tag) {
        MessageDialog d = new MessageDialog();
        d.msg = msg;
        d.setResponseListener(listener);
        showDialogAllowingStateLoss(fragmentManager, d, tag);
    }

    // From https://medium.com/inloopx/demystifying-androids-commitallowingstateloss-cb9011a544cc
    private static void showDialogAllowingStateLoss(
            FragmentManager fragmentManager,
            DialogFragment dialogFragment,
            String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(dialogFragment, tag);
        ft.commitAllowingStateLoss();
    }
}
