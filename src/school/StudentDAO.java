package school;

import java.util.ArrayList;

public class StudentDAO {

	private ArrayList<Student> stuList;
	private int cnt = 0;
	private int num = 1000;

	
	
	public ArrayList<Student> getStuList() {
		return stuList;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public StudentDAO() {
		init();
	}

	void init() {
		this.stuList = new ArrayList<Student>();
	}

	public void parseData(String[] data) {
		if (stuList.size() > 0) {
			init();
		}
		for (int i = 0; i < data.length; i++) {
			String[] one = data[i].split("/");
			Student s = new Student(Integer.parseInt(one[0]), one[1], one[2]);
			stuList.add(s);
			if (s.getStuNo()  > num) {
				num = s.getStuNo();
			}
		}
		cnt = stuList.size();
		printList();
	}

	private void printList() {
		System.out.println("전체 학생 수 : " + cnt);
		for (Student s : stuList) {
			System.out.println(s);
		}
	}

	public void printStuList(Controller controller) {
		if (controller.subDAO.getSubList().size() == 0 || stuList.size() == 0) {
			System.out.println("성적 혹은 학생 정보가 없습니다.");
			return;
		}

		int num = 1;
		ArrayList<Student> temp = orderByScore();
		for (Student s : temp) {
			System.out.println("================");
			System.out.println("[" + num++ + "등] 학번: " + s.getStuNo() + " 이름: " + s.getStuName());

			ArrayList<Subject> subjects = controller.subDAO.getSubjects(s.getStuNo());
			double cnt = subjects.size();
			if (subjects.size() > 0) {
				for (Subject sub : subjects) {
					System.out.println("[" + sub.getSubName() + "] 점수 " + sub.getScore());
				}
				double avg = s.sum / cnt;
				System.out.println("[총점 :" + s.sum + "] [평균 :" + avg + "]");
			} else {
				System.out.println("성적 정보가 없습니다.");
			}
		}
		System.out.println("================");
	}

	// 학생 추가
	public void addStudent(Controller controller) {
		System.out.println("== 학생 정보 추가 ==");

		while (true) {

			String stuId = Utils.getInstance().getStringVal("아이디 입력");
			if (getStuIdxById(stuId) != -1) {
				System.out.println("중복 아이디");
				continue;
			}
			String stuName = Utils.getInstance().getStringVal("이름 입력");

			num++;
			stuList.add(new Student(num, stuId, stuName));
			cnt++;
			System.out.println(num+"번 "+stuName+"학생 정보가 저장되었습니다."); 
			break;
		}
		//printList();
	}

	// stuId로 학생 index 반환하는 메서드
	private int getStuIdxById(String stuId) {
		int idx = -1;
		for (int i = 0; i < stuList.size(); i++) {
			if (stuList.get(i).getStuId().equals(stuId)) {
				return i;
			}
		}
		return idx;
	}

	// stuNo로 학생 index 반환하는메서드
	public int getStuIdxById(int stuNo) {
		int idx = -1;
		for (int i = 0; i < stuList.size(); i++) {
			if (stuList.get(i).getStuNo() == stuNo) {
				return i;
			}
		}
		return idx;
	}

	// 학생 삭제 메서드
	/*
	 * id 입력 후 삭제. + 그 학생의 과목도 같이 삭제
	 * 
	 */
	public void removeStudent(Controller controller) {
		System.out.println("== 학생 정보 삭제 ==");
		if (stuList.size() == 0) {
			System.out.println("학생 정보가 없습니다.");
			return;
		}
		while (true) {

			String stuId = Utils.getInstance().getStringVal("아이디 입력");
			int idx = -1;
			if ((idx = getStuIdxById(stuId)) == -1) {
				System.out.println("아이디를 찾을 수 없음");
				continue;
			}
			controller.subDAO.removeByStuNo(stuList.get(idx).getStuNo());
			stuList.remove(idx);

			System.out.println("학생 정보가 삭제되었습니다.");
			cnt--;
			break;
		}
		// printList();

	}

	// 학생총점 저장 메서드{
	public void setSum(int stuNo, int score) {
		for (Student s : stuList) {
			if (s.getStuNo() == stuNo) {
				s.sum += score;
			}
		}
	}

	// subList를 총점순으로 정렬해서 배열 반환

	private ArrayList<Student> orderByScore() {
		ArrayList<Student> temp = stuList;
		int max = 0;
		for (int i = 0; i < temp.size(); i++) {
			for (int j = i; j < temp.size(); j++) {
				if (i == j)
					continue;
				if (temp.get(i).sum < temp.get(j).sum) {
					Student tempStudent1 = temp.get(i);
					Student tempStudent2 = temp.get(j);
					temp.set(i, tempStudent2);
					temp.set(j, tempStudent1);
				}
			}
		}
		System.out.println(temp.size());
		return temp;
	}
}
