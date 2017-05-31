package my.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.websphere.crypto.PasswordUtil;

/**
 * Servlet implementation class EncryptionServlet
 */
@WebServlet("/EncryptionServlet")
public class EncryptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EncryptionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		PrintWriter out=response.getWriter().append("Served at: ").append(request.getContextPath());
		String encryptedPW=null,decryptedPW=null;
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/application.property");
		Properties prop = new Properties();		 
		try {		 
			prop.load(input);		 
		} catch (IOException ioe) {		 
			ioe.printStackTrace();		 
		}		 
		encryptedPW=prop.getProperty("MyAppPassword");		
		try {		 
			decryptedPW = PasswordUtil.decode(encryptedPW);		 
		} catch(Exception e) {		 
			e.printStackTrace();		 
			throw new ServletException("Exception decrypting password", e);		 
		}	
		out.println("encrypted password="+encryptedPW+", decryptedPW="+decryptedPW+" instead of printing it out, you can now use it for authentication!");
		System.out.println("encrypted password="+encryptedPW+", decryptedPW="+decryptedPW+" instead of printing it out, you can now use it for authentication!");
		
		// You can also perform programmatic encryption if you like:
		String password="mytrickypassword";
		try {		 
			encryptedPW = PasswordUtil.encode(password,"aes");		 
		} catch(Exception e) {		 
			e.printStackTrace();		 
			throw new ServletException("Exception encrypting password", e);		 
		}
		out.println("unencrypted password="+password+", encrypted value="+encryptedPW);
		System.out.println("unencrypted password="+password+", encrypted value="+encryptedPW);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
