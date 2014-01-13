package golf.club.app.moncompte;

import java.io.File;


import golf.club.app.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

public class ChoosePhoto extends Activity implements OnClickListener {

	ChoosePhoto m;

	ImageView imCamera;
	ImageView imgGalleryChoose;
	Intent takePicture;

	static Uri capturedImageUri=null;

	final int TAKE_PICTURE = 1;
	private Uri imageUri;

	private static final int SELECT_PICTURE = 2;

	TextView txDepuisLaCamera;
	TextView txDepuisLaGallerie;
	TextView txAnnuler;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choisir_depuis);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		m =this;


		initUI();
	}

	public void initUI(){

		txDepuisLaCamera= (TextView)findViewById(R.id.txDepuisLaCamera);
		txDepuisLaGallerie= (TextView)findViewById(R.id.txDepuisLaGallerie);
		txAnnuler= (TextView)findViewById(R.id.txAnnuler);

		txDepuisLaCamera.setOnClickListener(m);
		txDepuisLaGallerie.setOnClickListener(m);
		txAnnuler.setOnClickListener(m);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.txDepuisLaCamera:

			takePhoto();

			break;

		case R.id.txDepuisLaGallerie:

			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, SELECT_PICTURE);

			break;

		case R.id.txAnnuler:

			m.finish();

			break;

		}

	}

	//http://stackoverflow.com/questions/9585303/android-get-only-image-from-gallery
	//http://stackoverflow.com/questions/5424319/uploading-files-to-web-server-from-sd-card-in-android

	public void takePhoto() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStorageDirectory(), "user_image_golf.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(photo));
		startActivityForResult(intent, TAKE_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case TAKE_PICTURE:

			if (resultCode == RESULT_OK){
				//Uri imageUri;
				Uri selectedImage = imageUri;
				//getContentResolver().notifyChange(selectedImage, null);
				//ImageView imageView = (ImageView) findViewById(R.id.imCamera);
				//	ContentResolver cr = getContentResolver();
				//Bitmap bitmap;
				try {
					//	bitmap = android.provider.MediaStore.Images.Media
					//.getBitmap(cr, selectedImage);

					System.out.println("image path "+selectedImage.toString());
					//	imageView.setImageBitmap(bitmap);
					Toast.makeText(this, "This file: "+selectedImage.toString(),
							Toast.LENGTH_LONG).show();
					m.finish();


				} catch (Exception e) {
					Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
					.show();
					Log.e("Camera", e.toString());
				}
			}

			break;


		case SELECT_PICTURE:


			if (resultCode == RESULT_OK)
			{


				Uri photoUri = data.getData();
				File imageFile = new File(getRealPathFromURI(photoUri));

				System.out.println("URI PHOTO: "+imageFile.toString());

				if (photoUri != null)
				{
					try {


						String[] filePathColumn = {MediaStore.Images.Media.DATA};
						Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null); 
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
						String filePath = cursor.getString(columnIndex);

						System.out.println("filePath "+filePath);
						cursor.close();

						finish(); 

					}catch(Exception e)
					{}
				}

				break;

			}

		}}

	private String getRealPathFromURI(Uri contentURI) {
		Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			return contentURI.getPath();
		} else { 
			cursor.moveToFirst(); 
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
			return cursor.getString(idx); 
		}
	}

}


//	public String getPath(Uri uri) {
//		String[] projection = { MediaStore.Images.Media.DATA };
//		Cursor cursor = managedQuery(uri, projection, null, null, null);
//		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//		cursor.moveToFirst();
//		return cursor.getString(column_index);
//}
