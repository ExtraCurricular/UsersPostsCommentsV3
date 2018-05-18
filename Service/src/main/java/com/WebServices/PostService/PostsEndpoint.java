package com.WebServices.PostService;

import com.WebServices.PostService.ServiceFault.ServiceFault;
import com.WebServices.PostService.ServiceFault.ServiceFaultException;
import com.WebServices.PostService.models.Location;
import com.WebServices.PostService.models.Post;
import com.WebServices.PostService.models.PostDTO;
import com.WebServices.PostService.models.User;
import com.WebServices.PostService.repositories.PostRepository;
import com.WebServices.PostService.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.userspostscomments.posts.*;
import com.userspostscomments.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class PostsEndpoint {
	private static final String NAMESPACE_URI = "http://usersPostsComments.com/posts";

	@Autowired
	UserRepository userRepository;

	@Autowired
    PostRepository postRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPostsRequest")
	@ResponsePayload
	public GetAllPostsResponse getAllPosts(@RequestPayload GetAllPostsRequest request) {
        GetAllPostsResponse response = new GetAllPostsResponse();

        List<Post> posts = postRepository.findAll();

        RestTemplate restTemplate = new RestTemplate();

        for (Post post : posts) {
            ResponseEntity<Location> forecastResponse = null;
            if (post.getWeatherId() != 0) {
                try{
                    forecastResponse =
                            restTemplate.exchange("http://userspostscommentsv3_WeatherService_1:5000/locations/" + post.getWeatherId(),
                                    HttpMethod.GET, null, new ParameterizedTypeReference<Location>() {
                                    });
                } catch (Exception ex){
                    com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
                    postNew.setUserId((int)post.getUserId());
                    postNew.setBody(post.getBody());
                    postNew.setTitle(post.getTitle());
                    postNew.setId((int)post.getId());
                    postNew.setLocationId((int)post.getWeatherId());
                    response.getPosts().add(postNew);
                    continue;
                }

                if (forecastResponse.getStatusCode() == HttpStatus.OK) {
                    com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
                    postNew.setUserId((int)post.getUserId());
                    postNew.setBody(post.getBody());
                    postNew.setTitle(post.getTitle());
                    postNew.setId((int)post.getId());

                    Weather weather = new Weather();
                    weather.setCity(forecastResponse.getBody().getCity());
                    weather.setDate(forecastResponse.getBody().getDate());
                    weather.setTemperature(forecastResponse.getBody().getTemperature());

                    postNew.setLocation(weather);
                    response.getPosts().add(postNew);
                }
            } else {
                com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
                postNew.setUserId((int)post.getUserId());
                postNew.setBody(post.getBody());
                postNew.setTitle(post.getTitle());
                postNew.setId((int)post.getId());
                response.getPosts().add(postNew);
            }
        }

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPostByIdRequest")
	@ResponsePayload
	public GetPostByIdResponse getPostById(@RequestPayload GetPostByIdRequest request) {
        GetPostByIdResponse response = new GetPostByIdResponse();

        Post post = postRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A post with id: " + request.getId() + " was not found.")));

        if (post.getWeatherId() != 0) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Location> forecastResponse = null;
            try{
                forecastResponse =
                        restTemplate.exchange("http://userspostscommentsv3_WeatherService_1:5000/locations/" + post.getWeatherId(),
                                HttpMethod.GET, null, new ParameterizedTypeReference<Location>() {
                                });
            } catch (Exception ex){
                com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
                postNew.setUserId((int)post.getUserId());
                postNew.setBody(post.getBody());
                postNew.setTitle(post.getTitle());
                postNew.setId((int)post.getId());
                postNew.setLocationId((int)post.getWeatherId());
                response.setPost(postNew);

                return response;
            }

            if (forecastResponse.getStatusCode() == HttpStatus.OK) {
                com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
                postNew.setUserId((int)post.getUserId());
                postNew.setBody(post.getBody());
                postNew.setTitle(post.getTitle());
                postNew.setId((int)post.getId());

                Weather weather = new Weather();
                weather.setCity(forecastResponse.getBody().getCity());
                weather.setDate(forecastResponse.getBody().getDate());
                weather.setTemperature(forecastResponse.getBody().getTemperature());

                postNew.setLocation(weather);
                response.setPost(postNew);
                return response;
            } else{
                com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
                postNew.setUserId((int)post.getUserId());
                postNew.setBody(post.getBody());
                postNew.setTitle(post.getTitle());
                postNew.setId((int)post.getId());
                postNew.setLocationId((int)post.getWeatherId());
                response.setPost(postNew);

                return response;
            }
        } else {

            com.userspostscomments.posts.Post postNew = new com.userspostscomments.posts.Post();
            postNew.setUserId((int)post.getUserId());
            postNew.setBody(post.getBody());
            postNew.setTitle(post.getTitle());
            postNew.setId((int)post.getId());
            response.setPost(postNew);

            return response;
        }
	}

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPostRequest")
    @ResponsePayload
    public CreatePostResponse createPost(@RequestPayload CreatePostRequest request) {
        CreatePostResponse response = new CreatePostResponse();

        if (request.getUserId() == 0) {
            throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the user id"));
        }

        if (request.getTitle() == null) {
            throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the post title"));
        }

        if (request.getBody() == null) {
            throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the post body"));
        }

        Post postNew = new Post();

        if (request.getLocation() != null) {
            if (request.getLocation().getCity() == null) {
                throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the city"));
            }

            if (request.getLocation().getDate() == null) {
                throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the date"));
            }

            RestTemplate restTemplate = new RestTemplate();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            String json = "";
            try {
                json = ow.writeValueAsString(request.getLocation());
            } catch (Exception ex){
                throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, submit only correct location data"));
            }

            HttpEntity<String> WeatherRequest = new HttpEntity<>(json);

            ResponseEntity<String> postResponse = null;

            try{
                postResponse = restTemplate.exchange("http://userspostscommentsv3_WeatherService_1:5000/locations",
                        HttpMethod.POST, WeatherRequest, String.class);
            } catch (Exception ex){
                throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service did not respond"));
            }

            if (postResponse.getStatusCode() == HttpStatus.CREATED) {
                HttpHeaders headers = postResponse.getHeaders();
                String location = headers.getLocation().toString();
                int index = location.lastIndexOf('/');
                int id = Integer.parseInt(location.substring(index + 1, location.length()));
                postNew.setWeatherId(id);
            } else {
                throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service did not respond"));
            }
        }

        userRepository.findById((long)request.getUserId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getUserId() + " was not found.")));

        postNew.setBody(request.getBody());
        postNew.setTitle(request.getTitle());
        postNew.setUserId(request.getUserId());

        Post postNewest = postRepository.save(postNew);

        com.userspostscomments.posts.Post postDTO = new com.userspostscomments.posts.Post();
        postDTO.setId((int)postNewest.getId());
        postDTO.setUserId((int)postNewest.getUserId());
        postDTO.setBody(postNewest.getBody());
        postDTO.setTitle(postNewest.getTitle());
        postDTO.setId((int)postNewest.getId());
        postDTO.setLocationId((int)postNewest.getWeatherId());
        response.setCreatedPost(postDTO);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePostRequest")
    @ResponsePayload
    public UpdatePostResponse updatePost(@RequestPayload UpdatePostRequest request) {
        UpdatePostResponse response = new UpdatePostResponse();

        Post post =  postRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A post with id: " + request.getId() + " was not found.")));

        if (request.getUserId() != 0) {
            userRepository.findById((long)request.getUserId()).orElseThrow(() ->
                    new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getUserId() + " was not found.")));
            post.setUserId(request.getUserId());
        }

        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }

        if (request.getBody() != null) {
            post.setBody(request.getBody());
        }

        if(request.getLocation() != null){
            if(post.getWeatherId() != 0){
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> deleteResponse =
                        restTemplate.exchange("http://userspostscommentsv3_WeatherService_1:5000/locations/" + post.getWeatherId(),
                                HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
                                });
                if(deleteResponse.getStatusCode() != HttpStatus.OK){
                    throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service responded with error code"));
                }
            }

            if (request.getLocation().getCity() == null) {
                throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the city"));
            }

            if (request.getLocation().getDate() == null) {
                throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the date"));
            }

            RestTemplate restTemplate = new RestTemplate();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            String json = "";
            try {
                json = ow.writeValueAsString(request.getLocation());
            } catch (Exception ex){
                throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, submit only correct location data"));
            }

            HttpEntity<String> WeatherRequest = new HttpEntity<>(json);

            ResponseEntity<String> postResponse = null;

            try{
                postResponse = restTemplate.exchange("http://userspostscommentsv3_WeatherService_1:5000/locations",
                        HttpMethod.POST, WeatherRequest, String.class);
            } catch (Exception ex){
                throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service did not respond"));
            }


            if (postResponse.getStatusCode() == HttpStatus.CREATED) {
                HttpHeaders headers = postResponse.getHeaders();
                String location = headers.getLocation().toString();
                int index = location.lastIndexOf('/');
                int id = Integer.parseInt(location.substring(index + 1, location.length()));
                post.setWeatherId(id);
            } else {
                throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service did not respond"));
            }
        }

        Post postNewest = postRepository.save(post);

        com.userspostscomments.posts.Post postDTO = new com.userspostscomments.posts.Post();
        postDTO.setId((int)postNewest.getId());
        postDTO.setUserId((int)postNewest.getUserId());
        postDTO.setBody(postNewest.getBody());
        postDTO.setTitle(postNewest.getTitle());
        postDTO.setId((int)postNewest.getId());
        postDTO.setLocationId((int)postNewest.getWeatherId());
        response.setUpdatedPost(postDTO);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePostRequest")
    @ResponsePayload
    public DeletePostResponse deletePost(@RequestPayload DeletePostRequest request) {
        DeletePostResponse response = new DeletePostResponse();

        Post post = postRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A post with id: " + request.getId() + " was not found.")));

        if (post.getWeatherId() != 0) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> deleteResponse = null;
            try{
                deleteResponse =
                        restTemplate.exchange("http://userspostscommentsv3_WeatherService_1:5000/locations/" + post.getWeatherId(),
                                HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
                                });
            } catch (Exception ex){
                throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service did not respond"));
            }

            if(deleteResponse.getStatusCode() != HttpStatus.OK){
                throw new ServiceFaultException("ERROR", new ServiceFault("SERVER_ERROR", "The weather service responded with error code"));
            }
        }

        postRepository.delete(post);

        return response;
    }
}