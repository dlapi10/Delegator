package freeuni.android.delegator.ui;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.TaskStatus;

public class TaskActivity extends SuperActivity{

	public static final String EXTRA_TASK_ID = "freeuni.android.delegator.ui.TaskActivity.NEW_TASK";
	// Variable for storing current date and time
	private int year, month, day;
	private Spinner spinnerTaskStatus;
	private Task thisTask;

	/**
	 * Handling Incoming Intent;
	 */
	private void handleIncomingIntent(){
		Intent intent = getIntent();
		if(intent!=null){
			int taskID = intent.getIntExtra(EXTRA_TASK_ID,-1);
			if(taskID!=-1){
				thisTask = App.getDb().getTask(taskID);
				this.setTitle(thisTask.getTitle());
			}else{ 
				thisTask = new Task();
				thisTask.setTaskID(App.getDb().addTask(thisTask));
				this.setTitle(getResources().getString(R.string.task_default_title));
			}
			intent.removeExtra(EXTRA_TASK_ID);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIncomingIntent();
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.task);
		stub.inflate();
		
		spinnerTaskStatus = (Spinner) findViewById(R.id.task_status);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item,TaskStatus.statuses);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerTaskStatus.setAdapter(adapter);
	}

	/**
	 * Delete task, go to Home activity
	 * @param item
	 */
	public void deleteTask(MenuItem item){
		App.getDb().deleteTask(thisTask);
		Intent homeIntent = new Intent(this, HomeActivity.class);
		startActivity(homeIntent);
		finish();
	}

	/**
	 * Save task
	 * @param item
	 */
	public void saveTask(MenuItem item){
		//TODO
	}

	/**
	 * Menu for the Task activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_menu, menu); // Adding menu items to the super activity menu
		return super.onCreateOptionsMenu(menu);
	}

	
	/**
	 * Showing Deadline dialog box.
	 * @param v
	 */
	public void showDatePickerDialog(View v) {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int yearPicked,
					int monthOfYear, int dayOfMonth) {
				year = yearPicked;
				month = monthOfYear;
				day = dayOfMonth;
			}
		}, year, month, day);
		dpd.show();


	}



}
