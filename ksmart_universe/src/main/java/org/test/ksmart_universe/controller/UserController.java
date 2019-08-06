package org.test.ksmart_universe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.test.ksmart_universe.service.UserService;
import org.test.ksmart_universe.vo.User;

@Controller
public class UserController {
	@Autowired private UserService userService;
	
	@GetMapping("/login")
	public String login(){
		return "/login/login";
	}
	
	@PostMapping("/login")
	public String login(User user, HttpSession session, Model model) {
		User loginCheck = userService.getUserById(session, user.getMemberId(), user.getMemberPw());
		
		if(loginCheck == null) {
			model.addAttribute("alert", "아이디 혹은 비밀번호가 일치하지 않습니다.");
			return "/login/login";
		}
		
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
	@GetMapping("/userList")
	public String userList(Model model) {
		model.addAttribute("selectList", userService.selectList());
		return "/user/select/userSelect";
	}
	
	@PostMapping("/searchUserList")
	public String searchUserList(@RequestParam(value="sk") String sk
								,@RequestParam(value="sv") String sv
								,Model model){
		model.addAttribute("selectList", userService.selectList(sk, sv));
		return "/user/select/userSelect";
	}
	
	@GetMapping("/addUser")
	public String addUser() {
		return "/user/userInsert/addUser";
	}
	
	@PostMapping("/addUser")
	public String addUser(User user, Model model) {
		User userCheck = userService.getUserById(user.getMemberId());
		System.out.println(userCheck+"<--usercontroller userCheck");
		if(userCheck == null) {			
			userService.addUser(user);
			return "redirect:/userList";
		}else {
			model.addAttribute("alert", "동일한 아이디가 존재합니다.");
			return "/user/userInsert/addUser";
		}
	}
	
	@GetMapping("/modifyUser")
	public String modifyUser(@RequestParam(value="memberId") String memberId,
							Model model) {
		model.addAttribute("mem", userService.getUserById(memberId));
		return "/user/uUpdate/modifyUser";
	}
	
	@PostMapping("/modifyUser")
	public String modifyUser(User user) {
		System.out.println(user.toString() + "<--userController user");
		userService.modifyUser(user);
		return "redirect:/userList";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam(value="memberId") String memberId
							,Model model) {
		model.addAttribute("memberId", memberId);
		return "/user/uDelete/deleteUser";
	}
	
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam(value="memberId") String memberId
							,@RequestParam(value="memberPw") String memberPw
							,Model model) {
		
		String alert = userService.deleteUser(memberId, memberPw);
		
		if(!alert.equals("삭제성공")) {
			model.addAttribute("alert", alert);
			model.addAttribute("memberId", memberId);
			return "/user/uDelete/deleteUser";
		}
		
		return "redirect:/userList";
	}
}
