package Money;

public class Note extends Money {
    private int noteValue;

    public Note(String currency, char category, int noteValue) {
        super(currency, category);
        this.noteValue = noteValue;
    }

    public int getNoteValue() {
        return noteValue;

    }

    public void setNoteValue(int noteValue) {
        this.noteValue = noteValue;
    }
}
