package com.tranduythanh.bookstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.tranduythanh.adapter.AdvancedPublisherAdapter;
import com.tranduythanh.model.Publisher;
import java.util.ArrayList;

public class PublisherSettingCRUDActivity extends AppCompatActivity {
    Spinner spinnerPublisher;
    ArrayAdapter<Publisher> publisherAdapter;
    public static final String DATABASE_NAME = "BookStore.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    AdvancedPublisherAdapter advancedPublisherAdapter;
    ListView lvPublisher;
    EditText edtPublisherId, edtPublisherName;
    Button btnInsert,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_setting_crudactivity);
        addViews();
        loadPublisher();
        addEvents();
    }

    private void loadPublisherByPublisher(Publisher selectedPublisher) {
        ArrayList<Publisher> publishersby = new ArrayList<>();
        String sql = "SELECT * FROM Publisher WHERE publisherId = ?";
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery(sql, new String[]{selectedPublisher.getPublisherId()});
        while (cursor.moveToNext()) {
            String publisherId = cursor.getString(0);
            String publisherName = cursor.getString(1);

            Publisher publisher = new Publisher();
            publisher.setPublisherId(publisherId);
            publisher.setPublisherName(publisherName);

            publishersby.add(publisher);
        }
        cursor.close();
        selectedPublisher.setPublishers(publishersby);
        advancedPublisherAdapter.clear();
        advancedPublisherAdapter.addAll(publishersby);
    }
//
    private void loadPublisher() {
        // Tạo một đối tượng Publisher đại diện cho lựa chọn "All"
        Publisher allPublisher = new Publisher();
        allPublisher.setPublisherId("all");
        allPublisher.setPublisherName("All");

        publisherAdapter.add(allPublisher);

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

    private void addEvents() {
        spinnerPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Publisher selectedPublisher = publisherAdapter.getItem(position);
                if (selectedPublisher.getPublisherId().equals("all")) {
                    loadAllPublishers();
                } else {
                    loadPublisherByPublisher(selectedPublisher);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        lvPublisher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publisher publisher = advancedPublisherAdapter.getItem(position);
                edtPublisherId.setText(publisher.getPublisherId());
                edtPublisherName.setText(publisher.getPublisherName());
            }
        });
    }

    private void loadAllPublishers() {
        // Tải tất cả các nhà xuất bản từ cơ sở dữ liệu và cập nhật ListView
        ArrayList<Publisher> allPublishers = new ArrayList<>();
        // Thực hiện truy vấn SQL để lấy tất cả các nhà xuất bản
        String sql = "SELECT * FROM Publisher";
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String publisherId = cursor.getString(0);
            String publisherName = cursor.getString(1);
            Publisher publisher = new Publisher(publisherId, publisherName);
            allPublishers.add(publisher);
        }
        cursor.close();
        advancedPublisherAdapter.clear();
        advancedPublisherAdapter.addAll(allPublishers);
    }

    private void addViews() {
        spinnerPublisher=findViewById(R.id.spinnerPublisher);
        publisherAdapter=new ArrayAdapter<>(
                PublisherSettingCRUDActivity.this,
                android.R.layout.simple_spinner_item);
        publisherAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinnerPublisher.setAdapter(publisherAdapter);

        edtPublisherId=findViewById(R.id.edtPublisherId);
        edtPublisherName=findViewById(R.id.edtPublisherName);

        btnInsert=findViewById(R.id.btnInsert);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        lvPublisher=findViewById(R.id.lvPublisher);
        advancedPublisherAdapter=new AdvancedPublisherAdapter(
                PublisherSettingCRUDActivity.this,
                R.layout.advancedpublisher_item);
        lvPublisher.setAdapter(advancedPublisherAdapter);
    }
    public void processNewS(View view) {
        edtPublisherId.setText("");
        edtPublisherName.setText("");
        edtPublisherId.requestFocus();
    }
    public void processSaveS(View view) {
        ContentValues record = new ContentValues();
        String publisherId = edtPublisherId.getText().toString();
        String publisherName = edtPublisherName.getText().toString();
        record.put("publisherId", publisherId);
        record.put("publisherName", publisherName);
        long result = database.insert("Publisher", null, record);
        if (result > 0) {
            // Thêm đối tượng Publisher mới vào adapter
            Publisher newPublisher = new Publisher(publisherId, publisherName);
            publisherAdapter.add(newPublisher);

            // Cập nhật spinnerPublisher
            spinnerPublisher.setSelection(publisherAdapter.getCount() - 1);
            publisherAdapter.notifyDataSetChanged();

            Publisher publisher = new Publisher();
            publisher.setPublisherId(publisherId);
            loadPublisherByPublisher(publisher);
        }
    }

    public void processUpdateS(View view) {
        ContentValues record = new ContentValues();
        String publisherId = edtPublisherId.getText().toString();
        String publisherName = edtPublisherName.getText().toString();
        record.put("publisherName", publisherName);
        long result = database.update("Publisher", record, "publisherId=?", new String[]{publisherId});
        if (result > 0) {
            // Tạo một đối tượng Publisher mới với PublisherId không thay đổi và PublisherName đã cập nhật
            Publisher updatedPublisher = new Publisher();
            updatedPublisher.setPublisherId(publisherId);
            updatedPublisher.setPublisherName(publisherName);

            // Cập nhật spinnerPublisher để hiển thị đối tượng Publisher mới
            int selectedPosition = spinnerPublisher.getSelectedItemPosition();
            publisherAdapter.remove(publisherAdapter.getItem(selectedPosition));
            publisherAdapter.insert(updatedPublisher, selectedPosition);
            spinnerPublisher.setSelection(selectedPosition);
            publisherAdapter.notifyDataSetChanged();

            loadPublisherByPublisher(updatedPublisher);
        }
    }
    public void processDeleteS(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Xác nhận xóa, thực hiện xóa
                String publisherId = edtPublisherId.getText().toString();
                Publisher publisher = (Publisher) spinnerPublisher.getSelectedItem();
                long result = database.delete("Publisher", "publisherId=?", new String[]{publisherId});
                if (result > 0) {
                    loadPublisherByPublisher(publisher);
                    processNewS(view);
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

}
