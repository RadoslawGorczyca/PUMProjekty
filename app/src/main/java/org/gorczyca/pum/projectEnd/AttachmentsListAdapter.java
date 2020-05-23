package org.gorczyca.pum.projectEnd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.gorczyca.pum.R;

import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 23.05.2020 19:38
 */
public class AttachmentsListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> attachmentsList;
    private boolean isRemovable;

    public AttachmentsListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, boolean isRemovable) {
        super(context, resource, objects);
        this.context = context;
        this.attachmentsList = objects;
        this.isRemovable = isRemovable;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;

        if (isRemovable) {
            view = inflater.inflate(R.layout.layout_to_do_attachment_removable, null);
            ImageButton delete = view.findViewById(R.id.button_delete_attachment);
            delete.setOnClickListener(v -> {
                attachmentsList.remove(attachmentsList.get(position));
                notifyDataSetChanged();
            });
        } else {
            view = inflater.inflate(R.layout.layout_to_do_attachment, null);
        }

        TextView textView = view.findViewById(R.id.text_attachment);
        textView.setText(attachmentsList.get(position));

        return view;
    }
}
