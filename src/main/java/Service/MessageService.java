package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;

import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    
    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    

    /**
     * addMessage(Message message)
     * 
     * message_text empty return null
     * message_text length greater than 255 return null
     * account with the account_id belonging to the person who posted the message exists
     * 
     * @param message
     * @return inserted message
     */
    public Message addMessage(Message message) {
        if(message.getMessage_text()==""){
            return null;
        }else if(message.getMessage_text().length()>255){
            return null;
        }else if(accountDAO.getAccountByAccountid(message.getPosted_by())==null){
            return null;
        }
        return messageDAO.insertMessage(message);
    }


    /**
     * getAllMessages()
     * @return all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * getMessagebyMessageId(int message_id)
     * 
     * @param message_id
     * @return a message given message_id
     */
    public Message getMessagebyMessageId(int message_id){
        return messageDAO.getMessagebyMessageId(message_id);
    }

    
    /**
     * deleteMessagebyMessageId(int message_id)
     * 
     * @param message_id
     * @return the deleted message given message_id
     */
    public Message deleteMessagebyMessageId(int message_id){
        //Store the message before deleting it
        Message messageToBeDeleted=messageDAO.getMessagebyMessageId(message_id);
        messageDAO.deleteMessagebyMessageId(message_id);
        return messageToBeDeleted; //Return the stored variable
    }

    /**
     * updateMessagebyMessageId(int message_id)
     * The update of a message should be successful if and only if the message_id already exists 
     * and the new message_text is not blank and is not over 255 characters.
     * 
     * @param message_id
     * @param message_text
     * @return the updated message given message_id
     */
    public Message updateMessagebyMessageId(int message_id, String message_text){ 
        if(getMessagebyMessageId(message_id)==null){
            return null;
        }else if(message_text==""){
            return null;
        }
        return messageDAO.updateMessagebyMessageId(message_id, message_text);
    }


    /**
     * getAllMessagesbyAccountId(account_id)
     * 
     * @param account_id
     * @return a message given account_id
     */
    public List<Message> getAllMessagesbyAccountId(int account_id){
        return messageDAO.getAllMessagesbyAccountId(account_id);
    }

    
}
