class PeekingIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private Integer nextElement;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
        this.nextElement = iterator.hasNext() ? iterator.next() : null;
    }
    public Integer peek() {
        return nextElement;
    }
    @Override
    public Integer next() {
        Integer current = nextElement;
        nextElement = iterator.hasNext() ? iterator.next() : null;
        return current;
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }
}
