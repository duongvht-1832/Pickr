package com.example.android.Pickr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SentenceListAdapter extends RecyclerView.Adapter<SentenceListAdapter.SentenceViewHolder> {

    class SentenceViewHolder extends RecyclerView.ViewHolder {
        private final TextView japaneseSentenceItemView;
        private final TextView translationItemView;
        private final TextView idItemView;


        private SentenceViewHolder(View itemView) {
            super(itemView);
            japaneseSentenceItemView = itemView.findViewById(R.id.japaneseTextView);
            translationItemView = itemView.findViewById(R.id.translationTextView);
            idItemView = itemView.findViewById(R.id.idTextView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Sentence> mSentences; // Cached copy of sentences

    SentenceListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public SentenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SentenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SentenceViewHolder holder, int position) {
        if (mSentences != null) {
            Sentence current = mSentences.get(position);
            holder.japaneseSentenceItemView.setText(current.getJaSentence());
            holder.translationItemView.setText(current.getTranslation());
            holder.idItemView.setText(current.getId());
        } else {
            // Covers the case of data not being ready yet.
            holder.japaneseSentenceItemView.setText("No Sentence");
            holder.translationItemView.setText("No Translation");
            holder.idItemView.setText("No ID");
        }
    }

    void setSentences(List<Sentence> sentences) {
        mSentences = sentences;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mSentences has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mSentences != null)
            return mSentences.size();
        else return 0;
    }
}