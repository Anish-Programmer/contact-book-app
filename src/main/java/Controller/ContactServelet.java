package Controller;

import java.io.IOException;
import java.util.List;

import Model.Contact;
import Model.ContactDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ContactServelet
 */
@WebServlet("/contact/*")
public class ContactServelet extends HttpServlet {
	//	private static final long serialVersionUID = 1L;
	private ContactDAO contactDAO;


	@Override
	public void init() {
		contactDAO = new ContactDAO();
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null) {
			action = "/list";
		}

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/delete":
				deleteContact(request, response);
				break;
			case "/view":
				viewContact(request, response);
				break;
			case "/search":
				searchContact(request, response);
				break;
			default:
				listContacts(request, response);
				break;
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=unexpectedError");
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null) {
			action = "/list";
		}

		try {
			switch (action) {
			case "/insert":
				insertContact(request, response);
				break;
			case "/update":
				updateContact(request, response);
				break;
			default:
				listContacts(request, response);
				break;
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=unexpectedError");
		}
	}


	// List all contacts and forward to contacts-list.jsp
	private void listContacts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Contact> contacts = contactDAO.getAllContacts();
		request.setAttribute("contactList", contacts);
		request.getRequestDispatcher("/View/contact-list.jsp").forward(request, response);
	}


	// Show form for adding a new contact
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/View/add-contact.jsp").forward(request, response);
	}


	// Show form for editing an existing contact
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Contact existingContact = contactDAO.getContactById(id);
			if (existingContact == null) {
				response.sendRedirect(request.getContextPath() + "/contact/list?error=notFound");
				return;
			}
			request.setAttribute("contact", existingContact);
			request.getRequestDispatcher("/View/edit-contact.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			// If ID cannot be converted to an integer, redirect with error
			response.sendRedirect(request.getContextPath() + "/contact/list?error=invalidId");
		}

	}


	// Insert a new contact into the database
	private void insertContact(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String address = request.getParameter("address");

			if (name == null || phone == null || email == null || address == null) {
				response.sendRedirect(request.getContextPath() + "/contact/list?error=missingData");
				return;
			}

			Contact newContact = new Contact(0, name, phone, email, address);
			contactDAO.addContact(newContact);
			response.sendRedirect(request.getContextPath() + "/contact/list?success=added");
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=insertFailed");
		}
	}


	// Update an existing contact in the database
	private void updateContact(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String address = request.getParameter("address");


			if (name == null || phone == null || email == null || address == null) {
				response.sendRedirect(request.getContextPath() + "/contact/list?error=missingData");
				return;
			}
			// new contact object
			Contact updatedContact = new Contact(id, name, phone, email, address);
			contactDAO.updateContact(updatedContact);
			response.sendRedirect(request.getContextPath() + "/contact/list?success=updated");

		}catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=invalidId");
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=updateFailed");

		}
	}


	// Delete a contact from the database
	private void deleteContact(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			contactDAO.deleteContact(id);
			response.sendRedirect(request.getContextPath() + "/contact/list?success=deleted");
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=invalidId");
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=deleteFailed");

		}

	}


	// View details of a single contact
	private void viewContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Contact contact = contactDAO.getContactById(id);
			if (contact == null) {
				response.sendRedirect(request.getContextPath() + "/contact/list?error=notFound");
				return;
			}
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/View/view-contact.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/contact/list?error=invalidId");
		}
	}


	// serach contact by id or name
	private void searchContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve parameters from the form submission
		String name = request.getParameter("name");

		// Initialize the contact object
		Contact contact = null;

		try {
			if (name == null || name.isEmpty()) {
				// If no name is provided, set an error message
				request.setAttribute("error", "missingSearchParam");
				request.getRequestDispatcher("/View/search-contact.jsp").forward(request, response);
				return;
			}

			// Search by name
			contact = contactDAO.getContactByName(name);

			// If no contact found, set an error message
			if (contact == null) {
				request.setAttribute("error", "notFound");
			} else {
				// Pass the contact to the JSP
				request.setAttribute("contact", contact);
			}

			// Forward the request to the JSP
			request.getRequestDispatcher("/View/search-contact.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			// Handle invalid ID format
			request.setAttribute("error", "invalidId");
			request.getRequestDispatcher("/View/search-contact.jsp").forward(request, response);
		}
	}





}
