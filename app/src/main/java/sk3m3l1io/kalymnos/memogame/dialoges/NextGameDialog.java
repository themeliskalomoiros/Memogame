package sk3m3l1io.kalymnos.memogame.dialoges;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sk3m3l1io.kalymnos.memogame.R;

public class NextGameDialog extends DialogFragment {
    public interface ResponseListener {
        void onDialogPositiveResponse(NextGameDialog dialog);

        void onDialogNegativeResponse(NextGameDialog dialog);
    }

    private int messageRes;
    private ResponseListener listener;

    public NextGameDialog(int messageRes) {
        this.messageRes = messageRes;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ResponseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public void setResponseListener(ResponseListener listener) {
        if (listener != null) this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setMessage(messageRes)
            .setPositiveButton(R.string.next_game, (d, i) -> listener.onDialogPositiveResponse(this))
            .setNegativeButton(R.string.no, (d, i) -> listener.onDialogNegativeResponse(this));
        return builder.create();
    }
}
