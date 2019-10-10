package com.ivan.endpointstats.model;

public class AppVersionKey {

	private String appName;
	private String versionName;

	private AppVersionKey(String appName, String versionName) {
		this.appName = appName;
		this.versionName = versionName;
	}

	public static AppVersionKey create(String appName, String versionName) {
		return new AppVersionKey(appName, versionName);
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AppVersionKey that = (AppVersionKey) o;

		if (appName != null ? !appName.equals(that.appName) : that.appName != null)
			return false;
		if (versionName != null ? !versionName.equals(that.versionName) : that.versionName != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = appName != null ? appName.hashCode() : 0;
		result = 31 * result + (versionName != null ? versionName.hashCode() : 0);
		return result;
	}

	@Override public String toString() {
		return "AppVersionKey{" +
				"appName='" + appName + '\'' +
				", versionName='" + versionName + '\'' +
				'}';
	}
}