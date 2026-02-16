# HTL (Sightly) Migration Notes

## What is HTL?
HTL (HTML Template Language), formerly called **Sightly**, is Adobe Experience Manager's (AEM) preferred server-side template language. It replaces JSP for building AEM components.

**Key Benefits over JSP:**
- **Automatic XSS Protection** - All output is automatically escaped
- **Separation of Concerns** - Logic stays in Java (Sling Models), presentation in HTL
- **Simpler Syntax** - No scriptlets, cleaner HTML

---

## How Our JSP Pages Would Map to HTL

### 1. index.jsp → index.html (HTL Component)

**Current JSP (Form):**
```html
<form action="ga" method="POST">
    <input type="number" name="taskCount" value="20">
    <button type="submit">Run GA</button>
</form>
```

**HTL Equivalent:**
```html
<form action="${resource.path}.optimize.html" method="POST"
      data-sly-use.model="com.cloud.allocation.models.GAFormModel">
    <input type="number" name="taskCount" value="${model.defaultTaskCount}">
    <button type="submit">Run GA</button>
</form>
```

**Key Difference:** `data-sly-use` connects HTL to a Java backend class (Sling Model).

---

### 2. results.jsp → results.html (HTL Component)

**Current JSP (JSTL Loop):**
```jsp
<c:forEach var="row" items="${allocationTable}">
    <tr>
        <td>${row[0]}</td>
        <td>${row[1]}</td>
    </tr>
</c:forEach>
```

**HTL Equivalent:**
```html
<tr data-sly-list.row="${model.allocationTable}">
    <td>${row.taskId}</td>
    <td>${row.taskSize}</td>
</tr>
```

**Key Difference:** `data-sly-list` replaces JSTL `<c:forEach>`.

---

### 3. Conditional Rendering

**Current JSP:**
```jsp
<c:choose>
    <c:when test="${row[2] == 'High'}">
        <span class="priority-high">${row[2]}</span>
    </c:when>
</c:choose>
```

**HTL Equivalent:**
```html
<span data-sly-test="${row.priority == 'High'}" class="priority-high">
    ${row.priority}
</span>
```

**Key Difference:** `data-sly-test` replaces `<c:if>` and `<c:when>`.

---

## Key HTL Attributes Cheat Sheet

| HTL Attribute | Purpose | JSP Equivalent |
|---|---|---|
| `data-sly-use` | Connect to a Java model class | `<jsp:useBean>` |
| `data-sly-list` | Loop over a collection | `<c:forEach>` |
| `data-sly-test` | Conditional rendering | `<c:if>` / `<c:when>` |
| `data-sly-text` | Set text content (XSS-safe) | `${expression}` |
| `data-sly-attribute` | Set HTML attributes dynamically | `${attribute}` |
| `data-sly-resource` | Include another AEM component | `<jsp:include>` |
| `data-sly-include` | Include another HTL script | `<%@ include %>` |

---

## Architecture Comparison

| Aspect | Current (Servlet/JSP) | AEM (HTL) |
|---|---|---|
| **Template** | JSP files | HTL (.html) files |
| **Controller** | Servlet (GAServlet.java) | Sling Model (Java class) |
| **Routing** | web.xml servlet-mapping | Sling Resource Resolution |
| **Build Tool** | Maven WAR plugin | Maven Content Package plugin |
| **Server** | Apache Tomcat | Adobe AEM (Felix OSGi) |

---

## Interview Talking Points

> "I have studied HTL concepts and understand how it improves upon JSP
> in the AEM ecosystem. If we were to migrate our Cloud GA Scheduler to AEM,
> I would convert the JSP pages to HTL templates using data-sly-use for
> connecting to Sling Models, data-sly-list for rendering the allocation
> table, and data-sly-test for priority-based conditional styling.
> The key advantage would be automatic XSS protection and cleaner
> separation of presentation from business logic."
