package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.User;

import java.util.List;

public class AccountListActivity extends AppCompatActivity {
    private final Facade facade = Facade.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        assert recyclerView != null;
        recyclerView.setAdapter(new accountRecyclerViewAdapter(facade.getAllUsers()));
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    /**
     * Custom adapter taking user objects and mapping their toString to a text field
     */
    class accountRecyclerViewAdapter
            extends RecyclerView.Adapter<accountRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the User accounts to be shown in this list
         */
        private final List<User> mUsers;

        /**
         * set the items to be used by the adapter
         *
         * @param userList the list of Users to be displayed in the recycler view
         */
        accountRecyclerViewAdapter(List<User> userList) {
            mUsers = userList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // set up the view for each individual item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mUser = mUsers.get(position);

            // binding data
            holder.mView.setText(mUsers.get(position).toString());

            // set up listener to handle if the user clicks on the list item
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AccountListActivity.this, ViewUserActivity.class);
                    intent.putExtra("username", holder.mUser.getUsername());
                    intent.putExtra("name", holder.mUser.getName());
                    intent.putExtra("isBlocked", holder.mUser.isLockedOut() ? "Yes" : "No");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding b/t the model element (a WaterReport) and the two TextViews in the view
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mView;
            User mUser;

            ViewHolder(View view) {
                super(view);
                mView = (TextView) view.findViewById(R.id.item);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mView.getText() + "'";
            }
        }
    }
}
