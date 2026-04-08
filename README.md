# web_for-_form
# 📋 User Registration Web App
### HTML + Java Servlet + MySQL

---

## 📁 Project Folder Structure

```
YourApp/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/register/
│       │       └── RegisterServlet.java
│       └── webapp/
│           ├── index.html
│           └── WEB-INF/
│               ├── web.xml
│               └── lib/
│                   ├── mysql-connector-j-8.3.0.jar
│                   └── gson-2.10.1.jar
```

---

## ⚙️ Requirements

| Tool | Version | Download |
|------|---------|----------|
| Java JDK | 11 or above | https://www.oracle.com/java/technologies/downloads/ |
| Apache Tomcat | 9 or 10 | https://tomcat.apache.org/download-90.cgi |
| MySQL Server | 8.0 | https://dev.mysql.com/downloads/mysql/ |
| Eclipse IDE | Latest | https://www.eclipse.org/downloads/ |

---

## 🗄️ Step 1 — Setup MySQL Database

1. Open **MySQL Workbench** or **MySQL Command Line**
2. Run the following commands:

```sql
CREATE DATABASE IF NOT EXISTS registration_db;

USE registration_db;

CREATE TABLE IF NOT EXISTS users (
  id         INT          AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  email      VARCHAR(150) NOT NULL UNIQUE,
  phone      VARCHAR(20)  NOT NULL,
  created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
```

3. Or simply run the provided SQL file:
```bash
mysql -u root -p < setup.sql
```

---

## ☕ Step 2 — Configure the Java Servlet

Open `RegisterServlet.java` and update the DB credentials:

```java
private static final String DB_URL      = "jdbc:mysql://localhost:3306/registration_db?useSSL=false&serverTimezone=UTC";
private static final String DB_USER     = "root";          // ← your MySQL username
private static final String DB_PASSWORD = "yourpassword";  // ← your MySQL password
```

---

## 📦 Step 3 — Download Required JAR Files

Download both JARs and place them inside `WEB-INF/lib/` folder:

| JAR File | Download URL |
|----------|-------------|
| mysql-connector-j-8.3.0.jar | https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar |
| gson-2.10.1.jar | https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar |

---

## 📄 Step 4 — Create web.xml

Create a file at `WEB-INF/web.xml` and paste this content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" version="5.0">

  <servlet>
    <servlet-name>RegisterServlet</servlet-name>
    <servlet-class>com.register.RegisterServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>RegisterServlet</servlet-name>
    <url-pattern>/RegisterServlet</url-pattern>
  </servlet-mapping>

</web-app>
```

---

## 🌐 Step 5 — Deploy on Eclipse + Tomcat

1. Open **Eclipse IDE**
2. Go to **File → New → Dynamic Web Project**
3. Name it `YourApp`
4. Right-click project → **Build Path → Configure Build Path**
5. Add both JARs from `WEB-INF/lib/` to the build path
6. Copy all files into the correct folders:
   - `index.html` → `WebContent/` or `webapp/`
   - `RegisterServlet.java` → `src/com/register/`
   - `web.xml` → `WebContent/WEB-INF/`
   - JARs → `WebContent/WEB-INF/lib/`
7. Right-click project → **Run As → Run on Server**
8. Select your **Apache Tomcat** server → Click **Finish**

---

## 🚀 Step 6 — Run the App

Open your browser and go to:

```
http://localhost:8080/YourApp/index.html
```

> ⚠️ IMPORTANT: Never open index.html by double-clicking it.
> Always access it through the Tomcat URL above.

---

## ✅ How It Works

```
User fills form
      ↓
index.html sends JSON via fetch() POST
      ↓
RegisterServlet.java receives JSON
      ↓
Validates name, email, phone
      ↓
Inserts into MySQL → users table
      ↓
Returns success/error JSON
      ↓
index.html shows toast message
```

---

## 🛠️ Troubleshooting

| Error Message | Cause | Fix |
|--------------|-------|-----|
| Cannot reach server | Tomcat not running | Start Tomcat and use http://localhost:8080 |
| Error 404 | Wrong URL or servlet not mapped | Check web.xml and context path |
| Error 500 | DB error / wrong credentials | Check DB_USER and DB_PASSWORD in servlet |
| Email already registered | Duplicate email in DB | Use a different email |
| ClassNotFoundException | JAR missing | Add mysql-connector and gson JARs to WEB-INF/lib |

---

## 📊 Database Table Preview

| id | name | email | phone | created_at |
|----|------|-------|-------|------------|
| 1 | John Doe | john@example.com | 9876543210 | 2026-04-08 10:30:00 |
| 2 | Jane Smith | jane@example.com | 9123456789 | 2026-04-08 11:00:00 |

---

## 📂 Files Included

| File | Description |
|------|-------------|
| `index.html` | Frontend page with Register button and form |
| `RegisterServlet.java` | Java backend that stores data in MySQL |
| `setup.sql` | MySQL script to create database and table |
| `README.md` | This setup guide |

---

*Built with HTML · Java Servlet · MySQL · Apache Tomcat*
