package com.example.android.practiceset2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.poi.ss.usermodel.Workbook;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    TextView jaSentence;
    TextView translation;
    TextView hint;
    TextView id;
    Button btnLoadFile;
    Button btnGetNewCollection;
    Button btnNextWord;
    Switch switchShowJA;
    Spinner spinner;

    private ArrayList<Record> fullList = new ArrayList<Record>();
    private ArrayList<Record> shortList;
    private Record currentRecord;


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
        setContentView(R.layout.activity_main);

        jaSentence = findViewById(R.id.jaSentence);
        translation = findViewById(R.id.translation);
        hint = findViewById(R.id.hint);
        id = findViewById(R.id.id);
        btnLoadFile = findViewById(R.id.loadFile);
        btnGetNewCollection = findViewById(R.id.getNewCollection);
        btnNextWord = findViewById(R.id.nextWord);
        switchShowJA = findViewById(R.id.showJAswitch);
        spinner = findViewById(R.id.sort_by_spinner);


        btnLoadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readExcelFileFromAssets();
                goToNextWord();
                btnLoadFile.setEnabled(false);
                btnGetNewCollection.setEnabled(true);
                btnNextWord.setEnabled(true);
            }
        });

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
                if (currentRecord != null){
                    jaSentence.setText((switchShowJA.isChecked()) ? currentRecord.getJaSentence() : "");
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
                int id = (int)row.getCell(0).getNumericCellValue();
                String jaSentence = row.getCell(1).getStringCellValue();
                String translation = row.getCell(2).getStringCellValue();
                String hint = row.getCell(3).getStringCellValue();
                fullList.add(new Record(id + "", jaSentence, translation, hint));
                row = sheet.getRow(++rowCount);
            }
            getNewCollection();
            goToNextWord();
        } catch (Exception e) {
            Log.e("aaa", "error " + e.toString());
        }
    }

    public void getNewCollection() {
        Collections.shuffle(fullList);
        shortList = new ArrayList<Record>();
        for (int i = 0; i < 20; i++) {
            shortList.add(fullList.get(i));
        }
    }

    public void goToNextWord() {
        currentRecord = shortList.get((int) (Math.random() * shortList.size()));
        jaSentence.setText((switchShowJA.isChecked()) ? currentRecord.getJaSentence() : "");
        translation.setText(currentRecord.getTranslation());
        hint.setText(currentRecord.getHint());
        id.setText("Sentence ID: " + currentRecord.getID());
    }


}