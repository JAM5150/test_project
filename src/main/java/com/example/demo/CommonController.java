package com.example.demo;

import java.net.http.HttpClient.Redirect;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CommonController {
	@Autowired
	private SqlSession sqlSession;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
	
	//info 페이지 표시
	@RequestMapping(value="/*")
	public ModelAndView result() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/page/info");
		return mav;
	}
	
	//메뉴 id 를 받아와 해당 페이지 표시
	@SuppressWarnings("unchecked")
	@GetMapping("/menu={id}")
	public ModelAndView viewResult(@PathVariable String id) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("result", sqlSession.selectOne("menu.one", id));
			//System.out.println("\n");
			mav.setViewName(((HashMap<String, Object>) result.get("result")).get("MENU_DESC").toString());
			return mav;
		}catch(Exception e) {
			//System.out.println("\nerror");
			//LOGGER.debug("Error Massage : ", e.getMessage());
			//logger.info(e.getMessage());
			mav.setViewName("/page/info");
			return mav;
			//return "redirect:result";
		}
	}
	
	/*
	//다건 조회
	@GetMapping("/board")
	public HashMap<String, Object> list(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyword", request.getParameter("keyword"));
		
		try {
			
			result.put("result", sqlSession.selectList("board.list", paramMap));
			// 200 ok - GET, PUT 혹은 POST 요청에 대한 성공 응답 코드
			response.setStatus(200);
		}catch(Exception e) {
			// 417 Expectation Failed - Expect 요청 헤더 필드로 요청한 예상이 서버에서 적당하지 않음을 알려 줄때
			response.setStatus(417);
		}
		
		return result;
	}
	*/
	// 다건 조회
	@GetMapping("/comm/{namespace}")
	public HashMap<String, Object> list(@PathVariable String namespace, HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyword", request.getParameter("keyword"));
		
		try {
			System.out.println();
			result.put("result", sqlSession.selectList(namespace + ".list", paramMap));
			// 200 ok - GET, PUT 혹은 POST 요청에 대한 성공 응답 코드
			response.setStatus(200);
		} catch (Exception e) {
			// 417 Expectation Failed - Expect 요청 헤더 필드로 요청한 예상이 서버에서 적당하지 않음을 알려 줄때
			response.setStatus(417);
			System.out.println(e.getMessage());
		}

		return result;
	}

	// 단건 조회
	@GetMapping("/comm/{namespace}/{id}")
	public HashMap<String, Object> one(@PathVariable String namespace, @PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyword", request.getParameter("keyword"));
		paramMap.put("id", id);

		try {

			result.put("result", sqlSession.selectList(namespace + ".one", paramMap));
			// 200 ok - GET, PUT 혹은 POST 요청에 대한 성공 응답 코드
			response.setStatus(200);
		} catch (Exception e) {
			// 417 Expectation Failed - Expect 요청 헤더 필드로 요청한 예상이 서버에서 적당하지 않음을 알려 줄때
			response.setStatus(417);
			System.out.println(paramMap);
			System.out.println(e.getMessage());
		}

		return result;
	}
	
	//insert
	@PostMapping("/comm/{namespace}")
	public HashMap<String, Object> insert(@PathVariable String namespace, HttpServletRequest request, HttpServletResponse response ){
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, String> paramMap = new HashMap<String, String>();
		try {
			Enumeration<String> paramKeys = request.getParameterNames();
			while (paramKeys.hasMoreElements()) {
			     String key = paramKeys.nextElement();
			     paramMap.put(key, request.getParameter(key));
			}
			result.put("result", sqlSession.insert(namespace + ".insert", paramMap));
			System.out.println("\n" + paramMap + "\n" + result);
			response.setStatus(201);
		} catch(Exception e) {
			response.setStatus(417);
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	@PutMapping("/comm/{namespace}/{id}")
	public HashMap<String, Object> update(@PathVariable String namespace, @PathVariable String id,HttpServletRequest request, HttpServletResponse response ){
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, String> paramMap = new HashMap<String, String>();
		try {
			Enumeration<String> paramKeys = request.getParameterNames();
			while (paramKeys.hasMoreElements()) {
			     String key = paramKeys.nextElement();
			     paramMap.put(key, request.getParameter(key));
			}
			paramMap.put("id", id);
			result.put("result", sqlSession.insert(namespace + ".update", paramMap));
			System.out.println("\n" + paramMap + "\n" + result);
			response.setStatus(201);
		} catch(Exception e) {
			response.setStatus(417);
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	
	@DeleteMapping("/comm/{namespace}/{id}")
	public HashMap<String, Object> delete(@PathVariable String namespace, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		
		try {
			sqlSession.delete(namespace + ".delete", paramMap);
			response.setStatus(200);
		}catch(Exception e) {
			response.setStatus(417);
			System.out.println(e.getMessage());
		}
		
		return result;
	}
}