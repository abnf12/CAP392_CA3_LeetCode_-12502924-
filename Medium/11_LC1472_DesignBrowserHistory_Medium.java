class BrowserHistory {
    private final List<String> history;
    private int currIndex;
    private int bound;

    public BrowserHistory(String homepage) {
        this.history = new ArrayList<>();
        this.history.add(homepage);
        this.currIndex = 0;
        this.bound = 0;
    }

    public void visit(String url) {
        currIndex++;
        if (currIndex < history.size()) {
            history.set(currIndex, url);
        } else {
            history.add(url);
        }
        bound = currIndex;
    }

    public String back(int steps) {
        currIndex = Math.max(0, currIndex - steps);
        return history.get(currIndex);
    }

    public String forward(int steps) {
        currIndex = Math.min(bound, currIndex + steps);
        return history.get(currIndex);
    }
}
