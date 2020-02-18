import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kenneth P Rose
 * NETID:  111387328
 */
public class set_cover {

    public static ArrayList minSetCover;
    public static int minSetCoverSize = Integer.MAX_VALUE;

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();

        /**
         * ADD TEST FILE DIRECTORY HERE
         */
        File file = new File(".\\tests\\s-rg-63-25.txt");
        /**
         * ADD TEST FILE DIRECTORY HERE
         */

        Scanner scanner = new Scanner(file);

        int universalSetSize = Integer.parseInt(scanner.nextLine());
        int numSets = Integer.parseInt(scanner.nextLine());

        ArrayList universalSet = new ArrayList();
        for (int a = 0; a < universalSetSize; a++) {
            universalSet.add(a + 1);
        }

        ArrayList<ArrayList> sets = new ArrayList();

        for (int b = 0; b < numSets; b++) {
            String nextSet = scanner.nextLine();
            ArrayList set = new ArrayList();
            while (!nextSet.equals("")) {
                set.add(Integer.parseInt(nextSet.substring(0, nextSet.indexOf(" "))));
                nextSet = nextSet.substring(nextSet.indexOf(" ") + 1);
            }
            sets.add(set);
        }

        Collections.sort(sets, new Comparator<ArrayList>() {
            @Override
            public int compare(ArrayList o1, ArrayList o2) {
                return o2.size() - o1.size();
            }
        });

        ArrayList setCover = new ArrayList();

        ArrayList<Boolean> included = new ArrayList();
        for (int c = 0; c < sets.size(); c++) {
            included.add(false);
        }

        backtrack(universalSet, sets, setCover, included, 0, 0);

        System.out.println("Solution To: " + file.getName().substring(0, file.getName().indexOf(".")) + "\n");
        System.out.println("MSC Size: " + minSetCoverSize);
        printSet(minSetCover, "MSC Sets:");
        long end = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + ((end - start) / 1000F) + " seconds");
    }

    public static void backtrack(ArrayList US, ArrayList<ArrayList> candidates, ArrayList setCover, ArrayList<Boolean> included, int currentIndex, int numIncluded) {

        if (isSetCover(US, setCover)) {
            process_solution(included, candidates);
            return;
        } else if(numIncluded >= minSetCoverSize) {
            return;
        } else {
            for (int d = currentIndex; d < candidates.size(); d++) {
                if (!included.get(d)) {
                    ArrayList cover = new ArrayList(setCover);
                    cover = mergeSets(cover, candidates.get(d));
                    ArrayList<Boolean> cpy = new ArrayList<>(included);
                    cpy.set(d, true);
                    int includedIncrement = numIncluded + 1;
                    backtrack(US, candidates, cover, cpy, d, includedIncrement);
                }
            }
        }


    }

    public static ArrayList mergeSets(ArrayList set1, ArrayList set2) {
        ArrayList combinedSet = new ArrayList();
        combinedSet.addAll(set1);
        combinedSet.addAll(set2);
        Collections.sort(combinedSet);
        removeDuplicates(combinedSet);
        return combinedSet;
    }

    public static void removeDuplicates(ArrayList list) {
        int d = 0;
        while (list.size() > d + 2) {
            while (list.get(d) == list.get(d + 1)) {
                list.remove(d + 1);
            }
            d++;
        }
    }

    public static boolean isSetCover(ArrayList universalSet, ArrayList set) {
        if (universalSet.size() == set.size()) {
            Collections.sort(set);
            for (int e = 0; e < universalSet.size(); e++) {
                if (universalSet.get(e) != set.get(e)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static void process_solution(ArrayList<Boolean> included, ArrayList candidates) {
        int g = 0;
        ArrayList possibleMin = new ArrayList();
        for (int f = 0; f < included.size(); f++) {
            if (included.get(f)) {
                g++;
                possibleMin.add(candidates.get(f));
            }
        }

        if (g < minSetCoverSize) {
            minSetCover = possibleMin;
            minSetCoverSize = g;
        }
    }

    public static void printSet(ArrayList set, String name) {
        System.out.println(name + "\t");
        for (int z = 0; z < set.size(); z++) {
            System.out.println("\t" + set.get(z));
        }
        System.out.println("");
    }
}
