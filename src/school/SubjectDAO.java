package school;

import java.util.ArrayList;
import java.util.Iterator;

public class SubjectDAO {

	private ArrayList<Subject> subList;

	
	
	public ArrayList<Subject> getSubList() {
		return subList;
	}

	public SubjectDAO() {
		init();
	}

	private void init() {
		this.subList = new ArrayList<Subject>();
	}

	public void parseData(String[] data, Controller controller) {
		if (subList.size() > 0) {
			init();
		}

		for (int i = 0; i < data.length; i++) {
			String[] one = data[i].split("/");
			Subject s = new Subject(Integer.parseInt(one[0]), one[1], Integer.parseInt(one[2]));
			addSubject(s);
			controller.stuDAO.setSum(s.getStuNo() , s.getScore());
		}
		printList();
	}

	private void printList() {
		for (Subject s : subList) {
			System.out.println(s);
		}
	}

	private void addSubject(Subject s) {
		subList.add(s);
	}

	// 학생번호, 과목 이름 받아서 과목 성적 정보 삭제
	private void removeOneSubject(int stuNo, String subName) {
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getStuNo() == stuNo && subList.get(i).getSubName().equals(subName)) {
				subList.remove(i);
				break;
			}
		}
	}

	// 학생 번호를 받아서 해당 번호를 가지고있는 과목 성적 정보 삭제
	public void removeByStuNo(int stuNo) {
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getStuNo() == stuNo) {
				subList.remove(i);
				i--;
			}
		}
		// printList();
	}

	// 학번 입력 후 과목 이름을 받아서 과목 리스트에서 학생 정보(점수) 삭제
	public void removeStudentsSub(Controller controller) {
		System.out.println("== 학생 과목 정보 삭제 ==");
		if (subList.size() == 0 || controller.stuDAO.getStuList().size() == 0) {
			System.out.println("성적 혹은 학생 정보가 없습니다.");
			return;
		}
		while (true) {

			int stuNo = Utils.getInstance().getIntVal("학번 입력", 0, Integer.MAX_VALUE);
			int idx = -1;
			if ((idx = controller.stuDAO.getStuIdxById(stuNo)) == -1) {
				System.out.println("학생 정보를 찾을 수 없음");
				continue;
			}
			if (getSubIdxById(stuNo) == -1) {
				System.out.println("학생 성적 정보를 찾을 수 없음");
				continue;
			}
			String subName = Utils.getInstance().getStringVal("과목 이름");
			if (isVailed(stuNo, subName)) {
				System.out.println("없는 과목 이름입니다.");
				continue;
			}
			removeOneSubject(stuNo, subName);
			System.out.println("과목정보가 삭제되었습니다.");
			break;
		}
		//printList();
	}

	// 학생 과목 추가 메서드
	// 학번 입력 후점수 램덤 50 ~ 100 삽입 / 과목이름 중복 불가
	public void setSubject(Controller controller) {
		System.out.println("== 학생 과목 추가 ==");
		if (controller.stuDAO.getStuList().size() == 0) {
			System.out.println("학생 정보가 존재하지 않습니다.");
			return;
		}
		while (true) {

			int stuNo = Utils.getInstance().getIntVal("학번 입력", 0, Integer.MAX_VALUE);
			int idx = -1;
			if ((idx = controller.stuDAO.getStuIdxById(stuNo)) == -1) {
				System.out.println("학생 정보를 찾을 수 없음");
				continue;
			}
			String subName = Utils.getInstance().getStringVal("과목 이름");
			if (!isVailed(stuNo, subName)) {
				System.out.println("중복 과목 이름입니다.");
				continue;
			}
			int score = Utils.getInstance().getRamdomNum(50, 100);
			controller.stuDAO.getStuList().get(idx).sum+=score;
			addSubject(new Subject(stuNo, subName, score));
			break;
		}
		//printList();
	}

	// 과목 이름이 중복인지 반환해주는 메서드
	private boolean isVailed(int stuNo, String subName) {
		for (Subject s : subList) {
			if (s.getSubName().equals(subName) && s.getStuNo() == stuNo) {
				return false;
			}
		}
		return true;
	}

	private boolean isDuplicated(String subName) {
		for (Subject s : subList) {
			if (s.getSubName().equals(subName)) {
				return true;
			}
		}
		return false;
	}

	// 학번을 받아서 idx 반환
	// stuId로 학생 index 반환하는 메서드
	private int getSubIdxById(int stuNo) {
		int idx = -1;
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getStuNo() == stuNo) {
				return i;
			}
		}
		return idx;
	}

	// 학번을 받아서 subjectList 반환
	public ArrayList<Subject> getSubjects(int stuNo) {
		ArrayList<Subject> temp = new ArrayList<Subject>();
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getStuNo() == stuNo) {
				temp.add(subList.get(i));
			}
		}

		if (temp.size() > 0) {
			for (int i = 0; i < temp.size(); i++) {
				for (int j = i; j < temp.size(); j++) {
					if (i == j)
						continue;
					if (temp.get(i).getSubName().compareTo(temp.get(j).getSubName()) < 0) {
						Subject s = temp.get(i);
						temp.set(i, temp.get(j));
						temp.set(j, s);
					}
				}
			}
		}
		return temp;
	}

	// 과목을 입력받아서 성적순으로 출력
	// 과목이름 입력 받아서 별 학생 점수 오름차순으로 출력
	public void printSubScore(Controller controller) {
		System.out.println("과목별 성적 출력");
		if (subList.size() == 0 || controller.stuDAO.getStuList().size() == 0) {
			System.out.println("성적 혹은 학생 정보가 없습니다.");
			return;
		}
		while (true) {

			String subName = Utils.getInstance().getStringVal("과목 이름");
			if (!isDuplicated(subName)) {
				System.out.println("없는 과목 이름입니다.");
				continue;
			}
			ArrayList<Subject> subjects = getOneSubList(subName);

			int num = 1;
			System.out.println("과목 이름 " + subName);
			System.out.println("================");
			for (Subject sub : subjects) {
				Student student = controller.stuDAO.getStuList().get(controller.stuDAO.getStuIdxById(sub.getStuNo()));

				System.out.println("[" + num + "등] " + student.getStuNo() + "  " + student.getStuName());
				System.out.println(" 점수 " + sub.getScore());

				num++;
			}
			System.out.println("================");
			break;
		}
	}

	// 과목이름과 일치하는 성적 정보를 따로 arrayList로 만들어서 반환
	private ArrayList<Subject> getOneSubList(String subName) {
		ArrayList<Subject> temp = new ArrayList<Subject>();
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getSubName().equals(subName)) {
				temp.add(subList.get(i));
			}
		}

		if (temp.size() > 0) {
			for (int i = 0; i < temp.size(); i++) {
				for (int j = i; j < temp.size(); j++) {
					if (i == j)
						continue;
					if (temp.get(i).getScore() < temp.get(j).getScore()) {
						Subject s = temp.get(i);
						temp.set(i, temp.get(j));
						temp.set(j, s);
					}
				}
			}
		}
		return temp;
	}

}
