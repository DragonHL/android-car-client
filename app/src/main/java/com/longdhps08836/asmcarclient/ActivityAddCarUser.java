package com.longdhps08836.asmcarclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.longdhps08836.asmcarclient.DAO.CarUserDAO;
import com.longdhps08836.asmcarclient.Model.Car_user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ActivityAddCarUser extends AppCompatActivity {

    ImageView imgCarUser;
    EditText edtCarName, edtCarInformation;
    Button btnAddCarUser, btnCancelCarUser;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    byte[] bytes;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_user);

        connectLayout();
        init();


    }

    private void init() {

        imgCarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        btnAddCarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String carName = edtCarName.getText().toString().trim();
                String vehicleMaintenance = edtCarInformation.getText().toString().trim();

                if (bitmap != null && !carName.isEmpty() && !vehicleMaintenance.isEmpty()) {

                    bytes = getByteArrayFromBitmap(bitmap);
                    Car_user car_user = new Car_user(carName, vehicleMaintenance, bytes);
                    CarUserDAO carUserDAO = new CarUserDAO(ActivityAddCarUser.this);
                    carUserDAO.insert(car_user);

                    Fragment_Profile_Client.listCarUser.clear();
                    Fragment_Profile_Client.listCarUser.addAll(carUserDAO.getAllCarInfor());
                    finish();
                    Toast.makeText(ActivityAddCarUser.this, "Add Success", Toast.LENGTH_SHORT).show();
                } else if (bitmap == null) {
                    Toast.makeText(ActivityAddCarUser.this, "Please select a picture", Toast.LENGTH_SHORT).show();
                } else if (carName.equals("")) {
                    Toast.makeText(ActivityAddCarUser.this, "Car Name  may not be null", Toast.LENGTH_SHORT).show();
                }else if(vehicleMaintenance.equals("")) {
                    Toast.makeText(ActivityAddCarUser.this, "Car vehicleMaintenance may not be null", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancelCarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void connectLayout() {
        imgCarUser = findViewById(R.id.imgCarUser);
        edtCarName = findViewById(R.id.edtCarName);
        edtCarInformation = findViewById(R.id.edtCarInformation);
        btnAddCarUser = findViewById(R.id.btnAddCarUser);
        btnCancelCarUser = findViewById(R.id.btnCancelCarUser);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK) {

            try {
                Uri imageUri = data.getData();
                //xử lý lấy ảnh chọn từ điện thoại:
                InputStream is = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(is);
//                bitmap = resize(bitmap, 150, 120);
                imgCarUser.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        // hàm này ta có thể lấy ra được 1 byteArray từ 1 cái bitmap
        // hàm này nó đổi 1 cái bitmap thành 1 mảng byte và từ  mảng byte này ta sẽ gửi lên server cho nodejs sử lý
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_FOLDER);
    }


    // tạo ra 1 bitmap đã được thu gọn và vẫn giữ được chiều rộng chiều dài của ảnh
    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

}
