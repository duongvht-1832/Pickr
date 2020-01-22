package com.example.android.Pickr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class BrowseAllSentences extends AppCompatActivity {

    public static final int NEW_SENTENCE_ACTIVITY_REQUEST_CODE = 1;

    private SentenceViewModel mSentenceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_all_sentences);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final SentenceListAdapter adapter = new SentenceListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mSentenceViewModel = new ViewModelProvider(this).get(SentenceViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedSentences.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mSentenceViewModel.getAllSentences().observe(this, new Observer<List<Sentence>>() {
            @Override
            public void onChanged(@Nullable final List<Sentence> sentences) {
                // Update the cached copy of the sentences in the adapter.
                adapter.setSentences(sentences);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseAllSentences.this, NewSentenceActivity.class);
                startActivityForResult(intent, NEW_SENTENCE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_SENTENCE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Sentence sentence = new Sentence(data.getStringExtra(NewSentenceActivity.EXTRA_REPLY));
            mSentenceViewModel.insert(sentence);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}