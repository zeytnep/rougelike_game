package rogue;

    public class NoSuchItemException extends Exception {
        private String newMessage;
        /**
        * Default constructor.
        */
        public NoSuchItemException() {
            super();
        }

        /**
        * Constructor with argument.
        * @param message new message to set
        */
        public NoSuchItemException(String message) {
            super(message);
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
