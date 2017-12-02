package com.example.android.hackudaipur.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> mUserList;
    private Context mContext;
    private UserOnClickHandler mClickHandler;

    public UserAdapter(Context context,List<User> users, UserOnClickHandler handler) {
        this.mContext = context;
        this.mUserList = users;
        this.mClickHandler = handler;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_list_item, null);
        return new ViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User user = mUserList.get(position);
        viewHolder.mTVUserName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    public interface UserOnClickHandler{
        void onClick(User selectedUser);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener{

        @BindView(R.id.tv_select_user_name)
        TextView mTVUserName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this,itemLayoutView);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            User user = mUserList.get(adapterPosition);
            mClickHandler.onClick(user);
        }
    }

    public List<User> getUserList() {
        return mUserList;
    }
}