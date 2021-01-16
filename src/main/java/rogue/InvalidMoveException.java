package rogue;

    public class InvalidMoveException extends Exception {
        private String newMessage;
        /**
        * Default constructor.
        */
        public InvalidMoveException() {
            super();
        }
        /**
        * Constructor with argument.
        * @param message new message to set
        */
        public InvalidMoveException(String message) {
            super(message);
            setMessage(message);
        }
        /**
         * Setter for message.
         * @param message
        */
        public void setMessage(String message) {
            newMessage = message;
        }
        /**
         * Getter for message.
         * @return newMessage
        */
        public String getMessage() {
            return newMessage;
        }
}
