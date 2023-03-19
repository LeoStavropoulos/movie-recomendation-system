package org.example;

import java.util.*;

public class MovieRecommendationSystem {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int dur = 1;
            int flightDuration = 0;
            List<Integer> movieDurations = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                System.out.print("Please enter next movie's duration (enter 0 if there are no more movies): ");
                try {
                    dur = scanner.nextInt();
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer value for the movie duration.");
                }
                if (dur <= 0) {
                    break;
                }
                movieDurations.add(dur);
            }

            if (movieDurations.size() < 2) {
                throw new RuntimeException("There should be at least two movies!");
            }

            while (flightDuration <= 60) {
                System.out.print("Enter the flight duration (>60 min): ");
                flightDuration = scanner.nextInt();
            }

            int[] moviePair = selectMovies(movieDurations, flightDuration);

            System.out.printf("The best pair of movies to watch are: Movie %d with duration: %d and Movie %d with duration: %d. %n", (moviePair[0] + 1), movieDurations.get(moviePair[0]), (moviePair[1] + 1), movieDurations.get(moviePair[1]));
            System.out.println("Their total duration is: " + (movieDurations.get(moviePair[0]) + movieDurations.get(moviePair[1])) + " minutes.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer value for the flight duration.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static int[] selectMovies(List<Integer> movieDurations, int flightDuration) {
        // Create an array of index-value pairs for movie durations
        int n = movieDurations.size();
        Integer[] movieIndices = new Integer[n];
        for (int i = 0; i < n; i++) {
            movieIndices[i] = i;
        }

        // Sort the array of index-value pairs in descending order of values
        Arrays.sort(movieIndices, Collections.reverseOrder(Comparator.comparingInt(movieDurations::get)));

        // Find the pair of movies with maximum runtime within the desired time frame
        int maxRuntime = -1;
        int[] moviePair = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int runtime = movieDurations.get(movieIndices[i]) + movieDurations.get(movieIndices[j]);
                if (runtime <= flightDuration - 30 && runtime > maxRuntime) {
                    maxRuntime = runtime;
                    moviePair[0] = movieIndices[i];
                    moviePair[1] = movieIndices[j];
                }
            }
        }

        // Return the selected pair of movies
        return moviePair;
    }
}

