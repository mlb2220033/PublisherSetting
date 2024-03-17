package com.tranduythanh.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tranduythanh.adapter.BookAdapter;
import com.tranduythanh.model.Book;

public class AdvancedListBookObjectActivity extends AppCompatActivity {

    EditText edtBookId;
    EditText edtBookName;
    EditText edtUnitPrice;

    Button btnInsert,btnUpdate,btnDelete;

    ListView lvBook;
    //ArrayAdapter<Book> bookAdapter;
    BookAdapter bookAdapter;

    Book selectedBook=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_list_book_object);
        addViews();
        makeFakeData();
        addEvents();
    }

    private void addEvents() {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book=new Book();
                String bookId=edtBookId.getText().toString();
                String bookName=edtBookName.getText().toString();
                float unitPrice=Float.parseFloat(edtUnitPrice.getText().toString());
                book.setBookId(bookId);
                book.setBookName(bookName);
                book.setUnitPrice(unitPrice);

                bookAdapter.add(book);
                edtBookId.setText("");
                edtBookName.setText("");
                edtUnitPrice.setText("");
                edtBookId.requestFocus();
            }
        });

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBook=bookAdapter.getItem(i);
                edtBookId.setText(selectedBook.getBookId());
                edtBookName.setText(selectedBook.getBookName());
                edtUnitPrice.setText(selectedBook.getUnitPrice()+"");
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedBook==null)return;
                String bookId=edtBookId.getText().toString();
                String bookName=edtBookName.getText().toString();
                float unitPrice=Float.parseFloat(edtUnitPrice.getText().toString());
                selectedBook.setBookId(bookId);
                selectedBook.setBookName(bookName);
                selectedBook.setUnitPrice(unitPrice);
                bookAdapter.notifyDataSetChanged();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDelete();
            }
        });
    }

    private void processDelete() {
        if(selectedBook==null)return;
        bookAdapter.remove(selectedBook);
        selectedBook=null;
    }

    private void makeFakeData() {
        Book b1=new Book("Book1","Basic Mobile Programming",100);
        bookAdapter.add(b1);

        bookAdapter.add(new Book("Book2","Machine Learning for Business",300));
        bookAdapter.add(new Book("Book3","Algorithms",120));
    }

    private void addViews() {
        edtBookId=findViewById(R.id.edtPublisherId);
        edtBookName=findViewById(R.id.edtPublisherName);
        edtUnitPrice=findViewById(R.id.edtUnitPrice);
        btnInsert=findViewById(R.id.btnInsert);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        lvBook=findViewById(R.id.lvPublisher);
        /*bookAdapter=new ArrayAdapter<>(
                AdvancedListBookObjectActivity.this,
                android.R.layout.simple_list_item_1
        );*/
        bookAdapter=new BookAdapter(
                AdvancedListBookObjectActivity.this,
                R.layout.book_item
                );
        lvBook.setAdapter(bookAdapter);
    }
}