package school;


public class Controller {
	public SubjectDAO subDAO;
	public StudentDAO stuDAO;
	
	
	public Controller() {
		subDAO = new SubjectDAO();
		stuDAO = new StudentDAO();
		// TODO Auto-generated constructor stub
	}
	
	void init() {
		//util.setData(this);
	}
	
	void run() {
		init();
		while(true) {
			System.out.println("[1] 학생 추가"); // 학번 중복 저장 불가(자동증가) 
			System.out.println("[2] 학생 삭제"); // id 입력 후 삭제. + 그 학생의 과목도 같이 삭제
			System.out.println("[3] 학생별 과목 추가"); // 학번 입력 후점수 램덤 50 ~ 100 삽입 / 과목이름 중복 불가
			System.out.println("[4] 학생별 삭제"); // 학번 입력 후 과목 이름을 받아서 과목 리스트에서 학생 정보(점수) 삭제
			System.out.println("[5] 전체 학생 목록"); // 학생별 점수 출력		
			System.out.println("[6] 과목별 학생 목록"); // 과목이름 입력 받아서 별 학생 점수 오름차순으로 출력	
			System.out.println("[7] 파일 저장");
			System.out.println("[8] 파일 로드");
			System.out.println("[0] 종료"); 
			
			int sel = Utils.getInstance().getIntVal("메뉴 선택", 0 , 8);
			if( sel == 0 ) break;
			
			if(sel == 1) {// 아이디 중복검사
				stuDAO.addStudent(this);
			}else if(sel == 2) {
				stuDAO.removeStudent(this);
			}else if(sel == 3) {//중복과목 추가 불가
				subDAO.setSubject(this);
			}else if(sel == 4) {
				subDAO.removeStudentsSub(this);
			}else if(sel == 5) {
				stuDAO.printStuList(this);
			}else if(sel == 6) {
				subDAO.printSubScore(this);
			}else if(sel == 7 ) {
				Utils.getInstance().fileSave(this);
			}else if(sel == 8) {
				Utils.getInstance().fileLoad(this);
			}
		}
	}
	
}
