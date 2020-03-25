package sk3m3l1io.kalymnos.memogame.dialoges;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import sk3m3l1io.kalymnos.memogame.R;

public class VictoryDialog extends DialogFragment {
    public interface VictoryTitleDialogListener {
        void onDialogPositiveClick();
    }

    private VictoryTitleDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (VictoryTitleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public void setAddTitleDialogListener(VictoryTitleDialogListener listener) {
        if (listener != null) this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.victory);
        builder.setPositiveButton(R.string.next_game,
                (dialog, id) -> {
                    listener.onDialogPositiveClick();
                });
        builder.setNegativeButton(R.string.no, null);
        return builder.create();
    }

}
