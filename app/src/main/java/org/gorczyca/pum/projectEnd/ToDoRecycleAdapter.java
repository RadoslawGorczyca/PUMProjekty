package org.gorczyca.pum.projectEnd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.Constants;

import java.util.Calendar;
import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 22.05.2020 23:49
 */
public class ToDoRecycleAdapter extends RecyclerView.Adapter<ToDoRecycleAdapter.ToDoListViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ToDoItem> toDoItems;
    private Context context;

    public ToDoRecycleAdapter(@NonNull Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setToDoItemsList(List<ToDoItem> toDoItemsList){
        this.toDoItems = toDoItemsList;
        notifyDataSetChanged();
    }

    @Override
    public ToDoRecycleAdapter.ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.layout_to_do_item, parent, false);
        return new ToDoRecycleAdapter.ToDoListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        if(toDoItems == null){
            return;
        }

        final ToDoItem toDoItem = toDoItems.get(position);
        if(toDoItem != null){
            holder.isDone.setChecked(toDoItem.isDone());
            holder.name.setText(toDoItem.getName());
            holder.imageHighPriority.setVisibility(toDoItem.isHighPriority() ? View.VISIBLE : View.GONE);
            if(toDoItem.isDone()){
                holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            holder.isDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
                toDoItem.setDone(isChecked);
                if(toDoItem.isDone()){
                    holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Calendar calendar = Calendar.getInstance();
                    toDoItem.setEndDateMillis(calendar.getTimeInMillis());
                } else {
                    holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    toDoItem.setEndDateMillis(0);
                }
                ToDoMainActivity.toDoItemsViewModel.update(toDoItem);
            });
            holder.buttonOptions.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, holder.buttonOptions);
                popupMenu.getMenuInflater().inflate(R.menu.menu_project_end_to_do_item_options_pop_up, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()){
                        case R.id.action_details:
                            Intent intentDetails = new Intent(context, ToDoItemDetailsActivity.class);
                            intentDetails.putExtra(Constants.INTENT_KEY_TO_DO_ITEM_DETAILS, toDoItem.getUuid());
                            context.startActivity(intentDetails);
                            return true;
                        case R.id.action_edit:
                            Intent intentEdit = new Intent(context, ToDoAddOrEditActivity.class);
                            intentEdit.putExtra(Constants.TO_DO_EDITOR_ACTIVITY_MODE, Constants.TO_DO_EDITOR_MODE_EDITING);
                            intentEdit.putExtra(Constants.INTENT_KEY_TO_DO_ITEM_TO_EDIT, toDoItem.getUuid());
                            context.startActivity(intentEdit);
                            return true;
                        case R.id.action_delete:
                            new AlertDialog.Builder(context)
                                    .setTitle(R.string.delete)
                                    .setMessage(R.string.are_you_sure)
                                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                                        //ToDoMainActivity.toDoItemsViewModel.delete(toDoItem);
                                        ToDoItemDao toDoItemDao = ToDoItemsDatabase.getDatabase(context).toDoItemDao();
                                        toDoItemDao.delete(toDoItem);
                                        toDoItems.remove(toDoItem);
                                        notifyDataSetChanged();
                                    })
                                    .setNegativeButton(R.string.cancel, null)
                                    .show();
                            return true;
                        default:
                            return false;
                    }
                });
                popupMenu.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        if (toDoItems == null) {
            return 0;
        } else {
            return toDoItems.size();
        }
    }

    static class ToDoListViewHolder extends RecyclerView.ViewHolder {
        private CheckBox isDone;
        private TextView name;
        private ImageButton buttonOptions;
        private ImageView imageHighPriority;

        ToDoListViewHolder(@NonNull View itemView) {
            super(itemView);
            isDone = itemView.findViewById(R.id.check_to_do_is_done);
            name = itemView.findViewById(R.id.text_to_do_name);
            buttonOptions = itemView.findViewById(R.id.button_item_options);
            imageHighPriority = itemView.findViewById(R.id.image_high_priority);
        }
    }
}
