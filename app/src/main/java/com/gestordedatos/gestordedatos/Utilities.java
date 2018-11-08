package com.gestordedatos.gestordedatos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utilities {
    private static FileOutputStream fos;

    public static void loadImageFromStorage(Context context, String image, ImageView img) throws FileNotFoundException {
            //File f=new File(path, "profile.jpg");
            File f = context.getFileStreamPath(image);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img = (ImageView) findViewById(R.id.imgPicker);
            img.setImageBitmap(b);
    }

    public static void storeImage(Bitmap image, Context context, String fileName) throws IOException {
        /*File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("Error: ",
                    "creating media file, check storage permissions: ");// e.getMessage());
            return;
        }*/
        try {
            //FileOutputStream fos = new FileOutputStream(pictureFile);
            fos = context.openFileOutput(fileName,context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("Error: ", "File not found: " + e.getMessage());
        }
    }

    public static void deleteImage(Context context, String image){
        File fdelete = new File(String.valueOf(context.getFileStreamPath(image)));
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + context.getFileStreamPath(image));
            } else {
                System.out.println("file not Deleted :" + context.getFileStreamPath(image));
            }
        }
    }

    public static boolean isExternalStorageWritable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //EXTERNAL MEMORY IS WRITABLE
            return true;
        }
        else {
            //EXTERNAL MEMORY IS NOT WRITABLE
            return false;
        }
    }

    public static File storeImageInExternelMemory(Bitmap image, Context context, String fileName){
        if(isExternalStorageWritable()) {
            try {
                    fos = new FileOutputStream(context.getExternalFilesDir(null).getAbsolutePath()+"/"+fileName);
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                }
                catch (FileNotFoundException ex)
                {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return null;
        }
        else{
            return null;
        }
    }
}
