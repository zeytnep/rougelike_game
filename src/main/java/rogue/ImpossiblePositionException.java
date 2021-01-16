package rogue;

    public class ImpossiblePositionException extends Exception {
        /**
        * Default constructor.
        */
        public ImpossiblePositionException() {
            super();
        }
        /**
        * Constructor with argument.
        * @param message new message to set
        */
        public ImpossiblePositionException(String message) {
            super(message);
        }
}
