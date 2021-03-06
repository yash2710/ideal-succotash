package me.yashtrivedi.ideal_succotash.activity;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.adapter.TViewAdapter;
import me.yashtrivedi.ideal_succotash.fragment.ChatConversationFragment;
import me.yashtrivedi.ideal_succotash.fragment.ChatThreadFragment;
import me.yashtrivedi.ideal_succotash.fragment.ShowCreateChatFragment;
import me.yashtrivedi.ideal_succotash.model.Threads;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        if(b!=null&&b.getBoolean("toChat",false)){
            ChatConversationFragment chatConversationFragment = new ChatConversationFragment();
            chatConversationFragment.setArguments(b);
            fragmentManager.beginTransaction().replace(R.id.container,chatConversationFragment).commit();
        }
        else
        fragmentManager.beginTransaction().replace(R.id.container,new ChatThreadFragment()).commit();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel("onethreetwo",12345);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
        else
            finish();
        return true;
    }

}
