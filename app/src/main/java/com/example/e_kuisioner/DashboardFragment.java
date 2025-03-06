package com.example.e_kuisioner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DatabaseHelper myDb;
    private ListView userListView;
    private ArrayAdapter<String> userAdapter;
    private List<String> userIdList;
    private List<String> userDisplayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        myDb = new DatabaseHelper(requireContext());
        userListView = view.findViewById(R.id.user_list_view);

        userIdList = new ArrayList<>();
        userDisplayList = new ArrayList<>();

        loadAllNonAdminUsers();

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userId = userIdList.get(position);
                Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadAllNonAdminUsers() {
        Cursor cursor = myDb.getAllNonAdminUsers();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No users found", Toast.LENGTH_LONG).show();
            return;
        }

        userDisplayList.clear();
        userIdList.clear();
        int userNumber = 1;

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_1));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_4));
            @SuppressLint("Range") int isApproved = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_6));

            String approvalStatus = isApproved == 1 ? "(Approved)" : "(Not Approved)";
            userDisplayList.add(userNumber + ". " + email + " " + approvalStatus);
            userIdList.add(userId);
            userNumber++;
        }
        cursor.close();

        userAdapter = new ArrayAdapter<>(requireContext(), R.layout.list_view, R.id.text_view_item, userDisplayList);
        userListView.setAdapter(userAdapter);
    }
}
