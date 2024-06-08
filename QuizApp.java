import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Question {
    String question;
    String[] options;
    int correctOption;

    Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class QuizApp {
    private static HashMap<String, ArrayList<Question>> quizzes = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create a new quiz");
            System.out.println("2. Attempt a quiz");
            System.out.println("3. Delete questions from a quiz");
            System.out.println("4. Show available quizzes");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    createNewQuiz(scanner);
                    break;
                case 2:
                    attemptQuiz(scanner);
                    break;
                case 3:
                    deleteQuestionsFromQuiz(scanner);
                    break;
                case 4:
                    showAvailableQuizzes();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createNewQuiz(Scanner scanner) {
        System.out.print("Enter the quiz topic: ");
        String topic = scanner.nextLine();

        if (quizzes.containsKey(topic)) {
            System.out.println("A quiz with this topic already exists. Please choose another topic.");
            return;
        }

        quizzes.put(topic, new ArrayList<>());
        System.out.println("Quiz created successfully!");

        addQuestionsToQuiz(scanner, topic);
    }

    private static void addQuestionsToQuiz(Scanner scanner, String topic) {
        if (!quizzes.containsKey(topic)) {
            System.out.println("Quiz not found. Please create the quiz first.");
            return;
        }

        do {
            System.out.print("Enter the question: ");
            String question = scanner.nextLine();

            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                System.out.print("Enter option " + (i + 1) + ": ");
                options[i] = scanner.nextLine();
            }

            System.out.print("Enter the number of the correct option (1-4): ");
            int correctOption = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (correctOption < 1 || correctOption > 4) {
                System.out.println("Invalid correct option. Please try again.");
                continue;
            }

            quizzes.get(topic).add(new Question(question, options, correctOption - 1));
            System.out.println("Question added successfully!");

            System.out.print("Do you want to add another question to this quiz? (yes/no): ");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));
    }

    private static void attemptQuiz(Scanner scanner) {
        System.out.print("Enter the quiz topic: ");
        String topic = scanner.nextLine();

        if (!quizzes.containsKey(topic)) {
            System.out.println("Quiz not found.");
            return;
        }

        ArrayList<Question> quiz = quizzes.get(topic);
        int score = 0;

        for (int i = 0; i < quiz.size(); i++) {
            Question q = quiz.get(i);
            System.out.println("Question " + (i + 1) + ": " + q.question);
            for (int j = 0; j < q.options.length; j++) {
                System.out.println((j + 1) + ". " + q.options[j]);
            }
            System.out.print("Your answer: ");
            int answer = scanner.nextInt();
            if (answer - 1 == q.correctOption) {
                score++;
            }
        }

        System.out.println("You scored " + score + " out of " + quiz.size());
    }

    private static void deleteQuestionsFromQuiz(Scanner scanner) {
        System.out.print("Enter the quiz topic: ");
        String topic = scanner.nextLine();

        if (!quizzes.containsKey(topic)) {
            System.out.println("Quiz not found.");
            return;
        }

        ArrayList<Question> quiz = quizzes.get(topic);

        do {
            System.out.print("Enter the question number to delete (1-" + quiz.size() + "): ");
            int questionNumber = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (questionNumber < 1 || questionNumber > quiz.size()) {
                System.out.println("Invalid question number. Please try again.");
                continue;
            }

            quiz.remove(questionNumber - 1);
            System.out.println("Question deleted successfully!");

            System.out.print("Do you want to delete another question from this quiz? (yes/no): ");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));
    }

    private static void showAvailableQuizzes() {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
        } else {
            System.out.println("Available quizzes:");
            for (String topic : quizzes.keySet()) {
                System.out.println("- " + topic);
            }
        }
    }
}
