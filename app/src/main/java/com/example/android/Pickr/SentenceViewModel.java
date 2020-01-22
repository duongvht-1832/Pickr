package com.example.android.Pickr;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SentenceViewModel extends AndroidViewModel {

    private SentenceRepository mRepository;

    private LiveData<List<Sentence>> mAllSentences;

    public SentenceViewModel(Application application) {
        super(application);
        mRepository = new SentenceRepository(application);
        mAllSentences = mRepository.getAllSentences();
    }

    LiveData<List<Sentence>> getAllSentences() {
        return mAllSentences;
    }



    public void insert(Sentence sentence) {
        mRepository.insert(sentence);
    }
}