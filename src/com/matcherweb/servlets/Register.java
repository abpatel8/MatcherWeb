package com.matcherweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.matcher.dao.Db_Dao;
import com.matcher.dao.Person_Dao;
import com.matcher.databeans.Person;
import com.matcherweb.utils.WebUtils;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Register() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}

	protected void ProcessRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String name = WebUtils.getParameterValueSafely(request, "nameInput", true, true);
		String emailAddress = WebUtils.getParameterValueSafely(request, "emailInput", true, true);
		String birthdate = WebUtils.getParameterValueSafely(request, "birthdateInput", true, true);
		String phonenumber = WebUtils.getParameterValueSafely(request, "phoneInput", true, true);
		String gender = WebUtils.getParameterValueSafely(request, "genderOptions", true, true);

		Person p = new Person();
		p.setName(name);
		p.setEmail(emailAddress);
		p.setBirthdate(birthdate);
		p.setPhoneNumber(phonenumber);
		p.setGender(gender);
		
		Person_Dao pdao = new Person_Dao();
		long result = pdao.add(p);
		
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
		
		if(result == Db_Dao.rt_code_itemexists) {
			obj.put("result", "fail");
			obj.put("msg", "Error. A user with this email or phone number already exists");
		}else if(result == Db_Dao.rt_code_success) {
			obj.put("result", "success");
		}else {
			obj.put("result", "fail");
			obj.put("msg", "Error. There might be an issue with your submission or the server, please try again.");
		}
		
		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		out.print(obj.toJSONString());
		out.flush();

		
	}
	
}
