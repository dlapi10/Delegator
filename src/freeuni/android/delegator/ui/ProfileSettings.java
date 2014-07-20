package freeuni.android.delegator.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.User;

public class ProfileSettings extends SuperActivity {

	private static int RESULT_LOAD_IMAGE = 1;
	private Bitmap newAvatar;
	private Bitmap currentAvatar;
	private String newPassword;
	private String newTelNumber;
	private User currentUser;
	private ImageView avatarImgView;
	private EditText telNumberET;
	private String newAvatarPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.profile_settings);
		stub.inflate();
		
		currentUser = App.getDb().getUser(userName);
		newAvatarPath="";
		
		TextView username = (TextView) findViewById(R.id.userNameSettings);
		username.setText(userName);
		
		avatarImgView = (ImageView) findViewById(R.id.avatarSettings);
		telNumberET = (EditText) findViewById(R.id.newTel);
		
		currentAvatar = currentUser.getAvatar();
		if(currentAvatar == null)
			currentAvatar = BitmapFactory.decodeResource(getResources(), R.drawable.default_user_image);
		
		if(savedInstanceState != null){
			newAvatarPath = savedInstanceState.getString("avatar");
			if(!newAvatarPath.isEmpty()){
				newAvatar = BitmapFactory.decodeFile(newAvatarPath);
			avatarImgView.setImageBitmap(newAvatar);
			} else
				avatarImgView.setImageBitmap(currentAvatar);
			
		} else {
			avatarImgView.setImageBitmap(currentAvatar);
			telNumberET.setText(currentUser.getPhoneNumber());
		}
	}
	
	/**
	 * Menu for the Profile settings activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_menu, menu); // Adding menu items to the super activity menu
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("avatar", newAvatarPath);
		outState.putString("tel", telNumberET.getText().toString());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			newAvatarPath = cursor.getString(columnIndex);
			cursor.close();
			
			newAvatar = BitmapFactory.decodeFile(newAvatarPath);
			
			avatarImgView.setImageBitmap(newAvatar);
		}
	}
	
	public void onBrowseButtonClick(View v){
		Intent i = new Intent(
				Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}
	
	/**
	 * Save settings
	 * @param item
	 */
	public void onSaveButtonClicked(MenuItem item){
		newPassword = ((EditText)findViewById(R.id.newPassword)).getText().toString();
		newTelNumber = telNumberET.getText().toString();
		
		currentUser.setAvatar(newAvatar);
		currentUser.setPassword(newPassword);
		currentUser.setPhoneNumber(newTelNumber);
		String txt = App.getDb().updateUser(currentUser);
		Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
	}
	
	public void onNotifBtn(View v){
		App.notif("something");
	}
}
