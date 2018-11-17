package com.gestordedatos.gestordedatos.subjects;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.contentProvider.SubjectProvider;
import com.gestordedatos.gestordedatos.contentProvider.Contract;

public class ListSubjects extends AppCompatActivity {
    static View rowSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subjects);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Including list - Content Provider
        ((Globals) getApplicationContext()).CLASSROOM_TABLE_NAME = "Subjects";
        SubjectsListFragment SubjectsListFragment = new SubjectsListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_subjects,SubjectsListFragment);
        transaction.commit();

        //Floating button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.action_insert_alternative);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(getBaseContext(),FormListSubjects.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insert_menu, menu);
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
            Intent intent = new Intent(this,FormListSubjects.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_delete){
            //Restore toolbar
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.insert_menu);
            //Delete record
            SubjectProvider.deleteRecord(this.getContentResolver(),(Integer) rowSelected.getTag());
            return true;
        }
        else if(id == R.id.action_update){
            Intent intent = new Intent(this,FormListSubjectsUpdate.class);

            intent.putExtra(Contract.Subject._ID,(Integer) rowSelected.getTag());

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        new AlertDialog.Builder(ListSubjects.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpListClassrooms))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void setRowSelected(View rowSelected){
        this.rowSelected=rowSelected;
    }
}
