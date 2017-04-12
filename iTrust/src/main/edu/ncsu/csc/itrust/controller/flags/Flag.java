package edu.ncsu.csc.itrust.controller.flags;

public class Flag {
	
	private long fid;
	private long mid;
	private long pregID;
	private String flagType;
	
	public Flag(long fid, long mid, long pregID, String flagType) {
		this.setFid(fid);
		this.setMid(mid);
		this.setPregID(pregID);
		this.setFlagType(flagType);
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public long getPregID() {
		return pregID;
	}

	public void setPregID(long pregID) {
		this.pregID = pregID;
	}

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

}
