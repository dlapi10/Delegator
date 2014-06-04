package freeuni.android.delegator.model;

public class UserSettings {

	//Private Variables;
	private String alarmSoundPath;
	private boolean notificationsOn;
	/**
	 * @return the alarmSoundPath
	 */
	public String getAlarmSoundPath() {
		return alarmSoundPath;
	}
	/**
	 * @return the notificationsOn
	 */
	public boolean isNotificationsOn() {
		return notificationsOn;
	}
	/**
	 * @param alarmSoundPath the alarmSoundPath to set
	 */
	public void setAlarmSoundPath(String alarmSoundPath) {
		this.alarmSoundPath = alarmSoundPath;
	}
	/**
	 * @param notificationsOn the notificationsOn to set
	 */
	public void setNotificationsOn(boolean notificationsOn) {
		this.notificationsOn = notificationsOn;
	}
	
	
	
}
