# UsersPostsCommentsV3

<ip>(port 80)/soap
  
##############################################################
Users:</br>

Create:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:user="http://usersPostsComments.com/users"></br>
  <soapenv:Header/></br>
   <soapenv:Body></br>
      <user:createUserRequest></br>
         <user:email>?</user:email></br>
         <user:username>?</user:username></br>
      </user:createUserRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Get all:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:user="http://usersPostsComments.com/users"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <user:getAllUsersRequest/></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Get by id:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:user="http://usersPostsComments.com/users"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <user:getUserByIdRequest></br>
         <user:id>?</user:id></br>
      </user:getUserByIdRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Update:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:user="http://usersPostsComments.com/users"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <user:updateUserRequest></br>
         <user:id>?</user:id></br>
         <user:email>?</user:email></br>
         <user:username>?</user:username></br>
      </user:updateUserRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Delete:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:user="http://usersPostsComments.com/users"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <user:deleteUserRequest></br>
         <user:id>?</user:id></br>
      </user:deleteUserRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

####################################################################################
Posts:</br>

Create:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:pos="http://usersPostsComments.com/posts"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <pos:createPostRequest></br>
         <pos:userId>?</pos:userId></br>
         <pos:title>?</pos:title></br>
         <pos:body>?</pos:body></br>
         <!--Optional:--></br>
         <pos:location></br>
            <pos:city>?</pos:city></br>
            <pos:date>?</pos:date></br>
            <pos:temperature>?</pos:temperature></br>
         </pos:location></br>
      </pos:createPostRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Get all:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:pos="http://usersPostsComments.com/posts"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <pos:getAllPostsRequest/></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Get by id:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:pos="http://usersPostsComments.com/posts"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <pos:getPostByIdRequest></br>
         <pos:id>?</pos:id></br>
      </pos:getPostByIdRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Update:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:pos="http://usersPostsComments.com/posts"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <pos:updatePostRequest></br>
         <pos:id>?</pos:id></br>
         <pos:userId>?</pos:userId></br>
         <pos:title>?</pos:title></br>
         <pos:body>?</pos:body></br>
         <!--Optional:--></br>
         <pos:location></br>
            <pos:city>?</pos:city></br>
            <pos:date>?</pos:date></br>
            <pos:temperature>?</pos:temperature></br>
         </pos:location></br>
      </pos:updatePostRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Delete:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:pos="http://usersPostsComments.com/posts"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <pos:deletePostRequest></br>
         <pos:id>?</pos:id></br>
      </pos:deletePostRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

##############################################################################
Comments:</br>

Create:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:com="http://usersPostsComments.com/comments"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <com:createCommentRequest></br>
         <com:userId>?</com:userId></br>
         <com:postId>?</com:postId></br>
         <com:body>?</com:body></br>
      </com:createCommentRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Get all:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:com="http://usersPostsComments.com/comments"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <com:getAllCommentsRequest/></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Get by id:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:com="http://usersPostsComments.com/comments"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <com:getCommentByIdRequest></br>
         <com:id>?</com:id></br>
      </com:getCommentByIdRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Update:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:com="http://usersPostsComments.com/comments"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <com:updateCommentRequest></br>
         <com:id>?</com:id></br>
         <com:userId>?</com:userId></br>
         <com:postId>?</com:postId></br>
         <com:body>?</com:body></br>
      </com:updateCommentRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>

Delete:</br>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"</br> xmlns:com="http://usersPostsComments.com/comments"></br>
   <soapenv:Header/></br>
   <soapenv:Body></br>
      <com:deleteCommentRequest></br>
         <com:id>?</com:id></br>
      </com:deleteCommentRequest></br>
   </soapenv:Body></br>
</soapenv:Envelope></br>
