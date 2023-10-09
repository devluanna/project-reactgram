## RESTful API using Java/SpringBoot

This project was developed for the React.JS course, where the backend was made with Node.js and Express.

Decide to change and develop in Java.

Technologies ​​used:
- Java 17
- Springboot
- PostgreSQL
- Deploy with Railway

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
