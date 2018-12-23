package com.matcherweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.matcher.dao.Person_Dao;
import com.matcher.databeans.Person;
import com.matcherweb.utils.WebUtils;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
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
		pdao.add(p);


		PrintWriter out = response.getWriter();
		out.print("Success");
		out.flush();


	}

}
