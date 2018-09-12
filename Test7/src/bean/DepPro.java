package bean;

/**
 * 部门-项目管理实体类
 * 
 * @author Rylynn
 *
 */
public class DepPro {
	private int did; // 部门id
	private int pid; // 项目id
	private String pName; // 项目name

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}
}
