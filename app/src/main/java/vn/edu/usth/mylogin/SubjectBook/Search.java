package vn.edu.usth.mylogin.SubjectBook;
import java.util.List;
public class Search {
    private List<Doc> docs;
    public List<Doc> getDocs() {
        return docs;
    }
    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }
    public class Doc {
        String key;
        String type;
        public List<String> getSeed() {
            return seed;
        }
        public void setSeed(List<String> seed) {
            this.seed = seed;
        }
        List<String> seed;
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
    }
}
