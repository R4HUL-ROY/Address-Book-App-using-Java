package com.example.sqlite_addressbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editAddress, editContactNo, editEmailid;
    Button btnAdd, btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.edittext_name);
        editAddress = (EditText) findViewById(R.id.edittext_address);
        editContactNo = (EditText) findViewById(R.id.edittext_contact);
        editEmailid = (EditText) findViewById(R.id.edittext_emailid);
        btnAdd = (Button) findViewById(R.id.button);
        btnView = (Button) findViewById(R.id.button2);
        addData();
        getdata();
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void getdata(){
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getData();
                if(res.getCount() == 0){
                    showMessage("ERROR" , "NO DATA FOUND");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("name :" + res.getString(0) + "\n");
                    buffer.append("address :" + res.getString(1) + "\n");
                    buffer.append("contactno :" + res.getString(2) + "\n");
                    buffer.append("emailid :" + res.getString(3) + "\n\n");
                }
                showMessage("DATA", buffer.toString());
            }
        });
    }


    private void addData() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(),
                        editContactNo.getText().toString(),editEmailid.getText().toString());
                if(isInserted) {
                    Toast.makeText(getApplicationContext(), "inserted successfully", Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editAddress.setText("");
                    editContactNo.setText("");
                    editEmailid.setText("");
                }
                else Toast.makeText(getApplicationContext(), "FAILED TO INSERT" , Toast.LENGTH_LONG).show();

            }
        });
    }
}
