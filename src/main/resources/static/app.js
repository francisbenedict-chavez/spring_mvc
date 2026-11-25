const API_URL = "http://localhost:8080/api/students";
const API_AUTH = "http://localhost:8080/api/auth";

/**
 * Creates a student using the REST API.
 * @param {StudentData} studentData - The student details to submit.
 * @returns {Promise<void>}
 */
async function createStudent(studentData) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(studentData),
      credentials: "include",
    });

    if (response.ok) {
      alert("Student added successfully!");
      resetForm();
      getAllStudents();
    } else {
      alert("Error adding student.");
    }
  } catch (error) {
    console.error("Error creating student:", error);
  }
}

/**
 * Handles student form submissions.
 * @param {SubmitEvent} e - The form submit event.
 * @returns {void}
 */
document.getElementById("studentForm").addEventListener("submit", function (e) {
  e.preventDefault();

  if (!validateStudentForm()) {
    return;
  }
  const id = document.getElementById("studentId").value;
  const studentData = {
    firstName: document.getElementById("firstName").value,
    lastName: document.getElementById("lastName").value,
    email: document.getElementById("email").value,
  };
  if (id) {
    updateStudent(id, studentData);
  } else {
    createStudent(studentData);
  }
});

/**
 * Clears the student form and restores the submit button.
 * @returns {void}
 */
function resetForm() {
  document.getElementById("studentId").value = "";
  document.getElementById("studentForm").reset();

  const submitBtn = document.getElementById("submitBtn");
  submitBtn.textContent = "Add Student";
  submitBtn.style.backgroundColor = "28a745";
}

/**
 * Retrieves all students and renders them in the table.
 * @returns {Promise<void>}
 */
async function getAllStudents() {
  try {
    const response = await fetch(API_URL, {
      credentials: "include",
    });
    if (!response.ok) throw new Error("Network response was not ok");

    const students = await response.json();
    const tableBody = document.getElementById("studentTableBody");
    tableBody.innerHTML = "";

    students.forEach((student) => {
      const row = `
                <tr>
                    <td>${student.id}</td>
                    <td>${student.firstName}</td>
                    <td>${student.lastName}</td>
                    <td>${student.email}</td>
                    <td>
                        <button class="btn-edit" onclick="initializeStudentFormById(${student.id})">Edit</button>
                        <button class="btn-delete" onclick="deleteStudent(${student.id})">Delete</button>
                    </td>
                </tr>
            `;
      tableBody.innerHTML += row;
    });
  } catch (error) {
    console.error("Error fetching students:", error);
    alert("Failed to fetch students. Is your Spring Boot backend running?");
  }
}

/**
 * Loads a student into the form for editing.
 * @param {number|string} id - The student's ID.
 * @returns {Promise<void>}
 */
async function initializeStudentFormById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      credentials: "include",
    });
    if (!response.ok) throw new Error("Could not fetch student details");

    const student = await response.json();

    document.getElementById("studentId").value = student.id;
    document.getElementById("firstName").value = student.firstName;
    document.getElementById("lastName").value = student.lastName;
    document.getElementById("email").value = student.email;

    // button changes when edit button is triggered
    const submitBtn = document.getElementById("submitBtn");
    submitBtn.textContent = "Update";
    submitBtn.style.backgroundColor = "#007bff";

    // clear old error highlights when loading data into form
    clearValidationStyles();
  } catch (error) {
    console.error("Error fetching student details:", error);
  }
}

/**
 * Updates an existing student.
 * @param {number|string} id - The student's ID.
 * @param {StudentData} studentData - The updated student details.
 * @returns {Promise<void>}
 */
async function updateStudent(id, studentData) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(studentData),
      credentials: "include",
    });

    if (response.ok) {
      alert("Student updated successfully!");
      resetForm();
      getAllStudents();
    } else {
      alert("Error updating student.");
    }
  } catch (error) {
    console.error("Error updating student:", error);
  }
}

/**
 * Deletes a student after user confirmation.
 * @param {number|string} id - The student's ID.
 * @returns {Promise<void>}
 */
async function deleteStudent(id) {
  if (confirm("Are you sure you want to delete this student?")) {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        credentials: "include",
      });

      if (response.ok) {
        const message = await response.text();
        alert(message);
        getAllStudents();
      } else {
        alert("Error deleting student.");
      }
    } catch (error) {
      console.log("Error deleting student:", error);
    }
  }
}

/**
 * Logs a user in using session authentication.
 * @param {LoginDto} loginDto - The login credentials.
 * @returns {Promise<void>}
 */
async function loginUser(loginDto) {
  try {
    const response = await fetch(`${API_AUTH}/auth_login_session`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginDto),
      credentials: "include",
    });

    if (response.ok) {
      alert("Login successful!");
      window.location.href = "index.html";
    } else {
      alert("Invalid credentials.");
    }
  } catch (error) {
    console.error("Login error:", error);
  }
}

/**
 * Logs out the current user and clears browser state.
 * @returns {Promise<void>}
 */
async function logoutUser() {
  try {
    await fetch(`${API_AUTH}/logout`, {
      method: "POST",
      credentials: "include",
    });
    document.getElementById("studentTableBody").innerHTML = "";
    resetForm();
    alert("Logged out successfully!");
    window.location.href = "index.html";

    try {
      await fetch(API_URL, {
        method: "GET",
        headers: {
          Authorization: "Basic " + btoa("invalid_user:wrong_password"),
        },
      });
    } catch (error) {
      console.log("Browser credential cache cleared.");
    }
  } catch (error) {
    console.error("Logout transmission crash:", e);
  }
}

/**
 * Loads the student list after the page finishes loading.
 * @function
 * @name loadStudentsOnPageLoad
 */
document.addEventListener("DOMContentLoaded", getAllStudents);
