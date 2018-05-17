~~~~
PORT 80
  
############################################################################################################################
Users:

Create:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:user="http://usersPostsComments.com/users">
   <soapenv:Header/>
   <soapenv:Body>
      <user:createUserRequest>
         <user:email>?</user:email>
         <user:username>?</user:username>
      </user:createUserRequest>
   </soapenv:Body>
</soapenv:Envelope>

Get all:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:user="http://usersPostsComments.com/users">
   <soapenv:Header/>
   <soapenv:Body>
      <user:getAllUsersRequest/>
   </soapenv:Body>
</soapenv:Envelope>

Get by id:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:user="http://usersPostsComments.com/users">
   <soapenv:Header/>
   <soapenv:Body>
      <user:getUserByIdRequest>
         <user:id>?</user:id>
      </user:getUserByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>

Update:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:user="http://usersPostsComments.com/users">
   <soapenv:Header/>
   <soapenv:Body>
      <user:updateUserRequest>
         <user:id>?</user:id>
         <user:email>?</user:email>
         <user:username>?</user:username>
      </user:updateUserRequest>
   </soapenv:Body>
</soapenv:Envelope>

Delete:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:user="http://usersPostsComments.com/users">
   <soapenv:Header/>
   <soapenv:Body>
      <user:deleteUserRequest>
         <user:id>?</user:id>
      </user:deleteUserRequest>
   </soapenv:Body>
</soapenv:Envelope>

############################################################################################################################
Posts:

Create:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pos="http://usersPostsComments.com/posts">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:createPostRequest>
         <pos:userId>?</pos:userId>
         <pos:title>?</pos:title>
         <pos:body>?</pos:body>
         <!--Optional:-->
         <pos:location>
            <pos:city>?</pos:city>
            <pos:date>?</pos:date>
            <pos:temperature>?</pos:temperature>
         </pos:location>
      </pos:createPostRequest>
   </soapenv:Body>
</soapenv:Envelope>

Get all:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pos="http://usersPostsComments.com/posts">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:getAllPostsRequest/>
   </soapenv:Body>
</soapenv:Envelope>

Get by id:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pos="http://usersPostsComments.com/posts">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:getPostByIdRequest>
         <pos:id>?</pos:id>
      </pos:getPostByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>

Update:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pos="http://usersPostsComments.com/posts">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:updatePostRequest>
         <pos:id>?</pos:id>
         <pos:userId>?</pos:userId>
         <pos:title>?</pos:title>
         <pos:body>?</pos:body>
         <!--Optional:-->
         <pos:location>
            <pos:city>?</pos:city>
            <pos:date>?</pos:date>
            <pos:temperature>?</pos:temperature>
         </pos:location>
      </pos:updatePostRequest>
   </soapenv:Body>
</soapenv:Envelope>

Delete:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pos="http://usersPostsComments.com/posts">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:deletePostRequest>
         <pos:id>?</pos:id>
      </pos:deletePostRequest>
   </soapenv:Body>
</soapenv:Envelope>

############################################################################################################################
Comments:

Create:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:com="http://usersPostsComments.com/comments">
   <soapenv:Header/>
   <soapenv:Body>
      <com:createCommentRequest>
         <com:userId>?</com:userId>
         <com:postId>?</com:postId>
         <com:body>?</com:body>
      </com:createCommentRequest>
   </soapenv:Body>
</soapenv:Envelope>

Get all:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:com="http://usersPostsComments.com/comments">
   <soapenv:Header/>
   <soapenv:Body>
      <com:getAllCommentsRequest/>
   </soapenv:Body>
</soapenv:Envelope>

Get by id:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:com="http://usersPostsComments.com/comments">
   <soapenv:Header/>
   <soapenv:Body>
      <com:getCommentByIdRequest>
         <com:id>?</com:id>
      </com:getCommentByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>

Update:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:com="http://usersPostsComments.com/comments">
   <soapenv:Header/>
   <soapenv:Body>
      <com:updateCommentRequest>
         <com:id>?</com:id>
         <com:userId>?</com:userId>
         <com:postId>?</com:postId>
         <com:body>?</com:body>
      </com:updateCommentRequest>
   </soapenv:Body>
</soapenv:Envelope>

Delete:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:com="http://usersPostsComments.com/comments">
   <soapenv:Header/>
   <soapenv:Body>
      <com:deleteCommentRequest>
         <com:id>?</com:id>
      </com:deleteCommentRequest>
   </soapenv:Body>
</soapenv:Envelope>
~~~~
