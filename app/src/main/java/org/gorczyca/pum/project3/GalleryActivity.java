package org.gorczyca.pum.project3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private LinearLayout layoutThumbnails;
    private ImageView imageSelected;
    private ImageView imageThumbnail;
    private TextView textImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_3_gallery);
        setTitle(R.string.gallery);
        bindIds();
        getPermissionForExternalFilesRead();
    }

    private void bindIds() {
        layoutThumbnails = findViewById(R.id.layout_thumbnails);
        imageSelected = findViewById(R.id.image_selected);
        imageThumbnail = findViewById(R.id.image_thumbnail);
        textImageName = findViewById(R.id.text_thumbnail_name);

    }

    private void getPermissionForExternalFilesRead() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            loadImages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionForExternalFilesRead();
        } else {
            loadImages();
        }
    }

    private void loadImages() {

        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (isSDPresent) {

            String[] imagePaths = getMax20ImagesPaths();

            if (imagePaths.length > 0) {

                for (int i = 0; i < imagePaths.length; i++) {

                    Drawable drawable = Drawable.createFromPath(imagePaths[i]);

                    View singleThumbnail = getLayoutInflater().inflate(R.layout.layout_image_thumbnail, null);
                    singleThumbnail.setId(i);
                    imageThumbnail = singleThumbnail.findViewById(R.id.image_thumbnail);
                    textImageName = singleThumbnail.findViewById(R.id.text_thumbnail_name);
                    imageThumbnail.setImageDrawable(createThumbnail(imagePaths[i]));
                    textImageName.setText(imagePaths[i].substring(imagePaths[i].lastIndexOf(File.separator) + 1));
                    int whichPath = i;
                    singleThumbnail.setOnClickListener(v -> {
                        imageSelected.setImageDrawable(Drawable.createFromPath(imagePaths[whichPath]));
                    });
                    layoutThumbnails.addView(singleThumbnail);

                }
            } else {
                Toast.makeText(this, "Nie znaleziono żadnych zdjęć", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Brak karty SD", Toast.LENGTH_SHORT).show();
        }

    }

    public String[] getMax20ImagesPaths() {


        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        String[] arrPath = new String[0];
        if(cursor != null) {
            int count = Math.min(cursor.getCount(), 20);
            arrPath = new String[count];

            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                arrPath[i] = cursor.getString(dataColumnIndex);
            }
            cursor.close();
        }

        return arrPath;
    }

    private Drawable createThumbnail(String filePath){
        File file = new File(filePath);

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);

        int desiredWidth = 400;
        int desiredHeight = 300;
        float widthScale = (float)bitmapOptions.outWidth/desiredWidth;
        float heightScale = (float)bitmapOptions.outHeight/desiredHeight;
        float scale = Math.min(widthScale, heightScale);

        int sampleSize = 1;
        while (sampleSize < scale) {
            sampleSize *= 2;
        }
        bitmapOptions.inSampleSize = sampleSize;
        bitmapOptions.inJustDecodeBounds = false;

        Bitmap thumbnail = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
        return new BitmapDrawable(getResources(), thumbnail);
    }
}
