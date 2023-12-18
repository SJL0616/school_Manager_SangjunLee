package school;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Utils {
	private static Utils instance;
	private Scanner sc;
	private Random rd;
	private String path = System.getProperty("user.dir")+"\\src\\";
	//final String CUR_PATH = path + "\\src\\school_이상준\\"; 
	
	public static Utils getInstance() {
		if(instance == null) {
			instance = new Utils();
		}
		return instance;
	}
	// static 메서드로 모두 바꿔서 객체 생성없이 클래스를 사용하는 방법도 있음.
	private Utils() {
		// TODO Auto-generated constructor stub
		sc = new Scanner(System.in);
		rd = new Random();
	}
	
	void setData(Controller controller) {
		String stuListData = "1001/test1/이만수\n"
				+ "1003/test3/오수정\n"
				+ "1004/test4/박영희\n"
				+ "1005/test5/유재석\n"
				+ "1006/test6/아이유\n"
				+ "1007/test7/김철수\n"
				+ "1008/test8/개똥이\n"
				+ "1009/test9/점박이";
		controller.stuDAO.parseData(stuListData.split("\n"));
		
		String subjectData = "1001/국어/55\n"
				+ "1001/영어/80\n"
				+ "1003/국어/67\n"
				+ "1003/수학/99\n"
				+ "1003/영어/65\n"
				+ "1004/국어/65\n"
				+ "1004/영어/89\n"
				+ "1005/국어/100\n"
				+ "1005/수학/77\n"
				+ "1005/영어/84\n"
				+ "1009/사회/70";
		controller.subDAO.parseData(subjectData.split("\n"),controller);
	}
	
	public int getRamdomNum(int min , int max) {
		return rd.nextInt((max/2)+1)+min;
	}
	
	public String getStringVal(String showStr) {
		while(true) {
			System.out.println(showStr);
			String input = sc.nextLine();
			
			return input;
		}
		
	}
	
	public int getIntVal(String showStr, int start, int end) {
		int val = -1;
		while(true) {
			System.out.println(showStr);
			int input = 0;
			try {
				input = sc.nextInt();
				sc.nextLine();
				if(input < start || input > end) {
					System.out.println(start +" ~ "+end +" 범위 내의 숫자를 입력하세요.");
					continue;
				}
			}catch(InputMismatchException e) {
				System.out.println("정수를 입력하세요.");
				sc.nextLine();
				continue;
			}catch(Exception e) {
				
			}
			val = input;
			break;
		}
		return val;
	}
	
	
	public void fileSave(Controller controller) {
		saveStulist(controller.stuDAO);
		saveSublist(controller.subDAO);
	}
	
	public void fileLoad(Controller controller) {
		loadStulist(controller.stuDAO);
		loadSublist(controller);
	}
	
	private void saveStulist(StudentDAO stuDAO) {
		if(stuDAO.getStuList().size() == 0) {
			System.out.println("학생 정보가 없습니다.");
			return;
		}
		String fileName = "stuList01.txt";
		
		File file = new File(path+ this.getClass().getPackageName()+"\\" + fileName);
		
		try(FileWriter fw = new FileWriter(file)){
			String data ="";
			for(Student s : stuDAO.getStuList()) {
				data += s+"\n";
			}
			data = data.substring(0 , data.length()-1);
			fw.write(data);
			System.out.println("학생 리스트를 저장하였습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveSublist(SubjectDAO subDAO) {
    	if(subDAO.getSubList().size() == 0) {
			System.out.println("과목 정보가 없습니다.");
			return;
		}
		String fileName = "subList01.txt";
		
		File file = new File(path+ this.getClass().getPackageName()+"\\" + fileName);
		
		try(FileWriter fw = new FileWriter(file)){
			String data ="";
			for(Subject s : subDAO.getSubList()) {
				data += s+"\n";
			}
			data = data.substring(0 , data.length()-1);
			fw.write(data);
			System.out.println("과목 리스트를 저장하였습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
    
	private void loadStulist(StudentDAO stuDAO) {
        String fileName = "stuList01.txt";
		System.out.println(path+ this.getClass().getPackageName()+"\\"+ fileName);
		
		File file = new File(path+ this.getClass().getPackageName()+"\\" + fileName);
		if(!file.exists()) {
			System.out.println("파일이 존재하지 않습니다.");
			return;
		}
		try(FileReader fw = new FileReader(file)){
			BufferedReader br = new BufferedReader(fw);
			String data ="";
			String next = "";
			while((next = br.readLine()) != null) {
				data += next+"\n";
			}
			data = data.substring(0 , data.length()-1);
			stuDAO.parseData(data.split("\n"));
			System.out.println("학생 리스트를 로드하였습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
    
	private void loadSublist(Controller controller) {
        String fileName = "subList01.txt";
		
		File file = new File(path+ this.getClass().getPackageName()+"\\" + fileName);
		if(!file.exists()) {
			System.out.println("파일이 존재하지 않습니다.");
			return;
		}
		try(FileReader fw = new FileReader(file)){
			BufferedReader br = new BufferedReader(fw);
			String data ="";
			String next = "";
			while((next = br.readLine()) != null) {
				data += next+"\n";
			}
			data = data.substring(0 , data.length()-1);
			controller.subDAO.parseData(data.split("\n"),controller);
			System.out.println("학생 리스트를 로드하였습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
}
