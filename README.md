# Fun-Chat
[![Build Status](https://travis-ci.org/lymr/fun-chat.svg?branch=master)](https://travis-ci.org/lymr/fun-chat)

Fun-Chat is a showcase project for an online chat (Instant Messaging). It implements some concepts taken from
well known IM protocols, however its main goal is to demonstrate a fully asynchronous client -> server -> client
 architecture utilizing Scala, Akka, Akka-HTTP and PostgreSQL with ScalikeJDBC.

# Technical Features
* Asynchronous message based architecture for server as well as client using Akka.

 (Load balanced router | FSM | Actors pool | Become)
* REST API with Akka-HTTP.
* Non-blocking I/O with Akka actors.
* Secure resources access using OAuth2 Bearer token with underlying Auth0 implementation of Json Web Token (JWT).
* Storing users credentials at local DB with proper hashing using salt.
* Persistence with PostgreSQL and ScalikeJDBC.
* Utilization of Domain Specific Language (DSL) for sending messages.
* Usage of Functional Programing paradigm.

# User Features
* Send / Receive messages from a friends.
* Share a picture / document with a friends.
* Easy message sending with a simple DSL.

```markdown
    /**
    *   Message grammar
    *
    *   Note: single-quoted strings are tokens.
    *
    *   Example: "
    *       send to "Alice" , "Bob" message "Hey!"
    *                                              "
    */

    operation   :   recipients | text(s) | attachment(s)

    operation   :   'send'
    recipients  :   'to' recipient
    text        :   'message' "content" ~ (',' | ';') text
    attachment  :   'attachment' "attachment-id" ~ (',' | ';') attachment
    recipient   :   "user-name" ~ (',' | ';') recipient
```

# Requirements
* Scala 2.11.8 (https://www.scala-lang.org/documentation/)
* JDK 8        (http://www.oracle.com/technetwork/java/javase/downloads/index.html);
* sbt          (http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html);

#Copyright
* Copyright (C) 2016 Mor Levy.
* Distributed under the MIT License.
