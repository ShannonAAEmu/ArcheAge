package info.aaemu.converter.dat.entity;

public class DoodadNpc {
	
	private int id;
	private String x;
	private String y;
	private String z;
	
	public DoodadNpc(int id, String x, String y, String z) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getId() {
		return id;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public String getZ() {
		return z;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setX(String x) {
		this.x = x;
	}

	public void setY(String y) {
		this.y = y;
	}

	public void setZ(String z) {
		this.z = z;
	}
	
}
