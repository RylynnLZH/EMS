package bean;

/**
 * 业绩实体类
 * 
 * @author Rylynn
 *
 */
public class Score {
	private int id; // 业绩id
	private Employee emp; // 业绩中成员信息
	private Project pro; // 业绩中项目信息
	private Integer value; // 业绩value
	private String grade; // 业绩等级

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Project getPro() {
		return pro;
	}

	public void setPro(Project pro) {
		this.pro = pro;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
