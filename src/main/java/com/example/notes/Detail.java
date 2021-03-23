package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        TextView details = findViewById(R.id.noteDesc);
        details.setText(note.getContent());
        details.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actiondelete();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit) {
            Intent i = new Intent(this, Edit.class);
            i.putExtra("ID", id);
            startActivity(i);
        }
            else if (item.getItemId() == R.id.share)
            {
//                Toast.makeText(getApplicationContext(), "Share Text", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String header="this is app";
                String body="This app is cool";
                intent.putExtra(Intent.EXTRA_SUBJECT,header);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                CharSequence title;
                startActivity(Intent.createChooser(intent,title="share via"));

            }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void actiondelete()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)

//set icon
               // .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Delete Note?")
//set message
                .setMessage("Are you sure you want to delete this Note")
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        SimpleDatabase db = new SimpleDatabase(getApplicationContext());
                        db.deleteNote(id);
                        Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                        goToMain();
                        finish();
                    }
                })
//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        // Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                    }
                })
                .show();

    }
}
