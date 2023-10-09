## RESTful API using Java/SpringBoot

This project was developed for the React.JS course, where the backend was made with Node.js and Express.

Decide to change and develop in Java.

💻 Technologies ​​used:
- Java 17
- Springboot
- PostgreSQL
- Deploy with Railway

In this system, I tried to apply a **Clean Architecture**.
Using OOP concepts to bring more security to the software.

🌍 The idea behind the system is: a social network to share your photos with the world.
This is a project for my development personal as FullStack, using Java on the backend, PostgreSQL database and React.JS on the front end.

``` mermaid

classDiagram
    class UserAccount {
        -name: String
        -email: String
        -username: String
        -password: String
        -profile_image: String
        -bio: String
        -Dashbboard[] dashboard
    }

    class Dashboard {
        -PhotoPosted[] photoPosted
    }

    class PhotoPosted {
        -image: String
        -profile_image: String
        -uploaded_By: String
        -description: String
        -creation_date: Date
        -likes: Like[]
        -commentsphoto: Comment[]
    }

    class Like {
        -username: String
        -profile_image: String
    }

    class Comment {
        -comment: String
        -username: String
        -profile_image: String
    }

   UserAccount "1" *-- "1" Dashboard
   Dashboard "1" *-- "N" PhotoPosted

    PhotoPosted "1" *-- "N" Like
    PhotoPosted "1" *-- "N" Comment

```
