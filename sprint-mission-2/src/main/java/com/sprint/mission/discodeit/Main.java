package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFChannel;
import com.sprint.mission.discodeit.service.jcf.JCFMessage;
import com.sprint.mission.discodeit.service.jcf.JCFUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JCFChannel jcfChannel = new JCFChannel();
        JCFMessage jcfMessage = new JCFMessage();
        JCFUser jcfUser = new JCFUser();

        Scanner sc = new Scanner(System.in);

        User loginUser = null;

        boolean run = true;

        while(run){
            System.out.println("=====Discodeit Meun=====");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 종료");
            int choice = sc.nextInt();
            //로그인/ 회원가입 행동 함수로 분리 하려고 생각중..
            if(loginUser == null){
                if(choice == 1) {
                    System.out.println("===== 로그인 =====");
                    System.out.println("사용자 이메일 입력: ");
                    String username = sc.next();
                    loginUser = jcfUser.findUserByUserEmail(username);
                    if (loginUser == null) {
                        System.out.println("찾을수 없는 사용자 입니다.");
                        System.out.println("처음 화면으로 넘어 갑니다...");
                        continue;
                    }
                }else if(choice == 2) {
                    System.out.println("===== 회원가입 =====");
                    System.out.println("1. 서버 등록");
                    System.out.println("2. 서버 목록");
                }else if(choice == 3){
                    run = false;
                } else{
                    System.out.println("로그인:1, 회원 가입:2, 종료:3 중에 입력 해주세요.");
                    continue;
                }
            }
            System.out.println("====Discodeit 메뉴====");
            System.out.println("");

        }
        sc.close();

    }
}
