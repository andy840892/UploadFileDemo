
// Import required java libraries
import java.io.*;
import java.util.Collection;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// Extend HttpServlet class
public class Upload extends HttpServlet {

	private String message;

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Runnning upload function. DOGET");
		// Set response content type

		// String filename = request.getParameter("filename");
		// request.getParts();
		Part filePart = request.getPart("file"); // Retrieves <input type="file"
													// name="file">
		String fileName = filePart.getSubmittedFileName();
		InputStream fileContent = filePart.getInputStream();

		System.out.println("See if we can get the filename here " + fileName);

		// write the inputStream to a FileOutputStream
		OutputStream outputStream = new FileOutputStream(
				new File("C:\\Program Files\\Apache Group\\Tomcat 4.1\\webapps\\osr\\Pictures\\"+fileName));
		System.out.println("See if we can get the steam size " + fileContent.toString());
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = fileContent.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		fileContent.close();
		outputStream.close();

		// if(fileContent.length>0){

		// String filePdfPath = "C:\\Program Files\\Apache Group\\Tomcat
		// 4.1\\webapps\\osr\\Pictures\\test001.pdf";
		// FileOutputStream fout = new FileOutputStream(filePdfPath);
		// for (int i = 0; i < fileContent.length; i++) {
		// // fout.write((char) fileContent[i]);
		// }
		// }
		response.setContentType("text/html");
		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print("Start uploading file code do post function. ");
		// Set response content type
		/*
		 * Collection filePartlists= request.getParts(); System.out.print(
		 * "Can we get anying from the list.. "); filePartlists.forEach(( v)->{
		 * System.out.println("Item : " + " Count : " + ((Part)
		 * v).getSubmittedFileName());
		 * 
		 * 
		 * });
		 */

		try {

			Part filePart = request.getPart("files[]"); // Retrieves <input
														// type="file"
														// name="file">
			String fileName = filePart.getSubmittedFileName();
			String filesize = String.valueOf(filePart.getSize());
			 
		 
			InputStream fileContent = filePart.getInputStream();

			System.out.println("See if we can get the filename here " + fileName);

			// write the inputStream to a FileOutputStream
			OutputStream outputStream = new FileOutputStream(
					new File("C:\\Program Files\\Apache Group\\Tomcat 4.1\\webapps\\osr\\Pictures\\"+fileName));
			System.out.println("See if we can get the steam size " + fileContent.toString());
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = fileContent.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			fileContent.close();
			outputStream.close();
			// response.setContentType("text/html");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			// Get the printwriter object from response to write the required
			// json object to the output stream
			PrintWriter out = response.getWriter();

			JSONObject obj = new JSONObject();
		//	obj.put("files", "");
			JSONObject file = new JSONObject();

			file.put("name", fileName);
			file.put("size", filesize);
			file.put("url", "upload?getfile=" );
			file.put("thumbnail_url", "upload?getthumb=" );
			file.put("deleteUrl", "http://example.org/files/picture1.jpg");
			file.put("deleteType", "DELETE");
			JSONArray filelist = new JSONArray();
			filelist.add(file);
		 
			obj.put("files", filelist);
			
			
			/*
			 * String jsonresponse =
			 * 
			 * {"files": [ { "name": "picture1.jpg", "size": 902604, "url":
			 * "http:\/\/example.org\/files\/picture1.jpg", "thumbnailUrl":
			 * "http:\/\/example.org\/files\/thumbnail\/picture1.jpg",
			 * "deleteUrl": "http:\/\/example.org\/files\/picture1.jpg",
			 * "deleteType": "DELETE" }, { "name": "picture2.jpg", "size":
			 * 841946, "url": "http:\/\/example.org\/files\/picture2.jpg",
			 * "thumbnailUrl":
			 * "http:\/\/example.org\/files\/thumbnail\/picture2.jpg",
			 * "deleteUrl": "http:\/\/example.org\/files\/picture2.jpg",
			 * "deleteType": "DELETE" } ]}"
			 */

			// Assuming your json object is **jsonObject**, perform the
			// following, it will return your json object
			out.print(obj);
			System.out.println("what can we find from the json" + obj.toJSONString());
			out.flush();
			// Actual logic goes here.
			// PrintWriter out = response.getWriter();
			// out.println("<h1>" + message + "</h1>");
		} catch (Exception e) {
			System.out.println( "error "+e.toString());

		}
	}

	public void destroy() {
		// do nothing.
	}
}