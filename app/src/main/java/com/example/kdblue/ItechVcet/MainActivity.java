package com.example.kdblue.ItechVcet;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CollegeNewsLetter collegeNewsLetter = new CollegeNewsLetter(); // creating ProfessorAndStudentFragment Object

        FragmentManager fragmentManager = getSupportFragmentManager();/**  Return the FragmentManager for interacting with
         fragments associated with this fragment's activity. Basically, the difference is
         that Fragment's now have their own internal FragmentManager that can handle Fragments.
         **/

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();/**FragmentManager which is used to create
         transactions for adding, removing or replacing fragments. fragmentManager.beginTransaction();
         Start a series of edit operations on the Fragments associated with this FragmentManager.
         **/

        fragmentTransaction.add(R.id.fragment_container, collegeNewsLetter);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                AboutUs aboutUs = new AboutUs();
                FragmentManager f =getSupportFragmentManager();
                DialogFragment newFragment = aboutUs.newInstance();
                newFragment.show(f, "about_dialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
