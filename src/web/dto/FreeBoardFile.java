package web.dto;

import java.util.Date;

public class FreeBoardFile {

	private int fileno;
	private int postno;
	private String originname;
	private String storedname;
	private int filesize;
	private Date writedate;
	
	
	@Override
	public String toString() {
		return "FreeBoardFile [fileno=" + fileno + ", postno=" + postno + ", originname=" + originname + ", storedname="
				+ storedname + ", filesize=" + filesize + ", writedate=" + writedate + "]";
	}
	public int getFileno() {
		return fileno;
	}
	public void setFileno(int fileno) {
		this.fileno = fileno;
	}
	public int getPostno() {
		return postno;
	}
	public void setPostno(int postno) {
		this.postno = postno;
	}
	public String getOriginname() {
		return originname;
	}
	public void setOriginname(String originname) {
		this.originname = originname;
	}
	public String getStoredname() {
		return storedname;
	}
	public void setStoredname(String storedname) {
		this.storedname = storedname;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	
}
