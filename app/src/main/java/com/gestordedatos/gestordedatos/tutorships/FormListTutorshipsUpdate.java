package com.gestordedatos.gestordedatos.tutorships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.contentProvider.Contract;
import com.gestordedatos.gestordedatos.contentProvider.TutorshipProvider;
import com.gestordedatos.gestordedatos.pojos.Tutorship;

public class FormListTutorshipsUpdate extends AppCompatActivity {
    EditText editTextTeacher;
    EditText editTextClassroom;
    Spinner dropdownComienzo;
    Spinner dropdownFin;

    String teacher;
    String classroomName;
    String startTime;
    String endingTime;
    int tutorshipId;

    int checkonOptionsItemSelected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Globals.user.getTipoDeMiembro().equals("0")){
            setContentView(R.layout.activity_form_list_tutorships);
        }
        else{
            setContentView(R.layout.activity_form_list_tutorships_id);
        }

        editTextTeacher = (EditText) findViewById(R.id.editTextTeacher);
        editTextClassroom = (EditText) findViewById(R.id.editTextClassroom);

        tutorshipId = this.getIntent().getExtras().getInt(Contract.Tutorship._ID);
        Tutorship tutorship = TutorshipProvider.readRecord(getContentResolver(),tutorshipId);

        editTextTeacher.setText(tutorship.getName());
        editTextClassroom.setText(tutorship.getClassroom());

        dropdownComienzo = findViewById(R.id.spinnerComienzo);
        String[] itemsDropdownComienzo = new String[]{"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDropdownComienzo);
        dropdownComienzo.setAdapter(adapter1);

        if (tutorship.getStartTime() != null) {
            int spinnerPosition = adapter1.getPosition(tutorship.getStartTime());
            dropdownComienzo.setSelection(spinnerPosition);
        }

        dropdownFin = findViewById(R.id.spinnerFin);
        String[] itemsDropdownFin = new String[]{"08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDropdownFin);
        dropdownFin.setAdapter(adapter2);

        if (tutorship.getEndingTime() != null) {
            int spinnerPosition = adapter2.getPosition(tutorship.getEndingTime());
            dropdownFin.setSelection(spinnerPosition);
        }
    }

    public void validar(){
        editTextTeacher = (EditText) findViewById(R.id.editTextTeacher);
        editTextClassroom = (EditText) findViewById(R.id.editTextClassroom);

        editTextTeacher.setError(null);
        editTextClassroom.setError(null);

        teacher = editTextTeacher.getText().toString();
        classroomName = editTextClassroom.getText().toString();

        if(teacher.trim().equals("")){
            editTextTeacher.setError(getString(R.string.errorEmptyTeacher));
            editTextTeacher.requestFocus();
            return;
        }
        if(classroomName.trim().equals("")){
            editTextClassroom.setError(getString(R.string.errorEmptyClassroom));
            editTextClassroom.requestFocus();
            return;
        }

        startTime = dropdownComienzo.getSelectedItem().toString();
        endingTime = dropdownFin.getSelectedItem().toString();

        int startTime_hours = Integer.parseInt(startTime.substring(0,2));
        int startTime_minutes = Integer.parseInt(startTime.substring(3,5));
        int endingTime_hours = Integer.parseInt(endingTime.substring(0,2));
        int endingTime_minutes = Integer.parseInt(endingTime.substring(3,5));

        //Validating time from spinners
        if(startTime_hours>endingTime_hours || startTime_hours==endingTime_hours && startTime_minutes>=endingTime_minutes){
            String toast = getResources().getString(R.string.errorTime);
            //Dar formato al texto
            SpannableStringBuilder biggerText = new SpannableStringBuilder(toast);
            biggerText.setSpan(new RelativeSizeSpan(1.5f), 0, toast.length(), 0);

            Toast errorSpinner = Toast.makeText(getApplicationContext(),biggerText,Toast.LENGTH_LONG);
            errorSpinner.setGravity(Gravity.BOTTOM, 0, 40);

            errorSpinner.show();

            dropdownComienzo.requestFocus();
            return;
        }

        EditText editTextClassroomID = (EditText) findViewById(R.id.editTextClassroomID);

        int classroomID=0;
        try {
            classroomID=Integer.parseInt(editTextClassroomID.getText().toString());
        }
        catch(NumberFormatException e){
            editTextClassroomID.setError(getString(R.string.errorSubjectReference));
            editTextClassroomID.requestFocus();
            return;
        }

        //Update
        Tutorship tutorship = new Tutorship(tutorshipId,teacher,classroomName,startTime,endingTime,classroomID);
        try{
            //TutorshipProvider.updateRecord(getContentResolver(),tutorship);
            TutorshipProvider.updateConBitacora(getContentResolver(),tutorship);
        }
        catch(SQLException e){
            editTextClassroom.setError(getString(R.string.errorClassroomConstraint));
            editTextClassroom.requestFocus();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkonOptionsItemSelected = 1;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_menu, menu);

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
        if(id == R.id.action_save){
            if(checkonOptionsItemSelected==0) {
                validar();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showHelp() {
        new AlertDialog.Builder(FormListTutorshipsUpdate.this)
                .setTitle(getResources().getString(R.string.helpTitle))
                .setMessage(getResources().getString(R.string.helpFormListTutorships))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
