package freeuni.android.delegator.ui;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.TaskStatus;

public class TaskActivity extends SuperActivity{

	public static final String EXTRA_TASK_ID = "freeuni.android.delegator.ui.TaskActivity.NEW_TASK";
	public static final String EXTRA_ASSIGNEE = "freeuni.android.delegator.ui.TaskActivity.ASSIGNEE";
	// Variable for storing current date and time
	private int year=0, month=0, day=0;
	private Spinner spinnerTaskStatus;
	private Task thisTask;
	private String assignee_name;
	SeekBar completion;

	/**
	 * Handling Incoming Intent;
	 */
	private void handleIncomingIntent(){
		Intent intent = getIntent();
		if(intent!=null){
			assignee_name = intent.getStringExtra(EXTRA_ASSIGNEE);
			Log.i("TaskActivity: assignee", assignee_name);
			int taskID = intent.getIntExtra(EXTRA_TASK_ID,-1);
			if(taskID!=-1){
				thisTask = App.getDb().getTask(taskID);
				this.setTitle(thisTask.getTitle());
			}else{ 
				thisTask=null;
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
		this.setTitle(assignee_name);
		spinnerTaskStatus = (Spinner) findViewById(R.id.task_status);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,TaskStatus.statuses);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerTaskStatus.setAdapter(adapter);

		setContent();
	}

	private void setContent(){
		completion = (SeekBar)findViewById(R.id.completion_seek_bar);
		TextView reporter = (TextView)findViewById(R.id.reporter_info_reporter);
		TextView startDate = (TextView)findViewById(R.id.reporter_info_create_date);
		if(thisTask==null){
			reporter.setText(userName);
			startDate.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)+"/"+Calendar.getInstance().get(Calendar.YEAR));
		}else{
			reporter.setText(thisTask.getReporter().getUserName());
			Calendar c = thisTask.getStartDate();
			startDate.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));
		}
		completion.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView completion_number = (TextView)findViewById(R.id.completion_number);
				completion_number.setText(""+progress);
			}
		});
		if(thisTask!=null){
			if(thisTask.getTitle()!=null){
				EditText title = (EditText)findViewById(R.id.task_title);
				title.setText(thisTask.getTitle());
			}
			if(thisTask.getDescription()!=null){
				EditText description = (EditText)findViewById(R.id.task_decription);
				description.setText(thisTask.getDescription());
			}
			String st= thisTask.getStatus().getStatusName();
			if(st!=null){
				Spinner status = (Spinner)findViewById(R.id.task_status);
				for(int i=0;i<TaskStatus.statuses.length;i++){
					if(TaskStatus.statuses[i].equals(st)){
						status.setSelection(i);
					}
				}
			}

			completion.setProgress(thisTask.getCompletionPercent());
			Calendar deadline = thisTask.getDeadLine();
			if(deadline!=null){
				year = deadline.get(Calendar.YEAR);
				month = deadline.get(Calendar.MONTH);
				day = deadline.get(Calendar.DAY_OF_MONTH);
				showDeadline();
			}
			RatingBar priority = (RatingBar)findViewById(R.id.priority);
			priority.setProgress(thisTask.getPriority());
			
		}
	}

	/**
	 * Delete task, go to Home activity.
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
		if(thisTask==null){
			thisTask = new Task();
			thisTask.setTaskID(App.getDb().addTask(thisTask));
			thisTask.setStartDate(Calendar.getInstance());
			thisTask.setAssignee(App.getDb().getUser(assignee_name));
		}
		if(thisTask.getReporter()==null){
			thisTask.setReporter(App.getDb().getUser(userName));
		}
		thisTask.setAssignee(App.getDb().getUser(assignee_name));
		EditText title = (EditText)findViewById(R.id.task_title);
		thisTask.setTitle(title.getText().toString());
		EditText description = (EditText)findViewById(R.id.task_decription);
		thisTask.setDescription(description.getText().toString());
		Spinner status = (Spinner)findViewById(R.id.task_status);
		thisTask.setStatus(new TaskStatus(status.getSelectedItem().toString()));
		if(year!=0 && month!=0 && day!=0){
			Calendar date = Calendar.getInstance();
			date.set(Calendar.YEAR, year);
			date.set(Calendar.MONTH, month);
			date.set(Calendar.DAY_OF_MONTH, day);
			thisTask.setDeadLine(date);
		}
		RatingBar priority = (RatingBar)findViewById(R.id.priority);
		thisTask.setPriority(priority.getProgress());
		thisTask.setCompletionPercent(completion.getProgress());
		App.getDb().updateTask(thisTask);
		Toast.makeText(getApplicationContext(), freeuni.android.delegator.R.string.task_saved, Toast.LENGTH_SHORT).show();
		//Go Home after task saving finished
		Intent homeIntent = new Intent(this, HomeActivity.class);
		homeIntent.putExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME, assignee_name);
		startActivity(homeIntent);
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
				month = monthOfYear+1; // Mistake of Calendar, Bug O.o
				day = dayOfMonth;
				showDeadline();
			}
		}, year, month, day);
		dpd.show();
	}

	/**
	 * Shows deadline under button
	 */
	private void showDeadline(){
		TextView deadline = (TextView)findViewById(R.id.deadline);
		deadline.setText(day+"/"+month+"/"+year);
	}
}
