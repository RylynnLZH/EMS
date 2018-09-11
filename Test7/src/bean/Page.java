package bean;

import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

/**
 * 分页实体类
 * 
 * @author Rylynn
 *
 */
public class Page {
	private int nowpage; // 当前页面
	private int pagesize; // 每页显示数量
	private int allstart; // 每页显示编码
	private int start; // 每页开始编码
	private int datasize; // 数据总长度
	private int allpage; // 总页数

	// 构造函数接受数据
	public Page(int nowpage, int pagesize, int allstart, int start, int datasize) {

		this.allstart = allstart;
		this.pagesize = pagesize;
		this.datasize = datasize;

		// 求出总页数
		if (datasize % pagesize == 0) {
			allpage = datasize / pagesize;
		} else {
			allpage = datasize / pagesize + 1;
		}

		// 确定范围 防止越界
		if (nowpage <= 1) {
			this.nowpage = 1;
		} else if (nowpage >= allpage) {
			this.nowpage = allpage;
		} else {
			this.nowpage = nowpage;
		}

		// 计算每页下面显示页数
		if (nowpage <= allstart / 2 + 1) {
			this.start = start;
		} else if (nowpage > allpage - (allstart / 2)) {
			this.start = allpage - (allstart / 2 + 2);
		} else {
			this.start = nowpage - (allstart / 2);
		}

	}

	public int getDatasize() {
		return datasize;
	}

	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getAllstart() {
		return allstart;
	}

	public void setAllstart(int allstart) {
		this.allstart = allstart;
	}

	public int getAllpage() {
		return allpage;
	}

	public void setAllpage(int allpage) {
		this.allpage = allpage;
	}

}
