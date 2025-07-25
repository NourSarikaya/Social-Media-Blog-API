package Controller;


import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import java.util.List;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    /*private void exampleHandler(Context context) {
        context.json("sample text");
    }*/

    /*REFERENCE CONSTRUCTOR FROM LIBRARY MINI PROJECT:
    BookService bookService;
    AuthorService authorService;

    public LibraryController(){
        this.bookService = new BookService();
        this.authorService = new AuthorService();
    }*/

    AccountService accountService; 
    MessageService messageService;

    public SocialMediaController(){ 
        this.accountService = new AccountService(); 
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //app.get("example-endpoint", this::exampleHandler);

        //USER REGISTRATION
        app.post("/register", this::postAccountHandler);
        //LOGIN
        app.post("/login",this::postLoginHandler);
        //CREATE NEW MESSAGE
        app.post("/messages",this::postMessagesHandler);
        //GET ALL MESSAGES
        app.get("/messages",this::getAllMessagesHandler);
        //GET ONE MESSAGE GIVEN MESSAGE ID
        app.get("/messages/{message_id}",this::getMessagebyMessageIdHandler);
        //DELETE A MESSAGE GIVEN MESSAGE ID
        app.delete("/messages/{message_id}",this::deleteMessagebyMessageIdHandler);
        //UPDATE MESSAGE GIVEN MESSAGE ID
        app.patch("/messages/{message_id}",this::updateMessagebyMessageIdHandler);
        //GET ALL MESSAGES FROM USER GIVEN ACCOUNT ID
        app.get("/accounts/{account_id}/messages",this::getAllMessagebyAccountIdHandler);

        // app.start(8080); 
        //  We do not need to have app.start() in our startAPI method because the setUp method in the test
        // code already does it for us
        // Before every test, setUp method resets the database, restarts the Javalin app, 
        // and creates a new webClient and ObjectMapper
        // for interacting locally on the web.
        return app;
        
    }

    /* REFERENCE START API METHOD FROM LIBRARY MINI PROJECT:
    public void startAPI(){
        Javalin app = Javalin.create();
        app.get("/books", this::getAllBooksHandler);
        app.post("/books", this::postBookHandler);
        app.get("/authors", this::getAllAuthorsHandler);
        app.post("/authors", this::postAuthorHandler);
        app.get("/books/available", this::getAvailableBooksHandler);
        app.start(8080);
    }
    */

       /*
     * REFERENCE MINI LIBRARY PROJECT FROM WEEK 6
     * Referenced the following code for postHandlers in this controller
     * private void postBookHandler(Context ctx) throws JsonProcessingException {
     *  ObjectMapper mapper = new ObjectMapper();
     *  Book book = mapper.readValue(ctx.body(), Book.class);
     *  Book addedBook = bookService.addBook(book);
     *  if(addedBook!=null){
     *      ctx.json(mapper.writeValueAsString(addedBook));
     *  }else{
     *      ctx.status(400);
     *  }
     * }
     */

    /*
     * REFERENCE MINI LIBRARY PROJECT FROM WEEK 6
     * Referenced the following code for getHandlers in this controller
     * public void getAllBooksHandler(Context ctx){
     *      List<Book> books = bookService.getAllBooks();
     *      ctx.json(books);
     * }
     */
     

    /**
     * Handler to post a new account.
     * As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
     * The body will contain a representation of a JSON Account, but will not contain an account_id.
     * 
     * The registration will be successful if and only if the username is not blank, 
     * the password is at least 4 characters long, and an Account with that username does not already exist. 
     * If all these conditions are met, the response body should contain a JSON of the Account, including its account_id.
     * 
     * The response status should be 200 OK, which is the default. The new account should be persisted to the database.
     * 
     * If the registration is not successful, the response status should be 400. (Client error)
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException { 


        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        //The addAccount method should return null if the above conditions in the method description are not met.
        Account addedAccount = accountService.addAccount(account); 
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            //ctx.status(200); default (OK)
        }else{
            ctx.status(400);// Client Error
        }
    }


    /**
     * Handler to verify login info.
     * postLoginHandler(Context ctx) 
     * As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.
     * The request body will contain a JSON representation of an Account, 
     * not containing an account_id. 
     * 
     * The login will be successful if and only 
     * if the username and password provided in the request body JSON match a real account existing on the database. 
     * If successful, the response body should contain a JSON of the account in the response body, 
     * including its account_id. The response status should be 200 OK, which is the default.
     * 
     * If the registration is not successful, the response status should be 401. (Unauthorized)
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        //The getAccount method should return null if the above conditions in the method description are not met.
        Account foundAccount = accountService.getAccount(account); 
        if(foundAccount!=null){
            ctx.json(mapper.writeValueAsString(foundAccount));
            //ctx.status(200); //default
        }else{
            ctx.status(401); //Unauthorized
        }
    }


    /**
     * Create New Message
     * postMessagesHandler(Context ctx) 
     * As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. 
     * The request body will contain a JSON representation of a message, which should be persisted to the database, 
     * but will not contain a message_id.
     * 
     * The creation of the message will be successful if and only if the message_text is not blank, 
     * is under 255 characters, and posted_by refers to a real, existing user. 
     * If successful, the response body should contain a JSON of the message, including its message_id. 
     * The response status should be 200, which is the default. The new message should be persisted to the database.
     * If the creation of the message is not successful, the response status should be 400. (Client error)
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message= mapper.readValue(ctx.body(), Message.class);
        //The addAccount method should return null if the above conditions in the method description are not met.
        Message addedMessage = messageService.addMessage(message); 
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            //ctx.status(200); default
        }else{
            ctx.status(400);
        }
    }

     /**
     * Handler to retrieve all messages.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /*
     * REFERENCE WEEK 6 JAVALIN Handlers Class Notes
     * Reference for accessing path param such as {message_id}
     * public static Handler fetchById = ctx -> {
     * int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id"))); ...
     */

    /**
     * Handler to retrieve a message given message_id.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void getMessagebyMessageIdHandler(Context ctx){
        String message_id_str=ctx.pathParam("message_id");
        //We have to cast message_id into an integer
        int message_id=Integer.parseInt(message_id_str);
        Message foundMessage =messageService.getMessagebyMessageId(message_id);

        //check whether message belonging to message_id exists in the database
        if(foundMessage!=null){
            ctx.json(foundMessage);
        }else{
            ctx.result(""); //status code 200 by default
        }

    }

    
    /**
     * Handler to delete a message given message_id.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void deleteMessagebyMessageIdHandler(Context ctx){
        
        String message_id_str=ctx.pathParam("message_id");
        //We have to cast message_id into an integer
        int message_id=Integer.parseInt(message_id_str);
        Message deletedMessage =messageService.deleteMessagebyMessageId(message_id);
        //check whether message belonging to message_id exists in the database
        if(deletedMessage!=null){
            ctx.json(deletedMessage);
        }else{
            ctx.result(""); //status code 200 by default
        }
    }


    /**
     * Handler to update a message given message_id.
     * 
     * The update of a message should be successful if and only if the message_id already exists 
     * and the new message_text is not blank and is not over 255 characters.
     *  If the update is successful, the response body should contain the full updated message 
     * (including message_id, posted_by, message_text, and time_posted_epoch), 
     * and the response status should be 200, which is the default. 
     * The message existing on the database should have the updated message_text.
     * If the update of the message is not successful for any reason, the response status should be 400. (Client error)
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void updateMessagebyMessageIdHandler(Context ctx) throws JsonProcessingException { 
        // One bug I had that took me hours to fix was not having "throws JsonProcessingException"
        
        // Parse the body to get message_text
        ObjectMapper mapper = new ObjectMapper();
        
        // The request body should contain a new message_text values to replace the message identified by message_id. 
        // The request body can not be guaranteed to contain any other information.
        // Retrieve message_text from the request body
        // Possible by just retrieving Message as class from body and using a getter to get message_text
        Message message= mapper.readValue(ctx.body(), Message.class);

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        
        Message updatedMessage =messageService.updateMessagebyMessageId(message_id,message.getMessage_text());
        //check whether message belonging to message_id exists in the database
        if(updatedMessage!=null){
            ctx.json(updatedMessage);
        }else{
            ctx.status(400); //Client Error
        }
    }

    /**
     * 
     * Handler to retrieve messages given account_id.
     * 
     * The response body should contain a JSON representation of a list containing all messages posted by a particular user, 
     * which is retrieved from the database. 
     * It is expected for the list to simply be empty if there are no messages. 
     * The response status should always be 200, which is the default
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    

    private void getAllMessagebyAccountIdHandler(Context ctx) throws JsonProcessingException { 
    
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages =messageService.getAllMessagesbyAccountId(account_id);
        ctx.json(messages);
        
    }
}