package Entities;

public class MonthReport {

	private int idClinic;
	private int weekNum;
	private long MaxWaitTime;
	private long MinWaitTime;
	private long AvgWaitTime;
	private long SdWaitTime;

	public int getIdClinic() {
		return idClinic;
	}
	public void setIdClinic(int idClinic) {
		this.idClinic = idClinic;
	}
	public int getWeekNum() {
		return weekNum;
	}
	public void setWeekNum(int weekNum) {
		this.weekNum = weekNum;
	}
	public long getMaxWaitTime() {
		return MaxWaitTime;
	}
	public void setMaxWaitTime(long maxWaitTime) {
		MaxWaitTime = maxWaitTime;
	}
	public long getMinWaitTime() {
		return MinWaitTime;
	}
	public void setMinWaitTime(long minWaitTime) {
		MinWaitTime = minWaitTime;
	}
	public long getAvgWaitTime() {
		return AvgWaitTime;
	}
	public void setAvgWaitTime(long avgWaitTime) {
		AvgWaitTime = avgWaitTime;
	}
	public long getSdWaitTime() {
		return SdWaitTime;
	}
	public void setSdWaitTime(long sdWaitTime) {
		SdWaitTime = sdWaitTime;
	}
	
	
}
