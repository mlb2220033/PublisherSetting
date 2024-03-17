package com.tranduythanh.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tranduythanh.adapter.AdvancedBookAdapter;
import com.tranduythanh.model.Book;
import com.tranduythanh.model.Publisher;

import java.util.ArrayList;

public class PublisherBookSqliteCRUDActivity extends AppCompatActivity {
    Spinner spinnerPublisher;
    ArrayAdapter<Publisher> publisherAdapter;
    public static final String DATABASE_NAME = "BookStore.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    ListView lvBook;
    AdvancedBookAdapter advancedBookAdapter;
    EditText edtBookId, edtBookName, edtUnitPrice;
    Button btnInsert,btnUpdate,btnDelete;

    Book selectedBook=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_book_sqlite_crudactivity);
        addViews();
        loadPublisher();
        addEvents();
    }
    private void addEvents() {
        spinnerPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Publisher selectedPublish=publisherAdapter.getItem(position);
                loadBookByPublisher(selectedPublish);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = advancedBookAdapter.getItem(position);
                edtBookId.setText(book.getBookId());
                edtBookName.setText(book.getBookName());
                edtUnitPrice.setText(book.getUnitPrice()+"");
            }
        });

    }

    private void loadBookByPublisher(Publisher selectedPublisher) {
        //Remember:
        //(1) 1 Publisher has many books
        //(2) 1 book belong to a Publisher
        ArrayList<Book> books=new ArrayList<>();
        String sql="SELECT * from Book WHERE publisherId = '" + selectedPublisher.getPublisherId()+ "'";
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            String bookId=cursor.getString(0);
            String bookName=cursor.getString(1);
            float unitPrice=cursor.getFloat(2);
            String description=cursor.getString(3);

            Book book=new Book();
            book.setBookId(bookId);
            book.setBookName(bookName);
            book.setUnitPrice(unitPrice);
            book.setDescription(description);
            book.setPublisher(selectedPublisher);
            books.add(book);
        }
        cursor.close();
        selectedPublisher.setBooks(books);
        advancedBookAdapter.clear();
        advancedBookAdapter.addAll(books);
    }

    private void loadPublisher() {
        database = openOrCreateDatabase(DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM Publisher",
                null);
        while(cursor.moveToNext()){
            String publisherId = cursor.getString(0);
            String publisherName=cursor.getString(1);

            Publisher publisher=new Publisher(publisherId,publisherName);
            publisherAdapter.add(publisher);
        }
        cursor.close();
    }

    private void addViews() {
        spinnerPublisher=findViewById(R.id.spinnerPublisher);
        publisherAdapter=new ArrayAdapter<>(
                PublisherBookSqliteCRUDActivity.this,
                android.R.layout.simple_spinner_item);
        publisherAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinnerPublisher.setAdapter(publisherAdapter);

        edtBookId=findViewById(R.id.edtPublisherId);
        edtBookName=findViewById(R.id.edtPublisherName);
        edtUnitPrice=findViewById(R.id.edtUnitPrice);
        btnInsert=findViewById(R.id.btnInsert);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        lvBook=findViewById(R.id.lvPublisher);
        advancedBookAdapter=new AdvancedBookAdapter(
                PublisherBookSqliteCRUDActivity.this,
                R.layout.advancedbook_item);
        lvBook.setAdapter(advancedBookAdapter);


    }

    public void processNew(View view) {
        edtBookId.setText("");
        edtBookName.setText("");
        edtUnitPrice.setText("");
        edtBookId.requestFocus();
    }

    public void processSave(View view) {
        ContentValues record=new ContentValues();
        record.put("bookId",edtBookId.getText().toString());
        record.put("bookName",edtBookName.getText().toString());
        record.put("unitPrice",Float.parseFloat(edtUnitPrice.getText().toString()));
        Publisher publisher= (Publisher) spinnerPublisher.getSelectedItem();
        record.put("publisherId",publisher.getPublisherId());
        long result=database.insert("Book",null,record);
        if(result>0)
            loadBookByPublisher(publisher);
    }

    public void processUpdate(View view) {
        ContentValues record=new ContentValues();
        record.put("bookName",edtBookName.getText().toString());
        record.put("unitPrice",Float.parseFloat(edtUnitPrice.getText().toString()));
        Publisher publisher= (Publisher) spinnerPublisher.getSelectedItem();
        String bookId=edtBookId.getText().toString();
        long result=database.update("Book", record, "BookId=?", new String[]{bookId});
        //("Book", record, "BookId=?", new String[]{bookId});
        // ĐỐi số thứ 1: Bảng cần tham chiếu
        // ĐỐi số thứ 2: Cột cần thay đổi
        // ĐỐi số thứ 3: Điều kiện cập nhật

        if(result>0)
        {
            loadBookByPublisher(publisher);
        }
    }


    public void processDelete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Xác nhận xóa, thực hiện xóa
                String bookId = edtBookId.getText().toString();
                Publisher publisher = (Publisher) spinnerPublisher.getSelectedItem();
                long result = database.delete("Book", "BookId=?", new String[]{bookId});
                if (result > 0) {
                    loadBookByPublisher(publisher);
                    processNew(view);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Không làm gì nếu không muốn xóa
            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.advanced_publisher_crud,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnuPublisherSetting)
        {
            Intent intent=new Intent(PublisherBookSqliteCRUDActivity.this, PublisherSettingCRUDActivity.class);
            startActivityForResult(intent,1);
        }
        else if(item.getItemId()==R.id.mnuHelp)
        {
            String url="https://itbclub.uel.edu.vn/";
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri=Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.mnuAbout)
        {

        }
        return super.onOptionsItemSelected(item);
    }

}