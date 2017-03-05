package com.riprap.emrox.trippin.ui.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogListFragment extends DialogFragment {

    private DialogClickListener mListener;

    public interface DialogClickListener {
        public void itemChosen(String selection);
    }
    public MyDialogListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        String title = args.getString("title", "");
        final String [] listItems = args.getStringArray("list_items");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setItems(listItems, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int postition) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        //send the selected choice back to the fragment
                        mListener.itemChosen(listItems[postition]);
                    }
                });
        return builder.create();
    }

}
