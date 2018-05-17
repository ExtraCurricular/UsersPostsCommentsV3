package com.WebServices.PostService;

import com.WebServices.PostService.ServiceFault.ServiceFault;
import com.WebServices.PostService.ServiceFault.ServiceFaultException;
import com.WebServices.PostService.models.User;
import com.WebServices.PostService.repositories.UserRepository;
import com.userspostscomments.users.GetAllUsersRequest;
import com.userspostscomments.users.GetAllUsersResponse;
import com.userspostscomments.users.GetUserByIdRequest;
import com.userspostscomments.users.GetUserByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class UsersEndpoint {
	private static final String NAMESPACE_URI = "http://usersPostsComments.com/users";

	@Autowired
	UserRepository userRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
	@ResponsePayload
	public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
		GetAllUsersResponse response = new GetAllUsersResponse();

		List<User> users = userRepository.findAll();
		for(User user : users){
			com.userspostscomments.users.User userDto = new com.userspostscomments.users.User();
			userDto.setId((int)user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setUsername(user.getUsername());
			response.getUsers().add(userDto);
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
	@ResponsePayload
	public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request) {
		GetUserByIdResponse response = new GetUserByIdResponse();

        User user =  userRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A ser with id: " + request.getId() + " was not found.")));

        com.userspostscomments.users.User userDTO = new com.userspostscomments.users.User();
        userDTO.setId((int)user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        response.setUser(userDTO);

		return response;
	}
}