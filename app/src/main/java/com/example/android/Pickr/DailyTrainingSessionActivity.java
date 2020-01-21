package com.example.android.Pickr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.InputStream;
import java.util.ArrayList;

public class DailyTrainingSessionActivity extends AppCompatActivity {

    TextView jaSentence;
    TextView translation;
    TextView hint;
    TextView id;
    TextView totalNumberOfCards;
    Button btnLoadFile;
    Button btnGetNewCollection;
    Button btnNextWord;
    Switch switchShowJA;
    Switch switchShowTranslation;
    Switch switchShowHint;
    Spinner spinner;

    //    private ArrayList<Sentence> fullList = new ArrayList<Sentence>();
    public final SentenceRoomDatabase appDb = SentenceRoomDatabase.getInstance(this);
    private ArrayList<Sentence> shortList;
    private Sentence currentSentence;


    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training_session);

        jaSentence = findViewById(R.id.jaSentence);
        translation = findViewById(R.id.translation);
        hint = findViewById(R.id.hint);
        id = findViewById(R.id.id);
        totalNumberOfCards = findViewById(R.id.totalNumberOfCards);
        btnLoadFile = findViewById(R.id.loadFile);
        btnGetNewCollection = findViewById(R.id.getNewCollection);
        btnNextWord = findViewById(R.id.nextWord);
        switchShowJA = findViewById(R.id.showJAswitch);
        switchShowTranslation = findViewById(R.id.showTranslationSwitch);
        switchShowHint = findViewById(R.id.showHintSwitch);

        if (appDb.sentenceDao().getAllSentences().isEmpty()) {
            btnLoadFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readExcelFileFromAssets();
                    totalNumberOfCards.setText(appDb.sentenceDao().getAllSentences().size() + "");
                    btnLoadFile.setEnabled(false);
                    btnGetNewCollection.setEnabled(true);
                    btnNextWord.setEnabled(true);
                }
            });
        } else {
            btnLoadFile.setText("Refresh");
            btnLoadFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readExcelFileFromAssets();
                    totalNumberOfCards.setText(appDb.sentenceDao().getAllSentences().size() + "");
                }
            });
        }

        btnGetNewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewCollection();
                goToNextWord();
            }
        });

        btnNextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextWord();
            }
        });

        switchShowJA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSentence != null) {
                    jaSentence.setText((switchShowJA.isChecked()) ? currentSentence.getJaSentence() : "");
                }
            }
        });

        switchShowTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSentence != null) {
                    translation.setText((switchShowTranslation.isChecked()) ? currentSentence.getTranslation() : "");
                }
            }
        });

        switchShowHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSentence != null) {
                    hint.setText((switchShowHint.isChecked()) ? currentSentence.getHint() : "");
                }
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_item, shortList);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner sItems = (Spinner) findViewById(R.id.spinner1);
//        sItems.setAdapter(adapter);

    }


    public void readExcelFileFromAssets() {
        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = getAssets();
            //  open excel sheet
            myInput = assetManager.open("BST Câu.xlsx");
            OPCPackage pkg = OPCPackage.open(myInput);
            XSSFWorkbook sourceWb = new XSSFWorkbook(pkg);
            // Get the first sheet from workbook
            XSSFSheet sheet = sourceWb.getSheetAt(0);
            // We now need something to iterate through the cells.
            int rowCount = 1;
            XSSFRow row = sheet.getRow(rowCount);
            while (!row.getCell(1).getStringCellValue().isEmpty()) {
                String id = row.getCell(0).getNumericCellValue() + "";
                String jaSentence = row.getCell(1).getStringCellValue();
                String translation = row.getCell(2).getStringCellValue();
                String hint = row.getCell(3).getStringCellValue();
//                fullList.add(new Sentence(id + "", jaSentence, translation, hint));
                appDb.sentenceDao().insertSentence(new Sentence(id, jaSentence, translation, hint));
                row = sheet.getRow(++rowCount);
            }
            getNewCollection();
            goToNextWord();
        } catch (Exception e) {
            Log.e("aaa", "error " + e.toString());
        }
    }

    public void getNewCollection() {
        int appDbSize = appDb.sentenceDao().getAllSentences().size();
        shortList = new ArrayList<Sentence>();
        for (int i = 0; i < 20; i++) {
            shortList.add(appDb.sentenceDao().findSentenceById(appDbSize * Math.random() + ""));
        }
    }

    public void goToNextWord() {
        currentSentence = shortList.get((int) (Math.random() * shortList.size()));
        jaSentence.setText((switchShowJA.isChecked()) ? currentSentence.getJaSentence() : "");
        translation.setText((switchShowTranslation.isChecked()) ? currentSentence.getTranslation() : "");
        hint.setText((switchShowHint.isChecked()) ? currentSentence.getHint() : "");
        id.setText(currentSentence.getSid());
    }


}