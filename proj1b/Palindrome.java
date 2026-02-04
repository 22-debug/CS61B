public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        int n = word.length();
        for (int i = 0; i < n; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        }
        while (deque.size() > 1) {
            Character first = deque.removeFirst();
            Character last = deque.removeLast();
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        }
        while (deque.size() > 1) {
            Character first = deque.removeFirst();
            Character last = deque.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }
}
