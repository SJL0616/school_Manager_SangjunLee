package school;

public class Subject {

	private int stuNo;
	private String subName;
	private int score;
	
	public Subject(int stuNo, String subName, int score) {
		this.stuNo = stuNo;
		this.subName = subName;
		this.score = score;
	}
	
	
	
	public int getStuNo() {
		return stuNo;
	}



	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}



	public String getSubName() {
		return subName;
	}



	public void setSubName(String subName) {
		this.subName = subName;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stuNo +"/"+ subName +"/"+score;
	}
}
