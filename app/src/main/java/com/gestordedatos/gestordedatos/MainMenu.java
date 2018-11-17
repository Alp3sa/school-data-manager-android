package com.gestordedatos.gestordedatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.gestordedatos.gestordedatos.subjects.SubjectsListFragment;
import com.gestordedatos.gestordedatos.tutorships.FormListTutorships;
import com.gestordedatos.gestordedatos.tutorships.FormListTutorshipsUpdate;
import com.gestordedatos.gestordedatos.tutorships.TutorshipsListFragment;
/*import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import static com.gestordedatos.gestordedatos.contentProvider.SubjectProvider.readRecordFromClassrooms;
import static java.lang.Thread.sleep;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnLayoutChangeListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    Activity contexto = this;

    private TextView titleNavigationView;
    TabLayout tabLayout;

    Menu menu;

    ImageButton searchButton;
    EditText searchBox;
    String textSearched=null;
    int checkSearchBox=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BEGIN TEST - SAVE TIME TO SKIP THE LOGIN SYSTEM
        //User User = new User("",null,null,null,null,null,null,null);
        //((Globals) getApplicationContext()).User = User;
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
                //Set fragments based on tabs selection
                setFragments(tab);
                //Reset searchBox
                checkSearchBox=0;
                searchBox.clearFocus();
                searchBox.setText(getResources().getString(R.string.searchBox));
                hideKeyboard(contexto);

                //Reset subquery vars
                if (tab.getPosition() != 1) {
                    Globals.classroomsSubquery = null;
                    Globals.subquery=-1;
                }
                else if (tab.getPosition() != 0) {
                    Globals.classrooms1Subquery = null;
                    Globals.classrooms2Subquery = null;
                    Globals.subquery=-1;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Reset fragment
                setFragments(tab);

                //Reset searchBox
                checkSearchBox=0;
                searchBox.clearFocus();
                searchBox.setText(getResources().getString(R.string.searchBox));
                hideKeyboard(contexto);

                //Reset subquery vars
                Globals.classroomsSubquery = null;
                Globals.classrooms1Subquery = null;
                Globals.classrooms2Subquery = null;
                Globals.subquery=-1;
            }
        });

        //Floating button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.action_insert_alternative);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = null;
                if(Globals.CLASSROOM_TABLE_NAME=="Classrooms") {
                    intent = new Intent(getBaseContext(), FormListClassrooms.class);
                }
                else if(Globals.CLASSROOM_TABLE_NAME=="Subjects") {
                    intent = new Intent(getBaseContext(), FormListSubjects.class);
                }
                else if(Globals.CLASSROOM_TABLE_NAME=="Tutorships") {
                    intent = new Intent(getBaseContext(), FormListTutorships.class);
                }
                startActivity(intent);
            }
        });



        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchBox = (EditText) findViewById(R.id.textViewSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //Get text from searchBox
                searchBox = (EditText) findViewById(R.id.textViewSearch);
                textSearched = searchBox.getText().toString();
                Globals.tutorshipSearchBox=textSearched;

                //Hide keyboard
                hideKeyboard(contexto);

                //Reload fragment calling to the tabListener
                TabLayout.Tab tab = tabLayout.getTabAt(Globals.LAST_TAB);
                tab.select();
            }
        });

        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(checkSearchBox==0){
                    searchBox.setText("");
                    checkSearchBox=1;
                }
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
        tabLayout.getTabAt(Globals.LAST_TAB).select();
        //Add fragment depending on tab
        if(Globals.LAST_TAB==0) {
            ClassroomsListFragment myFragment = new ClassroomsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(Globals.LAST_TAB==1) {
            SubjectsListFragment myFragment = new SubjectsListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(myFragment);
            transaction.add(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            Globals.CLASSROOM_TABLE_NAME="Subjects";
            transaction.commit();
        }
        else if(Globals.LAST_TAB==2) {
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
        this.menu = menu;

        // Inflate the menu; this adds items to the action bar if it is present.
        if(Globals.LAST_TAB>=0 && Globals.LAST_TAB<=2){
            getMenuInflater().inflate(R.menu.insert_menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.map_menu, menu);
        }

        //Set title NavigationView
        //User User = getIntent().getExtras().getParcelable("User");
        User User = ((Globals) getApplicationContext()).User;
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
            if(Globals.CLASSROOM_TABLE_NAME=="Classrooms") {
                intent = new Intent(this, FormListClassrooms.class);
            }
            else if(Globals.CLASSROOM_TABLE_NAME=="Subjects") {
                intent = new Intent(this, FormListSubjects.class);
            }
            else if(Globals.CLASSROOM_TABLE_NAME=="Tutorships") {
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
            if(Globals.CLASSROOM_TABLE_NAME=="Classrooms") {
                try {
                    ClassroomProvider.deleteRecord(this.getContentResolver(), (Integer) ClassroomsListFragment.rowSelected.getTag(), this);
                }
                catch (android.database.SQLException e){
                    String toast = getResources().getString(R.string.errorClassroomUsed);
                    SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                    biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                    Toast errorImage = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                    errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                    errorImage.show();
                }
            }
            else if(Globals.CLASSROOM_TABLE_NAME=="Subjects") {
                SubjectProvider.deleteRecord(this.getContentResolver(),(Integer) SubjectsListFragment.rowSelected.getTag());
            }
            else if(Globals.CLASSROOM_TABLE_NAME=="Tutorships") {
                TutorshipProvider.deleteRecord(this.getContentResolver(),(Integer) TutorshipsListFragment.rowSelected.getTag());
            }
            return true;
        }
        else if(id == R.id.action_update){
            Intent intent = null;
            if(Globals.CLASSROOM_TABLE_NAME=="Classrooms") {
                intent = new Intent(this, FormListClassroomsUpdate.class);
                intent.putExtra(Contract.Classroom._ID,(Integer) ClassroomsListFragment.rowSelected.getTag());
            }
            else if(Globals.CLASSROOM_TABLE_NAME=="Subjects") {
                intent = new Intent(this, FormListSubjectsUpdate.class);
                intent.putExtra(Contract.Subject._ID,(Integer) SubjectsListFragment.rowSelected.getTag());
            }
            else if(Globals.CLASSROOM_TABLE_NAME=="Tutorships") {
                intent = new Intent(this, FormListTutorshipsUpdate.class);
                intent.putExtra(Contract.Tutorship._ID,(Integer) TutorshipsListFragment.rowSelected.getTag());
            }

            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_download){
            CreateJPG();
        }
        else if(id == R.id.action_subquery_classrooms){
            Globals.subquery = (Integer) ClassroomsListFragment.rowSelected.getTag();
            Globals.classroomsSubquery = ClassroomProvider.readRecord(getBaseContext().getContentResolver(),Globals.subquery);
            int checkSucjects = readRecordFromClassrooms(getBaseContext().getContentResolver(),Globals.classroomsSubquery.getClassroomName());

            if(checkSucjects==1){
                Globals.LAST_TAB=1;
                TabLayout.Tab tab = tabLayout.getTabAt(Globals.LAST_TAB);
                tab.select();
            }
            else if(checkSucjects==0){
                Globals.subquery = -1;
                Globals.classroomsSubquery = null;

                String toast = getResources().getString(R.string.errorNoSubjectRelated);
                SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
                biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
                Toast errorImage = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
                errorImage.setGravity(Gravity.BOTTOM, 0, 40);
                errorImage.show();
            }
        }
        else if(id == R.id.action_subquery_subjects){
            Globals.subquery = (Integer) SubjectsListFragment.rowSelected.getTag();
            Globals.classrooms1Subquery = SubjectProvider.readRecord(getBaseContext().getContentResolver(),Globals.subquery);

            Globals.LAST_TAB=0;
            TabLayout.Tab tab = tabLayout.getTabAt(Globals.LAST_TAB);
            tab.select();
        }
        else if(id == R.id.action_subquery_tutorships){
            Globals.subquery = (Integer) TutorshipsListFragment.rowSelected.getTag();
            Globals.classrooms2Subquery = TutorshipProvider.readRecord(getBaseContext().getContentResolver(),Globals.subquery);

            Globals.LAST_TAB=0;
            TabLayout.Tab tab = tabLayout.getTabAt(Globals.LAST_TAB);
            tab.select();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Globals.LAST_TAB=0;
            Globals.subquery=-1;
            Globals.classroomsSubquery=null;
            Globals.classrooms1Subquery=null;
            Globals.classrooms2Subquery=null;

            Globals.CLASSROOM_TABLE_NAME = "Classrooms";
            Globals.LAST_TAB = 0;

            ClassroomsListFragment myFragment = new ClassroomsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            //Set floating button as invisible
            findViewById(R.id.footer).setVisibility(View.VISIBLE);

            TabLayout.Tab tab = tabLayout.getTabAt(Globals.LAST_TAB);
            tab.select();
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
            //Reset subquery vars
            Globals.classroomsSubquery = null;
            Globals.classrooms1Subquery = null;
            Globals.classrooms2Subquery = null;
            Globals.tutorshipSearchBox = null;
            Globals.subquery=-1;

            Intent intent = new Intent(contexto, MainActivity.class);
            ((Globals) getApplicationContext()).User =null;
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
            return 4;
        }
    }

    public void showHelp() {
        String heltText="";

        if(Globals.LAST_TAB>=0 && Globals.LAST_TAB<=2) {
            heltText = getResources().getString(R.string.helpMainMenu);
        }
        else{
            heltText = getResources().getString(R.string.helpMap);
        }
        new AlertDialog.Builder(MainMenu.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(heltText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void CreateJPG(){
        //Get ListView
        ListView view = findViewById(android.R.id.list);
        //Add listener to use the next layout and restore it the original
        view.addOnLayoutChangeListener(this);
        //Get first row
        View child = view.getChildAt(0);
        //Get row height, num of last row and totalHeight
        int height = child.getMeasuredHeight()*ClassroomsListFragment.cursor.getCount();
        //Set ListView height
        view.getLayoutParams().height=height;
        view.requestLayout();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //Get entire ListView
        ListView view = findViewById(android.R.id.list);

        //Reset temporal ListView
        if(Globals.LAST_TAB==0){
            ClassroomsListFragment myFragment = new ClassroomsListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(Globals.LAST_TAB==1){
            SubjectsListFragment myFragment = new SubjectsListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(Globals.LAST_TAB==2){
            TutorshipsListFragment myFragment = new TutorshipsListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(getResources().getString(R.string.downloadingJPG));
        new MainMenu.CreateJPGAsyncTask(progressDialog).execute(view);
    }

    class CreateJPGAsyncTask extends AsyncTask<ListView, Integer, String> {
        ProgressDialog progressDialog;

        CreateJPGAsyncTask(ProgressDialog progressDialog) {
            this.progressDialog=progressDialog;
        }

        @Override
        protected String doInBackground(final ListView... v) {

            try{
                ListView view = v[0];

                //Get measures from table
                publishProgress(25);
                progressDialog.setMessage(getResources().getString(R.string.gettingMeasures));
                sleep(3000);
                View child;
                int width = 0;
                int height = 0;
                int totalHeight = 0;
                Bitmap rowImage=null;
                for(int i=0;i<view.getChildCount();i++){
                    child = view.getChildAt(i);
                    width = child.getMeasuredWidth();
                    height = child.getMeasuredHeight();
                    totalHeight = totalHeight+height;
                }


                //Create bitmap from table
                publishProgress(50);
                progressDialog.setMessage(getResources().getString(R.string.creatingBitmap));
                sleep(3000);
                Bitmap image = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(image);
                canvas.drawColor(Color.WHITE);
                Canvas c;

                for(int e=0;e<view.getChildCount();e++){
                    child = view.getChildAt(e);
                    width = child.getMeasuredWidth();
                    height = child.getMeasuredHeight();
                    rowImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    c = new Canvas(rowImage);
                    child.draw(c);
                    if(e>0){
                        canvas.drawBitmap(rowImage, 0, rowImage.getHeight()*e, null);
                    }
                    else{
                        canvas.drawBitmap(rowImage, new Matrix(), null);
                    }
                }

                //Create JPG
                publishProgress(75);
                progressDialog.setMessage(getResources().getString(R.string.creatingJPG));
                sleep(3000);

                File f = new File(getFilesDir(), Globals.CLASSROOM_TABLE_NAME + ".jpg");
                OutputStream fos = openFileOutput(Globals.CLASSROOM_TABLE_NAME + ".jpg", MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();

                //Finish
                publishProgress(100);
                progressDialog.setMessage(getResources().getString(R.string.jpgSuccessful));
                sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            publishProgress(100);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            String toast = getFilesDir()+"/"+Globals.CLASSROOM_TABLE_NAME + ".jpg";
            SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
            biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);
            Toast errorImage = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
            errorImage.setGravity(Gravity.BOTTOM, 0, 40);
            errorImage.show();
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }
    }

    public void setFragments(TabLayout.Tab tab){
        if (tab.getPosition() == 0) {
            Globals.CLASSROOM_TABLE_NAME = "Classrooms";
            Globals.LAST_TAB = 0;
            ClassroomsListFragment myFragment = new ClassroomsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            //Set footer
            findViewById(R.id.footer).setVisibility(View.VISIBLE);
            findViewById(R.id.searchPanel).setVisibility(View.INVISIBLE);
            findViewById(R.id.footer).setBackgroundColor(Color.TRANSPARENT);
        } else if (tab.getPosition() == 1) {
            Globals.CLASSROOM_TABLE_NAME = "Subjects";
            Globals.LAST_TAB = 1;
            SubjectsListFragment myFragment = new SubjectsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            //Set footer
            findViewById(R.id.footer).setVisibility(View.VISIBLE);
            findViewById(R.id.searchPanel).setVisibility(View.INVISIBLE);
            findViewById(R.id.footer).setBackgroundColor(Color.TRANSPARENT);
        } else if (tab.getPosition() == 2) {
            Globals.CLASSROOM_TABLE_NAME = "Tutorships";
            Globals.LAST_TAB = 2;
            TutorshipsListFragment myFragment = new TutorshipsListFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            //Set footer
            findViewById(R.id.footer).setVisibility(View.VISIBLE);
            findViewById(R.id.searchPanel).setVisibility(View.VISIBLE);
            findViewById(R.id.footer).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (tab.getPosition() == 3) {
            Globals.LAST_TAB = 3;
            MapsActivity myFragment = new MapsActivity();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            //Set footer
            findViewById(R.id.footer).setVisibility(View.INVISIBLE);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
