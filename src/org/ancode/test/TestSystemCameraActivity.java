package org.ancode.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TestSystemCameraActivity extends Activity {
	public static final int NONE=0;
	public static final int PHOTOHRAPH = 1; //拍照
	public static final int PHOTOZOOM = 2; //缩放
	public static final int PHOTORESOULT = 3; //结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	takePhoto();
            }
        });
    }
	
	private String mStrImgPath;
	private void takePhoto(){
		Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mStrImgPath = Environment.getExternalStorageDirectory().toString()+"/testcamera/";
		Log.v("test", mStrImgPath);
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
		File out = new File(mStrImgPath);
		if(!out.exists()){
			out.mkdirs();
		}
		out = new File(mStrImgPath, fileName);
		mStrImgPath = mStrImgPath + fileName;
		Uri uri = Uri.fromFile(out);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(imageCaptureIntent, PHOTOZOOM);
	}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.v("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
            }

            Toast.makeText(this, mStrImgPath, Toast.LENGTH_SHORT).show();
        }
    }
}
