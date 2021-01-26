package com.example.dam_seminar_005;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam_seminar_005.fragments.AboutFragment;
import com.example.dam_seminar_005.fragments.HomeFragment;
import com.example.dam_seminar_005.util.Expense;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_EXPENSE_REQUEST_CODE = 210;
    private static final int SELECT_ACCOUNT_CODE = 210;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment currentFragment;
    private ArrayList<Expense> expenses = new ArrayList<>();

    private TextView tv_main_show_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();
        initComponents();
        openDefaultFragment(savedInstanceState);
    }

    private void initComponents() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(setNavigationItemSelectedEvent());
    }

    /***************** NAVIGATION DRAWER *****************/
    private NavigationView.OnNavigationItemSelectedListener setNavigationItemSelectedEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.drawer_nav_home) {
                    currentFragment = HomeFragment.newInstance(expenses);
                } else if (item.getItemId() == R.id.drawer_nav_about) {
                    currentFragment = new AboutFragment();
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_close,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    /***************** FRAGMENTS *****************/
    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = HomeFragment.newInstance(expenses);
            openFragment();
            navigationView.setCheckedItem(R.id.drawer_nav_home);
        }
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, currentFragment)
                .commit();
    }

    /***************** OLD MENU *****************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_old_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.old_menu_new) {
            Intent intent = new Intent(getApplicationContext(), NewExpenseActivity.class);
            startActivityForResult(intent, NEW_EXPENSE_REQUEST_CODE);
        } else if (item.getItemId() == R.id.old_menu_select_account) {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivityForResult(intent, SELECT_ACCOUNT_CODE);
        }
        return true;
    }

    /***************** RESULTS *****************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && data != null) {

            if (requestCode == NEW_EXPENSE_REQUEST_CODE) {
                Expense expense = (Expense) data.getParcelableExtra(NewExpenseActivity.NEW_EXPENSE_KEY);
                if (expense != null) {
                    Toast.makeText(getApplicationContext(), R.string.new_expense_added,
                            Toast.LENGTH_SHORT).show();
                    expenses.add(expense);
                }
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).notifyAdapter();
                }
            } else if (requestCode == SELECT_ACCOUNT_CODE) {
                String name = data.getStringExtra(AccountActivity.SELECT_ACCOUNT_KEY);
                tv_main_show_message = findViewById(R.id.tv_main_show_message);
                tv_main_show_message.setText(getString(R.string.tv_main_show_message, name));
            }
        }
    }
}