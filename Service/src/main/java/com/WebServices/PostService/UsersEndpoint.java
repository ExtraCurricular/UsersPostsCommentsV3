package com.WebServices.PostService;

import com.WebServices.PostService.models.User;
import com.WebServices.PostService.repositories.UserRepository;
import com.userspostscomments.users.GetAllUsersRequest;
import com.userspostscomments.users.GetAllUsersResponse;
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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetAllUsersResponse getCountry(@RequestPayload GetAllUsersRequest request) {
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
}