package com.gestordedatos.gestordedatos.classrooms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.gestordedatos.gestordedatos.R;
import com.gestordedatos.gestordedatos.contentProvider.Contract;

/**
 * A fragment representing a list of Items.
 */
public class ClassroomsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    ClassroomsCursorAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    ActionMode nActionMode;
    View rowSelected;

    Handler handler = new Handler();//Timer to restore insert_menu

    public static ClassroomsListFragment newInstance() {
        ClassroomsListFragment f = new ClassroomsListFragment();
        return f;
    }

    /**
     * When creating, retrieve this instance's classroomName from its arguments.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.add(Menu.NONE, G.INSERTAR, Menu.NONE, "INSERTAR");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_nuevo_registro);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.INSERTAR:
                Intent intent = new Intent(getActivity(), CicloDetalleActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance classroomName.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.i(LOGTAG, "onCreateView");
        View v = inflater.inflate(R.layout.list_fragment_classrooms, container, false);

        mAdapter = new ClassroomsCursorAdapter(getActivity());
        setListAdapter(mAdapter);

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCallbacks = this;
        getLoaderManager().initLoader(0, null, mCallbacks);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(nActionMode!=null){
                    return false;
                }

                //nActionMode = getActivity().startActionMode(nActionModeCallback);
                view.setSelected(true);

                //Clear the toolbar and add a new menu
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.delete_modify_menu);

                //pass rowSelected to ListClassrooms, using this variable in onOptionsItemSelected
                rowSelected = view;
                ListClassrooms list = new ListClassrooms();
                list.setRowSelected(rowSelected);

                //Timer to restore toolbar
                handler.postDelayed(restoreMenu, 5000);
                return true;
            }
        });
    }

    // Restore insert_menu
    Runnable restoreMenu = new Runnable() {
        @Override
        public void run() {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.insert_menu);
        }
    };

    /*ActionMode.Callback nActionModeCallback = new ActionMode.Callback(){
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            nActionModeCallback = null;
        }
    };*/

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        String columns[] = new String[] { Contract.Classroom._ID,
                Contract.Classroom.classroomName,
                Contract.Classroom.subject
        };

        Uri baseUri = Contract.Classroom.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        String selection = null;

        return new CursorLoader(getActivity(), baseUri,
                columns, selection, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)

        Uri laUriBase = Uri.parse("content://"+Contract.AUTHORITY+"/Classrooms");
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    public class ClassroomsCursorAdapter extends CursorAdapter {
        public ClassroomsCursorAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int ID = cursor.getInt(cursor.getColumnIndex(Contract.Classroom._ID));
            String name = cursor.getString(cursor.getColumnIndex(Contract.Classroom.classroomName));
            String abbreviation = String.valueOf(name.charAt(0));
            String subject = cursor.getString(cursor.getColumnIndex(Contract.Classroom.subject));

            //Set table values
            TextView textviewNameTitle = (TextView) view.findViewById(R.id.textviewTitle_classroom_list_item_name);
            textviewNameTitle.setText(getResources().getString(R.string.classroomLabel)+" ");
            TextView textviewName = (TextView) view.findViewById(R.id.textview_classroom_list_item_name);
            textviewName.setText(name);

            TextView textviewSubject = (TextView) view.findViewById(R.id.textview_classroom_list_item_subject);
            textviewSubject.setText(subject);
            TextView textviewSubjectTitle = (TextView) view.findViewById(R.id.textviewTitle_classroom_list_item_subject);
            textviewSubjectTitle.setText(getResources().getString(R.string.subjectLabel)+" ");

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getColor(abbreviation); //Genera un color según el nombre
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(abbreviation.substring(0,1), color);

            ImageView image = (ImageView) view.findViewById(R.id.image_view);
            image.setImageDrawable(drawable);

            view.setTag(ID);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.list_item_classrooms, parent, false);
            bindView(v, context, cursor);
            return v;
        }
    }
}
