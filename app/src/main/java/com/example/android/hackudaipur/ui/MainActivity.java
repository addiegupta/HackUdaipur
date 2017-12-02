package com.example.android.hackudaipur.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.data.UserColumns;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.hackudaipur.data.UserProvider.AUTHORITY;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.drawerPane)
    RelativeLayout mDrawerPane;
    @BindView(R.id.navList)
    ListView mDrawerList;
    @BindView(R.id.userName)
    TextView mTVSelectedUser;
    @BindView(R.id.tv_main)
    TextView mTVMain;
    @BindView(R.id.fab_main_add)
    FloatingActionButton mFABAdd;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerListAdapter mDrawerListAdapter;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    public static final Uri URI_USERS = Uri.parse("content://" + AUTHORITY + "/users");

    private ArrayList<NavItem> mNavItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNavItems = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putInt(getString(R.string.SELECTED_USER_ID), mNavItems.get(position).mId).apply();
                Toast.makeText(MainActivity.this, mNavItems.get(position).mTitle + " selected", Toast.LENGTH_SHORT).show();
                loadNavigationList();
                mDrawerLayout.closeDrawer(mDrawerPane);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        String filename = "outside.txt";
        File myExternalFile;
        myExternalFile = new File(getExternalFilesDir(null), filename);
        String myData = "";

        try {

            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;
            }
            in.close();
            mTVMain.setText(myData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectUserActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNavigationList();
    }

    private void loadNavigationList() {
        mNavItems.clear();
        try {
            Cursor cursor = getContentResolver().query(URI_USERS, null, null, null, null);
            if (cursor != null) {
                int selectedUserId = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.SELECTED_USER_ID), 0);
                cursor.moveToFirst();
                boolean userSelected = false;
                while (!cursor.isAfterLast()) {
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns.NAME));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserColumns._ID));

                    if (id == selectedUserId) {
                        userSelected = true;
                        mTVSelectedUser.setText(userName);
                    } else {
                        mNavItems.add(new NavItem(userName, id));
                    }

                    cursor.moveToNext();
                }
                if (!userSelected) {
                    NavItem selectedItem = mNavItems.get(mNavItems.size() - 1);
                    int id = selectedItem.mId;
                    String name = selectedItem.mTitle;
                    mTVSelectedUser.setText(name);
                    PreferenceManager.getDefaultSharedPreferences(this).edit().
                            putInt(getString(R.string.SELECTED_USER_ID), id).apply();
                    mNavItems.remove(mNavItems.size() - 1);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Populate the Navigation Drawer with options
        mDrawerListAdapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(mDrawerListAdapter);

    }

    private void deleteSelectedUser() {
        int selectedUserId = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt(getString(R.string.SELECTED_USER_ID), 0);

        String selection = UserColumns._ID + "=?";
        String[] selectionArgs = {String.valueOf(selectedUserId)};

        getContentResolver().delete(URI_USERS, selection, selectionArgs);

        Toast.makeText(this, selectedUserId + " deleted", Toast.LENGTH_SHORT).show();

        loadNavigationList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_add_user:
                startActivity(new Intent(MainActivity.this, AddUserActivity.class));
                break;
            case R.id.action_delete_user:
                showDeleteConfirmationDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void showDeleteConfirmationDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_confirm_message)
                .setMessage(getString(R.string.user_will_be_deleted_permanently))
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSelectedUser();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            } else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            titleView.setText(mNavItems.get(position).mTitle);
            return view;
        }
    }

    private class NavItem {
        String mTitle;
        int mId;

        public NavItem(String title, int id) {
            mTitle = title;
            mId = id;
        }
    }
}
