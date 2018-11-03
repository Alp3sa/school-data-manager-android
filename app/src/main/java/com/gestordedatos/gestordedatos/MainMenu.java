package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.gestordedatos.gestordedatos.classrooms.ClassroomsListFragment;
import com.gestordedatos.gestordedatos.classrooms.FormListClassrooms;
import com.gestordedatos.gestordedatos.classrooms.FormListClassroomsUpdate;
import com.gestordedatos.gestordedatos.classrooms.ListClassrooms;
import com.gestordedatos.gestordedatos.contentProvider.ClassroomProvider;
import com.gestordedatos.gestordedatos.contentProvider.Contract;
import com.gestordedatos.gestordedatos.contentProvider.SubjectProvider;
import com.gestordedatos.gestordedatos.contentProvider.TutorshipProvider;
import com.gestordedatos.gestordedatos.pojos.User;
import com.gestordedatos.gestordedatos.subjects.FormListSubjects;
import com.gestordedatos.gestordedatos.subjects.FormListSubjectsUpdate;
import com.gestordedatos.gestordedatos.subjects.ListSubjects;
import com.gestordedatos.gestordedatos.subjects.SubjectsListFragment;
import com.gestordedatos.gestordedatos.tutorships.FormListTutorships;
import com.gestordedatos.gestordedatos.tutorships.FormListTutorshipsUpdate;
import com.gestordedatos.gestordedatos.tutorships.ListTutorships;
import com.gestordedatos.gestordedatos.tutorships.TutorshipsListFragment;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    Activity contexto = this;

    private TextView titleNavigationView;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BEGIN TEST - SAVE TIME TO SKIP THE LOGIN SYSTEM
        //User User = new User("",null,null,null,null,null,null,null);
        //((application) getApplicationContext()).User = User;
        //END TEST

        //Set layouts
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //Tab listener to replace fragments
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    application.CLASSROOM_TABLE_NAME="Classrooms";
                    application.LAST_TAB=0;
                    ClassroomsListFragment myFragment = new ClassroomsListFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment, myFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(tab.getPosition()==1){
                    application.CLASSROOM_TABLE_NAME="Subjects";
                    application.LAST_TAB=1;
                    SubjectsListFragment myFragment = new SubjectsListFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment, myFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(tab.getPosition()==2){
                    application.CLASSROOM_TABLE_NAME="Tutorships";
                    application.LAST_TAB=2;
                    TutorshipsListFragment myFragment = new TutorshipsListFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment, myFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        //Floating button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.action_insert_alternative);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = null;
                if(application.CLASSROOM_TABLE_NAME=="Classrooms") {
                    intent = new Intent(getBaseContext(), FormListClassrooms.class);
                }
                else if(application.CLASSROOM_TABLE_NAME=="Subjects") {
                    intent = new Intent(getBaseContext(), FormListSubjects.class);
                }
                else if(application.CLASSROOM_TABLE_NAME=="Tutorships") {
                    intent = new Intent(getBaseContext(), FormListTutorships.class);
                }
                startActivity(intent);
            }
        });

        //Navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set tab selected and default tab at the beginning
        tabLayout.getTabAt(application.LAST_TAB).select();
        //Add fragment depending on tab
        if(application.LAST_TAB==0) {
            ClassroomsListFragment myFragment = new ClassroomsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(application.LAST_TAB==1) {
            SubjectsListFragment myFragment = new SubjectsListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(myFragment);
            transaction.add(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            application.CLASSROOM_TABLE_NAME="Subjects";
            transaction.commit();
        }
        else if(application.LAST_TAB==2) {
            TutorshipsListFragment myFragment = new TutorshipsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insert_menu, menu);

        //Set title NavigationView
        //User User = getIntent().getExtras().getParcelable("User");
        User User = ((application) getApplicationContext()).User;
        titleNavigationView = (TextView) findViewById(R.id.navigationView_title);
        titleNavigationView.setText(User.getNombreUsuario());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
        else */
        if(id == R.id.action_help){
            showHelp();
            return true;
        }
        else if(id == R.id.action_insert){
            Intent intent = null;
            if(application.CLASSROOM_TABLE_NAME=="Classrooms") {
                intent = new Intent(this, FormListClassrooms.class);
            }
            else if(application.CLASSROOM_TABLE_NAME=="Subjects") {
                intent = new Intent(this, FormListSubjects.class);
            }
            else if(application.CLASSROOM_TABLE_NAME=="Tutorships") {
                intent = new Intent(this, FormListTutorships.class);
            }
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_delete){
            //Restore toolbar
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.insert_menu);
            //Delete record
            if(application.CLASSROOM_TABLE_NAME=="Classrooms") {
                ClassroomProvider.deleteRecord(this.getContentResolver(),(Integer) ClassroomsListFragment.rowSelected.getTag());
            }
            else if(application.CLASSROOM_TABLE_NAME=="Subjects") {
                SubjectProvider.deleteRecord(this.getContentResolver(),(Integer) SubjectsListFragment.rowSelected.getTag());
            }
            else if(application.CLASSROOM_TABLE_NAME=="Tutorships") {
                TutorshipProvider.deleteRecord(this.getContentResolver(),(Integer) TutorshipsListFragment.rowSelected.getTag());
            }
            return true;
        }
        else if(id == R.id.action_update){
            Intent intent = null;
            if(application.CLASSROOM_TABLE_NAME=="Classrooms") {
                intent = new Intent(this, FormListClassroomsUpdate.class);
                intent.putExtra(Contract.Classroom._ID,(Integer) ClassroomsListFragment.rowSelected.getTag());
            }
            else if(application.CLASSROOM_TABLE_NAME=="Subjects") {
                intent = new Intent(this, FormListSubjectsUpdate.class);
                intent.putExtra(Contract.Subject._ID,(Integer) SubjectsListFragment.rowSelected.getTag());
            }
            else if(application.CLASSROOM_TABLE_NAME=="Tutorships") {
                intent = new Intent(this, FormListTutorshipsUpdate.class);
                intent.putExtra(Contract.Tutorship._ID,(Integer) TutorshipsListFragment.rowSelected.getTag());
            }

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }
        /*else if (id == R.id.nav_profile) {

        }
        */else if (id == R.id.nav_formListClassrooms) {
            tabLayout.getTabAt(0).select();
        } else if (id == R.id.nav_formListSubjects) {
            tabLayout.getTabAt(1).select();
        } else if (id == R.id.nav_formListTutorships) {
            tabLayout.getTabAt(2).select();
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(contexto, MainActivity.class);
            ((application) getApplicationContext()).User =null;
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section classroomName for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * classroomName.
         */
        public static MainMenu.PlaceholderFragment newInstance(int sectionNumber) {
            MainMenu.PlaceholderFragment fragment = new MainMenu.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_main_menu, container, false);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public void showHelp() {
        new AlertDialog.Builder(MainMenu.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpMainMenu))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
