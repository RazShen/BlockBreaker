package highscores;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class features a high score table.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-05
 */
public class HighScoresTable implements Serializable {
    /**
     * Size of the high score table.
     */
    private int sizeOfTable;
    /**
     * List of high scores.
     */
    private List<ScoreInfo> highScores;


    /**
     * Constructor for high score table from size.
     * @param size size of the high score table.
     */
    public HighScoresTable(int size) {
        this.sizeOfTable = size;
        this.highScores = new ArrayList<ScoreInfo>();
    }

    /**
     * This method adds a score info to the high score table (if it's rank is in top 5).
     * @param score score info.
     */
    public void add(ScoreInfo score) {
        int rank = getRank(score.getScore());
        if (rank > this.sizeOfTable) {
            return;
        }
        this.highScores.add(rank - 1, score);
        if (this.highScores.size() > this.sizeOfTable) {
            this.highScores.remove(this.highScores.size() - 1);
        }
    }

    /**
     * This method returns the high score table size.
     * @return high score table size.
     */
    public int size() {
        return highScores.size();
    }

    /**
     * This method returns the high score table.
     * @return high score table.
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * This method gets the rank of inputted score by checking it's relative value.
     * @param score sore.
     * @return rank of inputted score.
     */
    public int getRank(int score) {
        int i;
        for (i = 0; i < this.highScores.size(); i++) {
            if (score > this.highScores.get(i).getScore()) {
                break;
            }
        }
        return i + 1;
    }

    /**
     * This method clears the high score table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * This method loads a file and creates and new high score table from it.
     * @param filename name of the table.
     * @throws IOException input/out exception.
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        HighScoresTable highScoresTable;
        String theFilename = filename.getName();
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(filename));
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.highScores = highScoresTable.highScores;
            this.sizeOfTable = highScoresTable.sizeOfTable;
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + theFilename);
            HighScoresTable emptyTable = new HighScoresTable(5);
            emptyTable.save(filename);
            this.highScores = emptyTable.highScores;
            this.sizeOfTable = emptyTable.sizeOfTable;
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find class for object in file: " + theFilename);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + theFilename);
            }
        }
    }

    /**
     * Save this table data into a specific file name.
     * @param filename file name to save into.
     * @throws IOException input out exception.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        String theFilename = filename.getName();
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(theFilename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + theFilename);
            }
        }
    }

    /**
     * This method loads high score table with a specific file name, if it isn't found this method creates a new high
     * score table.
     * @param filename specific file name.
     * @return high score table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable emptyTable = new HighScoresTable(5);
        String theFilename = filename.getName();
        try {
            if (!filename.exists()) {
                return emptyTable;
            }
            emptyTable.load(filename);
        } catch (IOException e) {
            System.err.println("Failed closing file: " + theFilename);
            return new HighScoresTable(5);
        }
        return emptyTable;
    }
}
