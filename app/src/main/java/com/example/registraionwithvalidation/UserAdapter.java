package com.example.registraionwithvalidation;

import static com.example.registraionwithvalidation.R.layout;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userArrayList;
    private Context context;

    public UserAdapter(Context context, List<User> modelOrderArrayList) {
        this.context = context;
        this.userArrayList = modelOrderArrayList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout.activity_users_list, viewGroup, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.id.setText(user.getUserId().trim());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private static final String TAG = "MyViewHolder";
        TextView id;
        ImageButton imageButton;
        private MenuItem menuItem;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            menuItem = item;
            User user;
            switch (item.getItemId()) {
                case R.id.action_popup_view:
                    Intent intent1 = new Intent(context.getApplicationContext(), ViewUserDetails.class);
                    intent1.putExtra(AppConstants.USER_ID, userArrayList.get(getAdapterPosition()).getUserId());
                    context.startActivity(intent1);
                    break;
                case R.id.action_popup_edit:
                    user = new User();
                    user = userArrayList.get(getAdapterPosition());
                    Intent intent = new Intent(context, UpdateUserDetails.class);
                    intent.putExtra(AppConstants.USER_ID, user.getUserId().toString());
                    context.startActivity(intent);
                    break;
                case R.id.action_popup_delete:
                    DBHelper dbHelper = DBHelper.getInstance(context.getApplicationContext());
                    user = userArrayList.get(getAdapterPosition());
                    Log.d(TAG, "onMenuItemClick: action_popup_delete @ " + getAdapterPosition());
                    userArrayList.remove(user);
                    dbHelper.userDao().deleteUser(user);
                    notifyDataSetChanged();
                    break;
                default:
                    return false;
            }
            return false;
        }
    }
}
