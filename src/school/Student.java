package school;

public class Student {
	private int stuNo;
	private String stuId;
	private String stuName;
	int sum;
	public Student(int stuNo, String stuId, String stuName) {
		this.stuNo = stuNo;
		this.stuId = stuId;
		this.stuName = stuName;
		sum = 0;
	}
	
	
	
	public int getStuNo() {
		return stuNo;
	}



	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}



	public String getStuId() {
		return stuId;
	}



	public void setStuId(String stuId) {
		this.stuId = stuId;
	}



	public String getStuName() {
		return stuName;
	}



	public void setStuName(String stuName) {
		this.stuName = stuName;
	}



	public int getSum() {
		return sum;
	}



	public void setSum(int sum) {
		this.sum = sum;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stuNo +"/"+ stuId +"/"+stuName;
	}
}
