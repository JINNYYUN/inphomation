package bit.com.inpho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bit.com.inpho.dto.DetailCountAllDto;
import bit.com.inpho.dto.DetailPostDto;
import bit.com.inpho.dto.DetailReplyDto;
import bit.com.inpho.dto.MyPageMemberDto;
import bit.com.inpho.service.DetailService;
import bit.com.inpho.service.MyPageService;

@Controller
public class DetailController {
	
	@Autowired
	DetailService service;
	
	@Autowired
	MyPageService MyService;
	
	
	@RequestMapping(value = "detail.do", method = RequestMethod.GET)
	public String detail(Model model, DetailPostDto post, DetailCountAllDto count) throws Exception {

		int seq = 1;
		
		MyPageMemberDto myPage = MyService.getProfile(seq);
		
		DetailPostDto postList = service.getPost(seq);
		List<DetailPostDto> tagList = service.getHashTag(seq);
		
		
		int cLike = service.countLike(count);
		System.out.println("like: " + cLike);
		
		int cBook = service.countBookmark(count);
		System.out.println("countBookmark: "+ cBook);
		
		
		model.addAttribute("post", postList);
		model.addAttribute("tag", tagList);
		model.addAttribute("cLike", cLike);
		model.addAttribute("cBook", cBook);
		model.addAttribute("profile", myPage);

		return "detail.tiles";
	}

	@ResponseBody
	@RequestMapping(value = "addBookmark.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String addBookmark(DetailCountAllDto dto, Model model) throws Exception {
		/* bookmark 테이블에 해당 유저의 데이터가 몇 개 있는지 확인하기 위함*/
		int n = service.countBookmark(dto);
		System.out.println("countBookmark: "+n);
		
		model.addAttribute("count", n);
		
		String msg = "";
		if (n == 0) {
			boolean add = service.addBookmark(dto);
			System.out.println(add);
			msg = "NO";
		}else {
			boolean del = service.deleteBookmark(dto); 
			System.out.println("del: " + del);
			msg = "YES";
		}
		return msg;
	}
	

	@ResponseBody
	@RequestMapping(value = "addLike.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String addLike (DetailCountAllDto dto, Model model) throws Exception {
		int n = service.countLike(dto);
		System.out.println(n);
		
		String msg = "";
		if (n == 0) {
			boolean add = service.addLike(dto);
			System.out.println(add);
			msg = "NO";
		}else {
			boolean del = service.deleteLike(dto); 
			System.out.println("del: " + del);
			msg = "YES";
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "countLikeAll.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String countLikeAll (int post_seq, Model model) throws Exception {
		int count = service.countLikeAll(post_seq);
		
		return count + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "addReply.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String addReply (DetailReplyDto dto, Model model) throws Exception {
	
		boolean n = service.addReply(dto);
		
		if(n) {
			return "YES";
		}else {
			return "NO";
		}
	}
	@ResponseBody
	@RequestMapping(value = "replyList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public List<DetailReplyDto> replyList (int post_seq, Model model) throws Exception {
		
		
		List<DetailReplyDto> list = service.replyList(post_seq);

		System.out.println("list: " + list);
		
		return list;
	}

	@RequestMapping(value = "deletReply.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String deletReply(DetailReplyDto dto)  throws Exception{
		System.out.println("deletReply: " + dto.getPost_seq());
		
		boolean n = service.deleteReply(dto);
		
		if (n) {
			return "redirect:detail.do";
		} else {
			return "redirect:detail.do";
		}		
	}
	
	
}





