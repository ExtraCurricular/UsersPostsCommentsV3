package com.WebServices.PostService;

import com.WebServices.PostService.ServiceFault.ServiceFault;
import com.WebServices.PostService.ServiceFault.ServiceFaultException;
import com.WebServices.PostService.models.User;
import com.WebServices.PostService.repositories.UserRepository;
import com.userspostscomments.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.server.endpoint.annotation.SoapHeader;

import javax.xml.ws.handler.MessageContext;
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
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getId() + " was not found.")));

        com.userspostscomments.users.User userDTO = new com.userspostscomments.users.User();
        userDTO.setId((int)user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        response.setUser(userDTO);

		return response;
	}

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request,  MessageContext messageContext) {
        CreateUserResponse response = new CreateUserResponse();

        if (request.getEmail() == null){
            throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the email"));
        }

        if(request.getUsername() == null){
            throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the username"));
        }

        if ((userRepository.findByEmail(request.getEmail())).size() != 0){
            throw new ServiceFaultException("ERROR", new ServiceFault("CONFLICT", "A user with email: " + request.getEmail() + " already exists."));
        }

        if((userRepository.findByUsername(request.getUsername())).size() != 0){
            throw new ServiceFaultException("ERROR", new ServiceFault("CONFLICT", "A user with username: " + request.getUsername() + " already exists."));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        User userNew = userRepository.save(user);

        com.userspostscomments.users.User userDTO = new com.userspostscomments.users.User();
        userDTO.setId((int)userNew.getId());
        userDTO.setUsername(userNew.getUsername());
        userDTO.setEmail(userNew.getEmail());

        response.setCreatedUser(userDTO);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse response = new UpdateUserResponse();

        User user =  userRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getId() + " was not found.")));

        if (request.getEmail() != null && (userRepository.findByEmail(request.getEmail())).size() != 0){
            throw new ServiceFaultException("ERROR", new ServiceFault("CONFLICT", "A user with email: " + request.getEmail() + " already exists."));
        }

        if(request.getUsername() != null && (userRepository.findByUsername(request.getUsername())).size() != 0){
            throw new ServiceFaultException("ERROR", new ServiceFault("CONFLICT", "A user with username: " + request.getUsername() + " already exists."));
        }

        if(request.getEmail() != null){
            user.setEmail(request.getEmail());
        }

        if(request.getUsername() != null){
            user.setUsername(request.getUsername());
        }

        userRepository.save(user);

        com.userspostscomments.users.User userDTO = new com.userspostscomments.users.User();
        userDTO.setId((int)user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        response.setUpdatedUser(userDTO);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();

        User user = userRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getId() + " was not found.")));

        userRepository.delete(user);

        return response;
    }
}