
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
	private String cdapath;

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
		cdapath="C:\\Program Files\\Apache Group\\Tomcat 4.1\\webapps\\osr\\CDAfiles\\";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
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
				new File(cdapath+fileName));
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
		response.setHeader("Access-Control-Allow-Origin", "*");
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
		String action = request.getParameter("action");
		String fileprefix = request.getParameter("fileprefix");
		System.out.println("The action name of the post is " + action+" ***"+ request.getParameter("fileprefix"));;
		try {

			String Servletname = "http://tj8.trisearch.com.au";

			JSONObject obj = new JSONObject();
			// obj.put("files", "");
			JSONObject file = new JSONObject();
			if (action.equalsIgnoreCase("upload")) {
				Part filePart = request.getPart("files[]"); // Retrieves <input
				// type="file"
				// name="file">
				String orgfileName = filePart.getSubmittedFileName();
				String fileName = fileprefix+orgfileName;
				String filesize = String.valueOf(filePart.getSize());

				InputStream fileContent = filePart.getInputStream();

				System.out.println("See if we can get the filename here " + fileName);

				// write the inputStream to a FileOutputStream
				OutputStream outputStream = new FileOutputStream(
						new File(cdapath + fileName));
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
				// Get the printwriter object from response to write the
				// required
				// json object to the output stream
				PrintWriter out = response.getWriter();


				file.put("name", orgfileName);
				file.put("size", filesize);
				file.put("url", Servletname + "/osr/Pictures/" + fileName);
				file.put("thumbnail_url", "upload?getthumb=");
				file.put("deleteUrl", Servletname + "/upload/upload?action=delete&filename=" +fileName);
				file.put("deleteType", "DELETE");
				JSONArray filelist = new JSONArray();
				filelist.add(file);

				obj.put("files", filelist);

				// Assuming your json object is **jsonObject**, perform the
				// following, it will return your json object
				out.print(obj);
				System.out.println("what can we find from the json" + obj.toJSONString());
				out.flush();
			}
			/*
			if (action.equalsIgnoreCase("deletes")) {
			 
				String deletefileName = request.getParameter("filename");  

				System.out.println("See if we can get the filename here " + deletefileName);
				File deletefile = new File(cdapath+ deletefileName);
	        	
	    		if(deletefile.delete()){
	    			System.out.println(cdapath+deletefile.getName() + " is deleted!");

					file.put(deletefileName, "true"); 
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    			file.put(deletefileName, "false"); 
					//file.put("name", deletefileName);
					//file.put("size", deletefile);
					//file.put("error", "Error happened when deleting the file."); 
	    		}
	    	    
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8"); 
				PrintWriter out = response.getWriter();
 

				JSONArray filelist = new JSONArray();
				filelist.add(file);

				obj.put("files", filelist);

				// Assuming your json object is **jsonObject**, perform the
				// following, it will return your json object
				out.print(obj);
				System.out.println("what can we find from the json" + obj.toJSONString());
				out.flush();
			}
			*/
			
			
			// Actual logic goes here.
			// PrintWriter out = response.getWriter();
			// out.println("<h1>" + message + "</h1>");
		} catch (Exception e) {
			System.out.println( "error "+e.toString());

		}
	}
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print("Start uploading file code do delete function. ");
		//response.setHeader("Access-Control-Allow-Origin", "*");
	 
		String action = request.getParameter("action");
		System.out.println("The action name of the post is " + action);;
		try {

			String Servletname = "http://tj8.trisearch.com.au";

			JSONObject obj = new JSONObject();
			// obj.put("files", "");
			JSONObject file = new JSONObject();
			 
			if (action.equalsIgnoreCase("delete")) {
			 
				String deletefileName = request.getParameter("filename");  

				System.out.println("See if we can get the filename here " + deletefileName);
				File deletefile = new File(cdapath + deletefileName);
	        	
	    		if(deletefile.delete()){
	    			System.out.println(deletefile.getName() + " is deleted!");

					file.put(deletefileName, "true"); 
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8"); 
					PrintWriter out = response.getWriter();
	 

					JSONArray filelist = new JSONArray();
					filelist.add(file);

					obj.put("files", filelist); 
					out.print(obj);
					System.out.println("what can we find from the json" + obj.toJSONString());
					out.flush();
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    			//file.put(deletefileName, "false"); 
					//file.put("name", deletefileName);
					//file.put("size", "76085");
					//file.put("error", "Error happened when deleting the file."); 
	    		}
	    	    
			
			}
			
			 
		} catch (Exception e) {
			System.out.println( "error "+e.toString());

		}
	}
	public void destroy() {
		// do nothing.
	}
}