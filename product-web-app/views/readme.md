# How to use parent.html

1. Add on the top of website:

```
{% extends "./parent.html" %}
{% block content %}
```

2. Start html inside <main></main>

3. End html file with:

```
{% endblock %}
```


CODE EXAMPLE:
```
{% extends "./parent.html" %}
{% block content %}
    
    <main class="container mt-4">
        <section id="jobRole" class="mt-4">
           {% if jobRole and jobRole.name.length > 0 %}
           <table class="table">
            <thead>
                <tr>
                    <th scope="col">Role Name</th>
                    <th scope="col">Role Description</th>
                    <th scope="col">SharePoint Link for Role Specification</th>
                </tr>
                <tbody>
                    <tr>
                        <td>{{ jobRole.name }}</td>
                        <td>{{ jobRole.description }} </td>
                        <td><a href="{{ jobRole.sharePointLink }}">View</a></td>
                    </tr>
                </tbody>
            </thead>
        </table>
           {% else %}
                Could not find job Role.
           {% endif %}
        </section>
    </main>

{% endblock %}
```

