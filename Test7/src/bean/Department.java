package bean;

/**
 * 部门实体类
 * 
 * @author Rylynn
 *
 */
public class Department {
	private Integer id; // 部门id
	private String name; // 部门name
	private int emp_count; // 部门人数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmp_count() {
		return emp_count;
	}

	public void setEmp_count(int emp_count) {
		this.emp_count = emp_count;
	}

}
